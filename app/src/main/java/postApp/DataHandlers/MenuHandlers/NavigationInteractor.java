package postApp.DataHandlers.MenuHandlers;

import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

import adin.postApp.R;
import postApp.Activities.NavigationActivity.Fragments.About;
import postApp.Activities.NavigationActivity.Fragments.Contact;
import postApp.Activities.NavigationActivity.Fragments.MirrorPostit;
import postApp.Activities.NavigationActivity.Fragments.Postit;
import postApp.Activities.NavigationActivity.Fragments.Preferences;
import postApp.Activities.NavigationActivity.Fragments.QrCode;
import postApp.Activities.NavigationActivity.Fragments.RemovePostit;
import postApp.Activities.NavigationActivity.Fragments.SettingsFrag.SettingsFrag;
import postApp.ActivitiesView.MenuView.NavigationActivity;

/**
 * Created by adinH on 2016-11-18.
 */

public class NavigationInteractor {

    String mirrorID = "No mirror chosen";
    String newsID = "No news chosen";
    String busID = "No bus or tram stop chosen";
    String weatherID = "No city chosen";
    String user;
    UUID idOne = UUID.randomUUID();
    View.OnClickListener mOriginalListener;

    postApp.ActivitiesView.MenuView.NavigationActivity NavigationActivity;


    public NavigationInteractor(NavigationActivity NavigationActivity){
        this.NavigationActivity = NavigationActivity;
    }
    /*
   Back pressed on phone, closes the drawer if its open
    */
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) NavigationActivity.findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            NavigationActivity.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        NavigationActivity.getMenuInflater().inflate(R.menu.main, menu);
        TextView usrnamenav = (TextView)NavigationActivity.findViewById(R.id.usernavdraw);
        usrnamenav.setText(user);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if settings is pressed we opens the settings fragment and set title to settings
        if (id == R.id.action_settings) {
            NavigationActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Settings");
        }
        if (id == R.id.pairmirror) {
            //since pairmirror uses a back button we save the original listener which is a drawer
            mOriginalListener = NavigationActivity.toggle.getToolbarNavigationClickListener();
            //turn of the drawer to a backbutton
            toggleDrawerUse(false);
            //set the title
            NavigationActivity.getSupportActionBar().setTitle("Mirror ID");
            //switch screen to QrCode frame
            NavigationActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCode()).commit();
        }

        return NavigationActivity.onOptionsItemSelected(item);
    }
    /*
    Used for changing from the Drawer functionality to a back button functionality
     */
    public void toggleDrawerUse(boolean useDrawer) {
        // Enable/Disable the icon being used by the drawer
        NavigationActivity.toggle.setDrawerIndicatorEnabled(useDrawer);
        final FragmentManager fragment = NavigationActivity.getFragmentManager();
        // Switch between the listeners as necessary
        if(useDrawer) {
            NavigationActivity.toggle.setToolbarNavigationClickListener(mOriginalListener);
        }
        else
            NavigationActivity.toggle.setHomeAsUpIndicator(R.drawable.back); //set the icon to a back button
        NavigationActivity.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //listeners that goes back to settings whne pressed
                fragment.beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();
                NavigationActivity.getSupportActionBar().setTitle("Settings"); //sets the title to settings
                toggleDrawerUse(true);//activates the drawer again

            }
        });
    }


    /*
    This is the navigationbar items switching fragments when clicked
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragment = NavigationActivity.getFragmentManager();
        //when a diffrent navigation item is clicked we do a specific thing, eg when nav_postit(postit icon) is clicked
        //we switch fragment to postit and set title of actionbar to publish posit.
        //same thing for all navigationitems
        if (id == R.id.nav_postit){
            fragment.beginTransaction().replace(R.id.content_frame, new Postit()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Publish PostIt");
        } else if (id == R.id.nav_mirror) {
            fragment.beginTransaction().replace(R.id.content_frame, new MirrorPostit()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Mirror");
        } else if (id == R.id.nav_remove) {
            fragment.beginTransaction().replace(R.id.content_frame, new RemovePostit()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Remove PostIt");
        } else if (id == R.id.nav_contact) {
            fragment.beginTransaction().replace(R.id.content_frame, new Contact()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Contact Us");
        } else if (id == R.id.nav_about) {
            fragment.beginTransaction().replace(R.id.content_frame, new About()).commit();
            NavigationActivity.getSupportActionBar().setTitle("About");
        }
        else if (id == R.id.nav_preferences) {
            fragment.beginTransaction().replace(R.id.content_frame, new Preferences()).commit();
            NavigationActivity.getSupportActionBar().setTitle("Preferences");
        }


        DrawerLayout drawer = (DrawerLayout) NavigationActivity.findViewById(R.id.drawer_layout);
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
