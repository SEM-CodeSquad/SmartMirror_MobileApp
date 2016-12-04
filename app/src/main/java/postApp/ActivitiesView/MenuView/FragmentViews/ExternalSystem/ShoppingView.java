package postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;


import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems.ShoppingPresenter;


public class ShoppingView extends Fragment {
    View myView;
    ShoppingPresenter presenter;
    TextView listTitle;
    ArrayAdapter<String> adapter = null;
    ListView listView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new ShoppingPresenter(this);
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.shopping, container, false);

        listTitle = (TextView) myView.findViewById(R.id.editText);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, presenter.getShoppingList());
        listView = (ListView) myView.findViewById(R.id.listView);
        presenter.saveTitle("ListNimish");
        listTitle.setText(presenter.fetchTitle());
        presenter.updateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(presenter.getShoppingList().get(position).trim())) {
                    removeElement(selectedItem, position);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Error Removing Element", Toast.LENGTH_LONG).show();
                }
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                listTitle.setEnabled(false);
                presenter.saveTitle(listTitle.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(),"Sending List...", Toast.LENGTH_LONG).show();
            }
        });
        listTitle.setHint("Enter list Title here");
        return myView;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Shopping List");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_add){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Item");
            final EditText input = new EditText(getActivity());
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.addItem(input.getText().toString());
                    presenter.updateList();
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
        if(id == R.id.action_clear){ AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Clear Entire List");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.clearList();
                    listTitle.setEnabled(true);
                    presenter.updateList();
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

        return super.onOptionsItemSelected(item);
    }

    public void removeElement(String selectedItem, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove " + selectedItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.removeElement(position);
                presenter.updateList();
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

    public ListView getListView(){
        return this.listView;
    }
    public ArrayAdapter getAdapter(){
        return this.adapter;
    }
    public void setTitle(String title){
        this.listTitle.setText(title);
    }
}
