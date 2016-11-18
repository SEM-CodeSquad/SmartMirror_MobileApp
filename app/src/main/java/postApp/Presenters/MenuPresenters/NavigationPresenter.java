package postApp.Presenters.MenuPresenters;

import android.view.Menu;
import android.view.MenuItem;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.NavigationInteractor;

/**
 * Created by adinH on 2016-11-18.
 */

public class NavigationPresenter {

    private postApp.DataHandlers.MenuHandlers.NavigationInteractor NavigationInteractor;

    public NavigationPresenter(NavigationActivity NavigationActivity) {
        this.NavigationInteractor = new NavigationInteractor(NavigationActivity);
    }
    /*
    Back pressed on phone, closes the drawer if its open
     */

    public void onBackPressed() {
       NavigationInteractor.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return NavigationInteractor.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationInteractor.onOptionsItemSelected(item);
    }
    /*
    Used for changing from the Drawer functionality to a back button functionality
     */

    public void toggleDrawerUse(boolean useDrawer) {
        NavigationInteractor.toggleDrawerUse(useDrawer);
    }
    /*
    This is the navigationbar items switching fragments when clicked
     */

    public boolean onNavigationItemSelected(MenuItem item) {
        return NavigationInteractor.onNavigationItemSelected(item);
    }
    /*
    Getters and setter for all the current string that will be used to passing data
    Having these to access the same from all fragments
     */
    public String getMirror(){
        return NavigationInteractor.getMirror();
    }
    public void setMirror(String UUID){
        NavigationInteractor.setMirror(UUID);
    }
    public String getBus(){
        return NavigationInteractor.getBus();
    }
    public void setBus(String busid){
        NavigationInteractor.setBus(busid);
    }
    public String getWeather(){
        return NavigationInteractor.getWeather();
    }
    public void setWeather(String W){
        NavigationInteractor.setWeather(W);
    }
    public String getNews(){
        return NavigationInteractor.getNews();

    }
    public void setNews(String N){
        NavigationInteractor.setNews(N) ;
    }
    public String getUUID(){
        return NavigationInteractor.getUUID();
    }
    public String getUser(){
        return NavigationInteractor.getUser();
    }
}
