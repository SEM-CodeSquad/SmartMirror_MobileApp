package postApp.Presenters.MenuPresenters;

import android.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.NavigationInteractor;

/**
 * Class presenting the navigationActivity and interacting with the NavigationInteractor
 */

public class NavigationPresenter {

    private postApp.DataHandlers.MenuHandlers.NavigationInteractor NavigationInteractor;
    private NavigationActivity NavigationActivity;
    View.OnClickListener mOriginalListener;

    /**
     * Instantiating a navigationinteractor
     * @param NavigationActivity That the class presents
     */
    public NavigationPresenter(NavigationActivity NavigationActivity) {
        this.NavigationActivity = NavigationActivity;
        this.NavigationInteractor = new NavigationInteractor();
    }

    /**
     * When changing from the normal navigation drawer to a back button we use this function
     * @param useDrawer if true use a drawer else back button
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
                    fragment.beginTransaction().replace(R.id.content_frame, new SettingsView()).commit();
                    NavigationActivity.getSupportActionBar().setTitle("Settings"); //sets the title to settings
                    toggleDrawerUse(true);//activates the drawer again

                }
            });

    }

    /**
     * When we want to update the settings we call this function
     * @param bus The station
     * @param news The selected news feed
     * @param weather The selected weather location
     * @param user The user
     */
    public void UpdateSettings(String bus, String busid, String news, String weather, String user){
        SetBusID(busid);
        setBus(bus);
        setNews(news);
        setWeather(weather);
        setUser(user);
    }

    /**
     * Method for getting the mirror ID
     * @return mirror ID
     */
    public String getMirror(){
        return NavigationInteractor.getMirror();
    }

    /**
     * Method for setting the mirror ID
     * @param UUID mirror ID
     */
    public void setMirror(String UUID){
        NavigationInteractor.setMirror(UUID);
    }

    /**
     * Method for getting the busstation
     * @return BusStation
     */
    public String getBus(){
        return NavigationInteractor.getBus();
    }

    /**
     * Method for setting the Busstation
     * @param busid Busstation
     */
    public void setBus(String busid){
        NavigationInteractor.setBus(busid);
    }

    /**
     * Method for getting weather city
     * @return weather city
     */
    public String getWeather(){
        return NavigationInteractor.getWeather();
    }

    /**
     * Method for setting the weather city
     * @param W weather city
     */
    public void setWeather(String W){
        NavigationInteractor.setWeather(W);
    }

    /**
     * Method for getting the news
     * @return news source
     */
    public String getNews(){
        return NavigationInteractor.getNews();
    }

    /**
     * Method for setting the news
     * @param N the news source
     */
    public void setNews(String N){
        NavigationInteractor.setNews(N) ;
    }

    /**
     * Method for setting the user
     * @param user username
     */
    public void setUser(String user) {
        NavigationInteractor.SetUser(user);
    }

    /**
     * Method for getting the username
     * @return user
     */
    public String getUser(){
        return NavigationInteractor.getUser();
    }

    /**
     * Method for setting each bus ID for the station
     * @param id the unique id for each busstation
     */
    public void SetBusID(String id){
        NavigationInteractor.SetBusID(id);
    }

    /**
     * Method for getting each BUS ID
     * @return bus ID
     */
    public String GetBusID() {
        return NavigationInteractor.GetBusID();
    }
}
