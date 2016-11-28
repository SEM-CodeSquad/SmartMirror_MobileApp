package postApp.Presenters.MenuPresenters;

import android.view.Menu;
import android.view.MenuItem;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.NavigationInteractor;

/**
w * @author adinH on 2016-11-18.
 */

public class NavigationPresenter {

    private postApp.DataHandlers.MenuHandlers.NavigationInteractor NavigationInteractor;

    public NavigationPresenter(NavigationActivity NavigationActivity) {
        this.NavigationInteractor = new NavigationInteractor(NavigationActivity);
    }

    public void toggleDrawerUse(boolean useDrawer) {
        NavigationInteractor.toggleDrawerUse(useDrawer);
    }

    public void UpdateSettings(String bus, String news, String weather, String user){
        setBus(bus);
        setNews(news);
        setWeather(weather);
        setUser(user);
    }
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
    public void setUser(String user) {
        NavigationInteractor.SetUser(user);
    }
    public String getUser(){
        return NavigationInteractor.getUser();
    }
    public void SetBusID(String busID){
        NavigationInteractor.SetBusID(busID);
    }
    public String GetBusID(){
        return NavigationInteractor.GetBusID();
    }
}
