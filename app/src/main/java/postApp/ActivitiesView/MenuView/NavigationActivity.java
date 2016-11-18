package postApp.ActivitiesView.MenuView;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import adin.postApp.R;
import postApp.Activities.NavigationActivity.Fragments.Postit;
import postApp.Presenters.MenuPresenters.NavigationPresenter;
import postApp.DataHandlers.Settings.Settings;

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
    String user;
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
        presenter = new NavigationPresenter(this);

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
        presenter.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return presenter.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onOptionsItemSelected(item);
    }
    /*
    Used for changing from the Drawer functionality to a back button functionality
     */
    public void toggleDrawerUse(boolean useDrawer) {
        presenter.toggleDrawerUse(useDrawer);
    }


    /*
    This is the navigationbar items switching fragments when clicked
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return presenter.onOptionsItemSelected(item);
    }
/*
Getters and setter for all the current string that will be used to passing data
Having these to access the same from all fragments
 */
public String getMirror(){
    return presenter.getMirror();
}
    public void setMirror(String UUID){
        presenter.setMirror(UUID);
    }
    public String getBus(){
        return presenter.getBus();
    }
    public void setBus(String busid){
        presenter.setBus(busid);
    }
    public String getWeather(){
        return presenter.getWeather();
    }
    public void setWeather(String W){
        presenter.setWeather(W);
    }
    public String getNews(){
        return presenter.getNews();

    }
    public void setNews(String N){
        presenter.setNews(N) ;
    }
    public String getUUID(){
        return presenter.getUUID();
    }
    public String getUser(){
        return presenter.getUser();
    }
}
