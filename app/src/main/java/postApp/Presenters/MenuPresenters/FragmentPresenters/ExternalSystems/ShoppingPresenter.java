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

package postApp.Presenters.MenuPresenters.FragmentPresenters.ExternalSystems;


import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExternalSystems.ShoppingHandler;


/*
 * The shopping presenter acts as a controller for the view class as well as handing over data to
 * the ShoppingHandler class.
 */
public class ShoppingPresenter {
    private ShoppingHandler handler;
    private ShoppingView view;

    /**
     * The Shopping presenter interacting with the handler for the data and logic. Instantiates a handler.
     * @param shoppingView the view
     * @param uuid the UUID
     */
    public ShoppingPresenter(ShoppingView shoppingView, String uuid){
        this.view = shoppingView;
        this.handler = new ShoppingHandler(this.view, this,uuid);
    }

    /**
     * Method that calls the handler update list function
     * @param requestType the Requuest type
     * @param item The item
     */
    public void updateList(String requestType, String item){
        handler.updateList(requestType, item);
    }

    /**
     * @return a linkedlist from the handler that is the shopping list
     */
    public LinkedList<String> getShoppingList(){
        return handler.getShoppingList();
    }

    /**
     * @return true or false
     */
    public boolean getBoolean(){
       return handler.getBoolean();
    }

    /**
     * Sets the boolean to false
     */
    public void setBooleanFalse(){
        handler.setBooleanFalse();
    }

}
