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

package postApp.ActivitiesView.MenuView;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import adin.postApp.R;
import postApp.ActivitiesView.AuthenticationView.LoginActivity;
import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.AboutView;
import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.ContactView;
import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.FAQView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.HidePostitView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.ManagePostitsView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.PreferencesView;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.Presenters.MenuPresenters.NavigationPresenter;

/**
 * This class is shared by all the fragments, it handles the toolbar and navigationdrawer
 */
public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    FragmentManager fragment;
    private NavigationPresenter presenter;
    View.OnClickListener mOriginalListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout
        setContentView(R.layout.activity_main);
        //initilize the toolbar and then set it and then set title.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Publish PostIt");
        //initilize the drawer and a toggle which opens and closes the drawer and then add it as a drawerlistener
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        //if its not a saved instancestate we set the iniatial frame as a postit
        if (savedInstanceState == null) {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, new PostitView()).commit();
        }

        //saving the originallistnere which is a drawer when we switch to a back button later
        mOriginalListener = toggle.getToolbarNavigationClickListener();
        // check if the DrawerLayout is open or closed after the instance state of the DrawerLayout has been restored.
        toggle.syncState();
        presenter = new NavigationPresenter(this, mOriginalListener);

        //if no mirror is chosen we call this method
        if (presenter.getMirror().equals("No mirror chosen")) {
            presenter.notPaired();

        }
        //here we just get the user that logged in from before using a bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            presenter.UpdateSettings(extras.getString("bus"), extras.getString("busid"), extras.getString("news"), extras.getString("weather"), extras.getString("user"));
            // /The key argument here must match that used in the other activity
        }

        navigationView.setNavigationItemSelectedListener(this);


    }

    /**
     * Methd used by all the fragments for going back. Handles both the drawer layouts going back button and the back pressed on the phone
     */
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //If we go back from the qr fragment then we set toggledrawer to true, which goes back from a back button to a navigation drawer button
        if (getFragmentManager().findFragmentByTag("QRFRAG") != null && getFragmentManager().findFragmentByTag("QRFRAG").isVisible()) {
            toggleDrawerUse(true);
        }
        //If we go back from the bus fragment then we set toggledrawer to true, which goes back from a back button to a navigation drawer button
        else if (getFragmentManager().findFragmentByTag("BUSFRAG") != null && getFragmentManager().findFragmentByTag("BUSFRAG").isVisible()) {
            toggleDrawerUse(true);
        }
        // if the drawer is open, going back closes it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //otherwise we check the backstackentycount for the fragmentmanager and then popbackstack, which goes back to the previous fragment
        else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }

    }

    /**
     * When this activity is created this method is automatically called which inflates the menu. And sets the name of the TextView to the user logged in
     * @param menu the menu
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView usrnamenav = (TextView) findViewById(R.id.usernavdraw);
        usrnamenav.setText(presenter.getUser());
        return true;
    }

    /**
     * This handles all the click on the Options menu, when a option is pressed we switch fragments.
     * @param item the item
     * @return super class function for this item
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
        */
        int id = item.getItemId();
        //if settings is pressed we opens the settings fragment and set title to settings
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Settings");

        }
        if (id == R.id.pairmirror) {
            //since pairmirror uses a back button we save the original listener which is a drawer
            mOriginalListener = toggle.getToolbarNavigationClickListener();
            //turn of the drawer to a backbutton
            toggleDrawerUse(false);
            //set the title
            getSupportActionBar().setTitle("Mirror ID");
            //switch screen to QrCodeView frame
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView(), "QRFRAG").addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }


    public void NotPaired() {
        new AlertDialog.Builder(this)
                .setMessage("Mirror is not paired, would you like to pair now?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportActionBar().setTitle("Mirror ID");
                        //switch screen to QrCodeView2 frame
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView()).addToBackStack(null).commit();
                        toggleDrawerUse(false);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    /**
     * This handles all the clicks on the navigation drawer, when a item is pressed this is called and then we switch fragments with this method
     * @param item the item pressed
     * @return true
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = getFragmentManager();
        //when a diffrent navigation item is clicked we do a specific thing, eg when nav_postit(postit icon) is clicked
        //we switch fragment to postit and set title of actionbar to publish posit.
        //same thing for all navigationitems
        if (id == R.id.nav_postit) {
            fragment.beginTransaction().replace(R.id.content_frame, new PostitView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Publish Post-It");
        } else if (id == R.id.nav_mirror) {
            fragment.beginTransaction().replace(R.id.content_frame, new ManagePostitsView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Mirror");
        } else if (id == R.id.nav_contact) {
            fragment.beginTransaction().replace(R.id.content_frame, new ContactView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Contact Us");
        } else if (id == R.id.nav_about) {
            fragment.beginTransaction().replace(R.id.content_frame, new AboutView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("About");
        } else if (id == R.id.nav_preferences) {
            fragment.beginTransaction().replace(R.id.content_frame, new PreferencesView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Preferences");
        } else if (id == R.id.nav_shoppinglist) {
            fragment.beginTransaction().replace(R.id.content_frame, new ShoppingView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Shopping List");
        } else if (id == R.id.nav_filterpost) {
            fragment.beginTransaction().replace(R.id.content_frame, new HidePostitView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Filter Post-Its");
        }
        // If logout is pressed we switch activiy to LoginActivity
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_help) {
            fragment.beginTransaction().replace(R.id.content_frame, new FAQView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("FAQ");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //close drawer when done
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * @return Mirror ID
     */
    public String getMirror() {
        return presenter.getMirror();
    }

    /**
     * Sets mirror ID
     * @param UUID the id
     */
    public void setMirror(String UUID) {
        presenter.setMirror(UUID);
    }

    /**
     * @return Bus name
     */
    public String getBus() {
        return presenter.getBus();
    }
    /**
     * Sets bus ID
     * @param busid the id
     */
    public void setBus(String busid) {
        presenter.setBus(busid);
    }
    /**
     * @return weather city
     */
    public String getWeather() {
        return presenter.getWeather();
    }
    /**
     * Sets weather city
     * @param W The city
     */
    public void setWeather(String W) {
        presenter.setWeather(W);
    }
    /**
     * @return news source
     */
    public String getNews() {
        return presenter.getNews();
    }
    /**
     * Sets news source
     * @param N The source
     */
    public void setNews(String N) {
        presenter.setNews(N);
    }
    /**
     * @return user
     */
    public String getUser() {
        return presenter.getUser();
    }
    /**
     * Sets bus ID
     * @param busID The ID
     */
    public void SetBusID(String busID) {
        presenter.SetBusID(busID);
    }

    /**
     * @return Bus ID
     */
    public String GetBusID() {
        return presenter.GetBusID();
    }

    /**
     * Used for changing from the Drawer functionality to a back button functionality
     * @param useDrawer true for having a drawer, false for backbutton
     */
    public void toggleDrawerUse(boolean useDrawer) {
        presenter.toggleDrawerUse(useDrawer);
    }
}
