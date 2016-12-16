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

package postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.HashMap;
import java.util.List;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.FAQPresenter;

/**
 * Class responsible for the FAQ layout. Main purpose is to make a new FAQAdapter with the Hasmap and list of items we want to fill
 * the expandable list with
 */

public class FAQView extends Fragment {
    View myView;
    ExpandableListView Exp_list;
    FAQAdapter adapter;
    FAQPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.faq, container, false);
        presenter = new FAQPresenter(this);
        Exp_list = (ExpandableListView) myView.findViewById(R.id.expandablelist);
        presenter.setAdapter();
        Exp_list.setAdapter(adapter);
        return myView;
    }

    /**
     * Method used to instantiate a new adapter with the Hasmap and list
     * @param QuestionCat The hashmap of all the Categories
     * @param QuestionAns List of all the answers
     */
    public void SetAdapter(HashMap<String, List<String>> QuestionCat, List<String> QuestionAns) {
        adapter = new FAQAdapter(getActivity(), QuestionCat, QuestionAns);
    }
    /**
     * On resume sets title to to FAQ
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("FAQ");
    }
}
