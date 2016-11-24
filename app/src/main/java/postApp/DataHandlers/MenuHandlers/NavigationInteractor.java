package postApp.DataHandlers.MenuHandlers;

import android.app.FragmentManager;
import android.view.View;

import java.util.UUID;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.Settings.Settings;

/**
 * Created by adinH on 2016-11-18.
 */

public class NavigationInteractor {

    String mirrorID = "No mirror chosen";
    String newsID = "No news chosen";
    String busID = "No bus or tram stop chosen";
    String weatherID = "No city chosen";
    String user;
    String busIDVäst = "no väst";
    UUID idOne = UUID.randomUUID();
    String BusID;
    View.OnClickListener mOriginalListener;

    postApp.ActivitiesView.MenuView.NavigationActivity NavigationActivity;


    public NavigationInteractor(NavigationActivity NavigationActivity){
        this.NavigationActivity = NavigationActivity;
    }

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
                fragment.beginTransaction().replace(R.id.content_frame, new SettingsView()).commit();
                NavigationActivity.getSupportActionBar().setTitle("Settings"); //sets the title to settings
                toggleDrawerUse(true);//activates the drawer again

            }
        });
    }
    public void UpdateSettings(){
        Settings set = new Settings(user);
        String[] db = set.getSettings();
        setBus(db[0]);
        setWeather(db[1]);
        setNews(db[2]);
        System.out.println(db.toString());
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
    public void SetUser(String user){
        this.user = user;
    }
    public String getUser(){
        return user;
    }
    public void SetBusID(String busID){this.busIDVäst = busID;}
    public String GetBusID(){
        return busIDVäst;
    }

}
