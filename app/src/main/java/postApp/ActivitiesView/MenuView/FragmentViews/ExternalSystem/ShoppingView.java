/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

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

/*
 * This class here initiates the shopping.xml which is the fragment view for the Shopping List
 * RFC that we are implementing. For consistency purposes, the list that holds the shopping
 * list items will be mentioned as 'shopping list' whereas references to the ShoppingList object
 * will be mentioned as 'ShoppingList Obj'
 */
public class ShoppingView extends Fragment {
    View myView;
    ShoppingPresenter presenter;
    ArrayAdapter<String> adapter = null;          /* A way to handle a list or array of objects and map them to a row */
    ListView listView = null;
    String uuid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.uuid = ((NavigationActivity) getActivity()).getMirror(); // TODO Use this method and pass it on.
        presenter = new ShoppingPresenter(this,uuid);
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.shopping, container, false);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, presenter.getShoppingList());
        listView = (ListView) myView.findViewById(R.id.listView);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter);

        /*
         * This function sets each row of a listView(each item of a shopping list) a clickable item which will open
         * a dialog box that prompts the user whether or not he/she wants to delete the item.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(presenter.getShoppingList().get(position).trim())) {     /*A check done for integrity purposes*/
                    removeElement(selectedItem);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Error Removing Element", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*
         * The purpose of having a floating button is that the user will use this button to publish
         * a new shopping list. Ater tapping the floating button, it will publish the list items along
         * with the list title and will disappear. The title shall be saved internally and will remain
         * uneditable as the RFC doesnt state a way to modify/fetch the title of a shopping list.
         * This button will reappear upon deletion of the already created shopping list.
         */
        return myView;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Shopping List");
    }

    /*
     * This method uses a menu resource file to add 2 buttons to the app bar (add Item & delete list).
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*
     * The app bar items(buttons in our case) are handled here. Appropriate methods are called
     * and published to the RFC topic using the id of each button.
     * Upon completion of each list update, the updateList method is called which updates the
     * shoppingList item linkedList with the shopping List received by fetch calls made to the RFC topic.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            if (uuid == "No mirror chosen") {
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
                        if (!presenter.getBoolean()){
                            makeToast("Error adding item, please try again");
                        }
                        else if (presenter.getBoolean()) {
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
                        System.out.println("The list is " + presenter.getShoppingList().toString());
                    }
                });
                builder.show();
                return true;
            }
        }

        if (id == R.id.action_clear) {
            if (uuid == "No mirror chosen") {
                makeToast("Please choose a mirror first");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Clear list items ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.updateList("delete-list", "empty");
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
                        if(!presenter.getBoolean()){
                            makeToast("Error deleting list, please try again");
                        }
                        else if (presenter.getBoolean()) {
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

    /*
     * This method here specifically deals with deletion of items from the shopping list.
     */
    public void removeElement(final String selectedItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove " + selectedItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateList("delete", selectedItem);
                for (int i=0; i<10; i++){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (presenter.getBoolean()){
                        break;
                    }
                }
                if(!presenter.getBoolean()){
                    makeToast("Error removing item, please try again");
                }
                if (presenter.getBoolean()){
                    makeToast(selectedItem +" Removed");
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
    public void makeToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public ListView getListView(){
        return this.listView;
    }
    public ArrayAdapter getAdapter(){
        return this.adapter;
    }


}

