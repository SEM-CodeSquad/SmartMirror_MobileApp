package postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;



/**
 * This class here initiates the shopping.xml which is the fragment view for the Shopping List
 * RFC that we are implementing. For consistency purposes, the list that holds the shopping
 * list items will be mentioned as 'shopping list'.
 */
public class ShoppingView extends Fragment {
    View myView;
    ShoppingPresenter presenter;
    ArrayAdapter<String> adapter = null;          /* A way to handle a list or array of objects and map them to a row */
    ListView listView = null;
    String uuid;

    /**
     * onCreateView applies the shopping.xml layout and displays it on the screen using the below
     * mentioned parameters.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return returns the view inflated on the screen.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.uuid = ((NavigationActivity) getActivity()).getMirror();
        presenter = new ShoppingPresenter(this, uuid);
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.shopping, container, false);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, presenter.getShoppingList());
        listView = (ListView) myView.findViewById(R.id.listView);

        /**
         * This function sets each row of a listView(each item of a shopping list) a clickable item which will open
         * a dialog box that prompts the user whether or not he/she wants to delete the item.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(presenter.getShoppingList().get(position).trim())) {     /*A check done for integrity purposes*/
                    removeElement(selectedItem);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error Removing Element", Toast.LENGTH_LONG).show();
                }
            }
        });

        return myView;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onResume() {
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Shopping List");
    }

    /**
     * This method uses a menu resource file to add 2 buttons to the app bar (add Item & delete list).
     *
     * @param menu the menu xml layout that is applied when creating the options menu
     * @param inflater inflates the menu on the view.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * The app bar items(buttons in our case) are handled here. Appropriate methods are called
     * and published to the RFC topic using the id of each button.
     * Upon completion of each list update, the updateList method is called which updates the
     * shoppingList item linkedList with the shopping List received by fetch calls made to the RFC topic.
     *
     * @param item the MenuItem instance that has been tapped by the user.
     * @return true after the MenuItem is tapped.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /**
         * If the MenuItem tapped has the id "add", an Alert Dialog is shown to the user
         * which prompts the user to type in the item name and add it to the list.
         */
        if (id == R.id.action_add) {
            if (uuid.equals("No mirror chosen")) {
                makeToast("Please choose a mirror first");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Item");
                final EditText input = new EditText(getActivity());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.updateList("add", input.getText().toString());

                        /**
                         * The below for loop stops the thread for 5 seconds but at interval of each half
                         * a second, checks whether the phone has received a "done" message after
                         * adding an element to the list. If it does receive the "done" reply,
                         * the method then updates the list view adapter to display the added
                         * item along with the original list.
                         */
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (presenter.getBoolean()) {
                                break;
                            }
                        }
                        if (!presenter.getBoolean()) {
                            makeToast("Error adding item, please try again");
                        } else if (presenter.getBoolean()) {
                            makeToast(input.getText().toString() + " added");
                            presenter.setBooleanFalse();
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        }

        /**
         * Menu button to clear all list items.
         */
        if (id == R.id.action_clear) {
            if (uuid.equals("No mirror chosen")) {
                makeToast("Please choose a mirror first");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Clear list items ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.updateList("delete-list", "empty");

                        /**
                         * This for loop here is used in the same way as described in the previous comment,
                         * except the fact that this is used to clear all the items in a list.
                         */
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (presenter.getBoolean()) {
                                break;
                            }
                        }
                        if (!presenter.getBoolean()) {
                            makeToast("Error deleting list, please try again");
                        } else if (presenter.getBoolean()) {
                            makeToast("List Deleted");
                            presenter.setBooleanFalse();
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method here specifically deals with deletion of items from the shopping list.
     *
     * @param selectedItem is the item in the list that has been selected by the user.
     */
    public void removeElement(final String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove " + selectedItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateList("delete", selectedItem);

                /**
                 * This for loop here is used in the same way as described in the previous comment,
                 * except the fact that this is used to clear an item from the list.
                 */
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (presenter.getBoolean()) {
                        break;
                    }
                }
                if (!presenter.getBoolean()) {
                    makeToast("Error removing item, please try again");
                }
                if (presenter.getBoolean()) {
                    makeToast(selectedItem + " Removed");
                    presenter.setBooleanFalse();
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     *  Method used to display toast messages.
     *
     * @param message the message that will be displayed in the toast.
     */
    public void makeToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the List view with the updated list.
     */
    public void updateListView(){
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
            }
        });
    }
}

