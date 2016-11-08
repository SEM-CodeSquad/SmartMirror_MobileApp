package postApp.Controllers;
import android.app.FragmentManager;
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


import java.util.UUID;

import adin.postApp.R;
import postApp.Controllers.Fragments.About;
import postApp.Controllers.Fragments.Contact;
import postApp.Controllers.Fragments.MirrorPostit;
import postApp.Controllers.Fragments.Postit;
import postApp.Controllers.Fragments.Preferences;
import postApp.Controllers.Fragments.QrCode;
import postApp.Controllers.Fragments.RemovePostit;
import postApp.Controllers.Fragments.SettingsFrag;

/*
Oncreate method for navigationactivity, starts a navigation drawer and sets the toolbar, functionality etc.
 */
public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    String mirrorID = "No mirror chosen";
    String newsID = "No news chosen";
    String busID = "No bus or tram stop chosen";
    String weatherID = "No city chosen";
    String user;
    UUID idOne = UUID.randomUUID();
    View.OnClickListener mOriginalListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Publish PostIt");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        if(savedInstanceState == null) {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, new Postit()).commit();
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
            //The key argument here must match that used in the other activity
        }

        mOriginalListener = toggle.getToolbarNavigationClickListener();
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);


    }
/*
Back pressed obv, not implemented that well yet
 */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
            getSupportActionBar().setTitle("SettingsFrag");
        }
        if (id == R.id.pairmirror) {
            mOriginalListener = toggle.getToolbarNavigationClickListener();
            toggleDrawerUse(false);
            getSupportActionBar().setTitle("Mirror ID");
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCode()).commit();
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    Used for changing from the Drawer functionality to a back button functionality
     */
    public void toggleDrawerUse(boolean useDrawer) {
        // Enable/Disable the icon being used by the drawer
        toggle.setDrawerIndicatorEnabled(useDrawer);
        final FragmentManager fragment = getFragmentManager();
        // Switch between the listeners as necessary
        if(useDrawer) {
            toggle.setToolbarNavigationClickListener(mOriginalListener);
        }
        else
            toggle.setHomeAsUpIndicator(R.drawable.back);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
                getSupportActionBar().setTitle("Settings");
                toggleDrawerUse(true);

            }
        });
    }


    /*
    This is the navigationbar items switching fragments when clicked
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragment = getFragmentManager();

        if (id == R.id.nav_postit){
            fragment.beginTransaction().replace(R.id.content_frame, new Postit()).commit();
            getSupportActionBar().setTitle("Publish PostIt");
        } else if (id == R.id.nav_mirror) {
            fragment.beginTransaction().replace(R.id.content_frame, new MirrorPostit()).commit();
            getSupportActionBar().setTitle("Mirror");
        } else if (id == R.id.nav_remove) {
            fragment.beginTransaction().replace(R.id.content_frame, new RemovePostit()).commit();
            getSupportActionBar().setTitle("Remove PostIt");
        } else if (id == R.id.nav_contact) {
            fragment.beginTransaction().replace(R.id.content_frame, new Contact()).commit();
            getSupportActionBar().setTitle("Contact Us");
        } else if (id == R.id.nav_about) {
            fragment.beginTransaction().replace(R.id.content_frame, new About()).commit();
            getSupportActionBar().setTitle("About");
        }
        else if (id == R.id.nav_preferences) {
            fragment.beginTransaction().replace(R.id.content_frame, new Preferences()).commit();
            getSupportActionBar().setTitle("Preferences");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
Getters and setter for all the current string that will be used to passing data
Having these to access the same from all fragments
 */
    public String getMirror(){
        return mirrorID;
    }
    public void setMirror(String UUID){
        this.mirrorID = UUID;
    }
    public String getBus(){
        return busID;
    }
    public void setBus(String busid){
        this.busID = busid;
    }
    public String getWeather(){
        return weatherID;
    }
    public void setWeather(String W){
        weatherID = W ;
    }
    public String getNews(){
        return newsID;
    }
    public void setNews(String N){
        newsID = N ;
    }
    public String getUUID(){
        return idOne.toString();
    }
    public String getUser(){
        return user;
    }


}
