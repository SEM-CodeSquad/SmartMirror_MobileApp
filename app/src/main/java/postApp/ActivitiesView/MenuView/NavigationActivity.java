package postApp.ActivitiesView.MenuView;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
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
import postApp.ActivitiesView.MenuView.FragmentViews.ExternalSystem.ShoppingView;
import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.AboutView;
import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.ContactView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.HidePostitView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.ManagePostitsView;
import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.PreferencesView;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.Presenters.MenuPresenters.NavigationPresenter;

/*
Oncreate method for navigationactivity, starts a navigation drawer and sets the toolbar, functionality etc.
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
        presenter = new NavigationPresenter(this);

        if (presenter.getMirror() == "No mirror chosen") {
            UnsuccessfulRegister();
        }
        //here we just get the user that logged in from before using a bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            presenter.UpdateSettings(extras.getString("bus"), extras.getString("busid"), extras.getString("news"), extras.getString("weather"), extras.getString("user"));
            // /The key argument here must match that used in the other activity
        }

        navigationView.setNavigationItemSelectedListener(this);


    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (getFragmentManager().findFragmentByTag("QRFRAG") != null && getFragmentManager().findFragmentByTag("QRFRAG").isVisible()) {
            toggleDrawerUse(true);
        } else if (getFragmentManager().findFragmentByTag("BUSFRAG") != null && getFragmentManager().findFragmentByTag("BUSFRAG").isVisible()) {
            toggleDrawerUse(true);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {

            getFragmentManager().popBackStack();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView usrnamenav = (TextView) findViewById(R.id.usernavdraw);
        usrnamenav.setText(presenter.getUser());
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            //switch screen to QrCodeView2 frame
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView(), "QRFRAG").addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void UnsuccessfulRegister() {
        new AlertDialog.Builder(this)
                .setMessage("Mirror is not paired, would you like to pair now?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportActionBar().setTitle("Mirror ID");
                        //switch screen to QrCodeView2 frame
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView()).addToBackStack(null).commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    /*
 This is the navigationbar items switching fragments when clicked
  */
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = getFragmentManager();
        //when a diffrent navigation item is clicked we do a specific thing, eg when nav_postit(postit icon) is clicked
        //we switch fragment to postit and set title of actionbar to publish posit.
        //same thing for all navigationitems
        if (id == R.id.nav_postit) {
            fragment.beginTransaction().replace(R.id.content_frame, new PostitView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Publish PostIt");
        } else if (id == R.id.nav_mirror) {
            fragment.beginTransaction().replace(R.id.content_frame, new ManagePostitsView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Mirror");
        } else if (id == R.id.nav_contact) {
            fragment.beginTransaction().replace(R.id.content_frame, new ContactView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("ContactView Us");
        } else if (id == R.id.nav_about) {
            fragment.beginTransaction().replace(R.id.content_frame, new AboutView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("AboutView");
        } else if (id == R.id.nav_preferences) {
            fragment.beginTransaction().replace(R.id.content_frame, new PreferencesView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("PreferencesView");
        } else if (id == R.id.nav_shoppinglist) {
            fragment.beginTransaction().replace(R.id.content_frame, new ShoppingView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Shopping List");
        } else if (id == R.id.nav_filterpost) {
            fragment.beginTransaction().replace(R.id.content_frame, new HidePostitView()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("Filter Postits");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    Getters and setter for all the current string that will be used to passing data
    Having these to access the same from all fragments
     */
    public String getMirror() {
        return presenter.getMirror();
    }

    public void setMirror(String UUID) {
        presenter.setMirror(UUID);
    }

    public String getBus() {
        return presenter.getBus();
    }

    public void setBus(String busid) {
        presenter.setBus(busid);
    }

    public String getWeather() {
        return presenter.getWeather();
    }

    public void setWeather(String W) {
        presenter.setWeather(W);
    }

    public String getNews() {
        return presenter.getNews();
    }

    public void setNews(String N) {
        presenter.setNews(N);
    }

    public String getUUID() {
        return presenter.getUUID();
    }

    public String getUser() {
        return presenter.getUser();
    }

    public void SetBusID(String busID) {
        presenter.SetBusID(busID);
    }

    public String GetBusID() {
        return presenter.GetBusID();
    }


    /*
    Used for changing from the Drawer functionality to a back button functionality
     */

    public void toggleDrawerUse(boolean useDrawer) {
        presenter.toggleDrawerUse(useDrawer);
    }
}
