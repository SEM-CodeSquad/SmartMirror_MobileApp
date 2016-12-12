package postApp.DataHandlers.MenuHandlers;

import android.app.FragmentManager;
import android.view.View;

import java.util.UUID;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
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
    String busIDVäst = "No ID";
    UUID idOne = UUID.randomUUID();

    public NavigationInteractor(){
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
