package postApp.Activities.NavigationActivity;
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
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.UUID;

import adin.postApp.R;
import postApp.Activities.NavigationActivity.Fragments.About;
import postApp.Activities.NavigationActivity.Fragments.Contact;
import postApp.Activities.NavigationActivity.Fragments.MirrorPostit;
import postApp.Activities.NavigationActivity.Fragments.Postit;
import postApp.Activities.NavigationActivity.Fragments.Preferences;
import postApp.Activities.NavigationActivity.Fragments.QrCode;
import postApp.Activities.NavigationActivity.Fragments.RemovePostit;
import postApp.Activities.NavigationActivity.Fragments.SettingsFrag;
import postApp.DataHandlers.Network.DataBase.Settings;

/*
Oncreate method for navigationactivity, starts a navigation drawer and sets the toolbar, functionality etc.
 */
public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    //since we have 1 activity shared by several fragments we have the variables accesible by all fragments since they
    //share activity
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
        if(savedInstanceState == null) {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, new Postit()).commit();
        }
        //here we just get the user that logged in from before using a bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
            // /The key argument here must match that used in the other activity
        }
        //saving the originallistnere which is a drawer when we switch to a back button later
        mOriginalListener = toggle.getToolbarNavigationClickListener();
        // check if the DrawerLayout is open or closed after the instance state of the DrawerLayout has been restored.
        toggle.syncState();

        //Connecting to our database and getting the settings for this user, then we set the bus, weather and news to the users chosen settings from before.
        Settings set = new Settings(user);
        String[] db = set.getSettings();
        setBus(db[0]);
        setWeather(db[1]);
        setNews(db[2]);



        navigationView.setNavigationItemSelectedListener(this);


    }
/*
Back pressed on phone, closes the drawer if its open
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
        TextView usrnamenav = (TextView)findViewById(R.id.usernavdraw);
        usrnamenav.setText(user);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if settings is pressed we opens the settings fragment and set title to settings
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
            getSupportActionBar().setTitle("Settings");
        }
        if (id == R.id.pairmirror) {
            //since pairmirror uses a back button we save the original listener which is a drawer
            mOriginalListener = toggle.getToolbarNavigationClickListener();
            //turn of the drawer to a backbutton
            toggleDrawerUse(false);
            //set the title
            getSupportActionBar().setTitle("Mirror ID");
            //switch screen to QrCode frame
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
            toggle.setHomeAsUpIndicator(R.drawable.back); //set the icon to a back button
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //listeners that goes back to settings whne pressed
                fragment.beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
                getSupportActionBar().setTitle("Settings"); //sets the title to settings
                toggleDrawerUse(true);//activates the drawer again

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
        //when a diffrent navigation item is clicked we do a specific thing, eg when nav_postit(postit icon) is clicked
        //we switch fragment to postit and set title of actionbar to publish posit.
        //same thing for all navigationitems
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
