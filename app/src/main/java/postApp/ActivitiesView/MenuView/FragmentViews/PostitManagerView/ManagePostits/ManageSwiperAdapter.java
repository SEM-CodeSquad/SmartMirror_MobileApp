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

package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Class switches between fragments when swiped, Each fragments has different values that are taken from the json object
 */

class ManageSwiperAdapter extends FragmentStatePagerAdapter {

    private JSONArray json;

    /**
     * Constructor for the manageSwiperAdapter
     * @param fm the fragmentmanager
     * @param json the json array of all postits
     */
    ManageSwiperAdapter(FragmentManager fm, JSONArray json){
       super(fm);
        this.json = json;
    }

    /**
     * This method is switching between the PageFragment. We create a bundle in this method and put values for each PageFragment
     * @param position the position in the fragment
     * @return the fragment
     */
    @Override
    public Fragment getItem(int position) {
        // we get a json object at this fragment position
        JSONObject finalobj = (JSONObject) json.get(position);
        Fragment fragment = new PageFragment();
        //parse date
        Long timestamp = Long.parseLong(finalobj.get("Timestamp").toString());
        Date date = new Date ();
        date.setTime(timestamp*1000);
        //A bundle and add all the values to it
        Bundle bundle = new Bundle();
        bundle.putString("Timestamp", date.toString());
        bundle.putString("ID", finalobj.get("PostitID").toString());
        bundle.putString("Text", finalobj.get("Text").toString());
        bundle.putString("Color", finalobj.get("Color").toString());
        //set arguments to the fragment
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * @return The json.size which indicates how many fragments we should be able to swipe(how many postits there are)
     */
    @Override
    public int getCount() {
        return json.size();
    }
}
