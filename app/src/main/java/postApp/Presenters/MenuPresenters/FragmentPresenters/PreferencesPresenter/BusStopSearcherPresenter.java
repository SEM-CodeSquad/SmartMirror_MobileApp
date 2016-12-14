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

package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import android.view.View;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.BusStopSearcherView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.BusStopSearcherHandler;

/**
 * Class for interacting with the busstopsearcherview and the busstopsearcherhandler
 */
public class BusStopSearcherPresenter {

    private BusStopSearcherView BusStopSearcherView;
    private BusStopSearcherHandler BusStopSearcherHandler;

    /**
     * The constructor for this class that injects this presenter into the handler and sets the view
     * @param BusStopSearcherView the view
     */
    public BusStopSearcherPresenter(BusStopSearcherView BusStopSearcherView){
        this.BusStopSearcherView = BusStopSearcherView;
        this.BusStopSearcherHandler = new BusStopSearcherHandler(this);
    }

    /**
     * To get all stops we call the handlers function
     * @param s The stop name we search for
     */
    public void GetStops(String s){
        BusStopSearcherHandler.GetStops(s);
    }

    /**
     * To empty list we call the handlers fucntion to emptylist
     */
    public void EmptyList(){
        BusStopSearcherHandler.EmptyList();
    }

    /**
     * When clicked a bus we call the views method for the bus clicked
     * @param bus the bus
     */
    public void OnBusClick(String bus){
        BusStopSearcherView.OnBusClick(bus);
    }

    /**
     * To hide the views keyboard
     * @param V the view
     */
    public void HideKeyboard(View V){
        BusStopSearcherView.hideKeyboard(V);
    }

    /**
     * To show that no mirror is chosen we call this views class
     */
    public void NoMirror(){
        BusStopSearcherView.NoMirror();
    }

    /**
     * To get the busID of the busstop
     * @param s the busstop name
     * @return the id string
     */
    public String getBusID(String s){
        return BusStopSearcherHandler.getStopID(s);
    }

    /**
     * To populate the listview with the adapter
     * @param list the list used in the adapter
     */
    public void setListview(ArrayList<String> list ) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BusStopSearcherView.getActivity(),
                R.layout.listitem, R.id.txtitem, list);
        BusStopSearcherView.listView.setAdapter(adapter);
    }
}
