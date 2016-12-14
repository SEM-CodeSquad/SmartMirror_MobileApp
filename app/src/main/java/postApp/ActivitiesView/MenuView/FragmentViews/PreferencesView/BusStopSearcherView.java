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

package postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.BusStopSearcherPresenter;

/**
    Class for searching a stop using västtrafiks api and showing a view for it
 */
public class BusStopSearcherView extends Fragment {
    View myView;
    public ListView listView;
    EditText SearchBox;
    BusStopSearcherPresenter presenter;

    /**
     * When we switch to this fragment we initialize the views, onclicklisteners etc
     * @param inflater used for switching layout
     * @param container the shared viewgroup
     * @param savedInstanceState saved state
     * @return the view of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.searchstop, container, false);
        //initialize the views
        listView = (ListView) myView.findViewById(R.id.listview);
        SearchBox = (EditText) myView.findViewById(R.id.txtsearch);
        presenter = new BusStopSearcherPresenter(this);

        //set a textchanged listener to searchbox
        SearchBox.addTextChangedListener(new TextWatcher() {

            /**
             * For a textwatcher you need to implement these methods they are not used
             * @param s not used
             * @param start not used
             * @param count not used
             * @param after not used
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            /**
             * @param s not used
             * @param start not used
             * @param count not used
             * @param before not used
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
            }

            /**
             * After the text is changed we use this method to call methods in the presenter with diffrent outcomes
             * @param s the string that is written
             */
            @Override
            public void afterTextChanged(Editable s) {
                //show nothing if not more then 2 letters
                if (s.length() <= 2 && s.length() > 0) {
                    presenter.EmptyList();
                }
                // if more than 2 letters are typed we start a travelbysearch which fetches västtrafiks stops.
                else {
                    if(s.length() > 2){
                        presenter.GetStops(s.toString());
                    }
                }
            }

        });

        /**
         * if we chose a item on the list then we publish that one.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                presenter.OnBusClick((listView.getItemAtPosition(i).toString()));
            }
        });
        /**
         * if the searchbox has no focus hide it!
         */
        SearchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.HideKeyboard(v);
                }
            }
        });
        return myView;

    }

    /**
     * Just when this fragment is resumed we call the superfunctions onResume, and set title to bustops.
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Search for Bus Stops");
    }

    /**
     * When we pick a busstop we switch fragments and swich back to a normal drawer and set bus to bus stop
     */
    public void OnBusClick(String bus){
        ((NavigationActivity) getActivity()).setBus(bus);
        ((NavigationActivity) getActivity()).SetBusID(presenter.getBusID(bus));
        //set it back to a drawer instead of backbutton
        ((NavigationActivity) getActivity()).toggleDrawerUse(true);
        //switch back to settings
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsView()).commit();
    }
    /**
     * if no mirror is chosen we display this with a toast
     */
    public void NoMirror(){
        Toast.makeText(getActivity(), "Please chose a mirror first.", Toast.LENGTH_SHORT).show();

    }
    /**
     * Hides keyboard
     * @param view the view where we are hiding the keyboard
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

