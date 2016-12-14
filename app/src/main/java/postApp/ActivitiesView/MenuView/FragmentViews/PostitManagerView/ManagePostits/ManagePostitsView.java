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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.ManagePostitsPresenter;

/**
 * Class responsible for loading all the postits
 */
public class ManagePostitsView extends Fragment {
    ManagePostitsPresenter presenter;
    ViewPager viewPager;
    ProgressDialog progress;

    /**
     * Oncreatemethod that instantiates all the views and starts fetching postits
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the savedinstance
     * @return the view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.mirror_postit, container, false);
        progress = new ProgressDialog(getActivity());
        presenter = new ManagePostitsPresenter(this);
        viewPager = (ViewPager) myView.findViewById(R.id.view_pager);
        FetchPost();
        return myView;
    }

    /**
     * Method to start a ManagerSwiperAdapter and settings the viewpager to that adapter
     * @param PostIts the postits passed in to the ManagerSwiperAdapter
     */
    public void UpdateGuiPost(JSONArray PostIts){
        ManageSwiperAdapter ManageSwiperAdapter = new ManageSwiperAdapter(getChildFragmentManager(), PostIts);
        viewPager.setAdapter(ManageSwiperAdapter);
    }

    /**
     * Calls the presenters method for fetching postits
     */
    public void FetchPost(){
        presenter.FetchPost(((NavigationActivity) getActivity()).getUser());
    }

    /**
     * Sets title on the actionbar on resume
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Manage Mirror Post-Its");
    }

    /**
     * displays a progress dialog that says loading postits
     */
    public void Loading(){
        progress.setMessage("Loading Postits");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    /**
     * Dismisses the progress dialog
     */
    public void DoneLoading(){
        progress.dismiss();
    }
}
