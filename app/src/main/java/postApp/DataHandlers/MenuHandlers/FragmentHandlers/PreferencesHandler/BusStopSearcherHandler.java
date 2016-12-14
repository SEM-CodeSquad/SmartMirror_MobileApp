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

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.ParseJson;
import postApp.DataHandlers.AppCommons.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.AppCommons.Vasttrafik.TravelBySearch;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.BusStopSearcherPresenter;

/**
 * Class acting as the handler for the busstopsearcher view and interacts with the presenter
 */
public class BusStopSearcherHandler {
    private String auth;
    private String emptylist[] = new String[0];
    private ParseJson js;
    private BusStopSearcherPresenter BusStopSearcherPresenter;

    /**
     * Constructor that generates a access codes and sets auth to it. Instantiates a ParseJson class aswell
     * @param BusStopSearcherPresenter the presenter
     */
    public BusStopSearcherHandler(BusStopSearcherPresenter BusStopSearcherPresenter){
        this.BusStopSearcherPresenter = BusStopSearcherPresenter;
        // here we use the generateaccesscode class to get a new access code for the api
        GenerateAccessCode gen = new GenerateAccessCode();
        //we set the access to auth
        try {
            auth = gen.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //json parser for search
        js = new ParseJson();
    }

    /**
     * Function that creates a new TravelBySearch and executes it with the String S that is what the user searched for
     * This method also calls the presenter method for settings the list view but first parses it with ParseJson class.
     * @param s the user search
     */
    public void GetStops(String s){
        try {
            TravelBySearch trav = new TravelBySearch();
            //we execute the async task
            trav.execute(auth, s);
            //we parse the json data with the data we get from vässtrafik and the string that was searched for
            BusStopSearcherPresenter.setListview(initList(js.parseSearch(trav.get(), s)));
        } catch (Exception v) {
            v.printStackTrace();
        }
    }

    /**
     * Gets a bus stop unique ID from the jsonbuilder class
     * @param S the string we search for
     * @return the busID
     */
    public String getStopID(String S){
        return js.getBusIDfromSearch(S);
    }

    /**
     * To empty the arraylist we call this class
     */
    public void EmptyList(){
        BusStopSearcherPresenter.setListview(initList(emptylist));
    }

    /**
     * Converts a String[] into a arraylist
     * @param vastitems the String[] of bus stop names
     * @return the ArrayList of the vastitems string
     */
    private ArrayList<String>  initList(String[] vastitems){
        return new ArrayList<>(Arrays.asList(vastitems));
    }
}
