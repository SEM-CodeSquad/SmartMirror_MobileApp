package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.SettingsHandler;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsPresenter {
    private SettingsHandler SettingsHandler;
    private SettingsView SettingsView;

    public SettingsPresenter(SettingsView SettingsView) {
        this.SettingsView = SettingsView;
        this.SettingsHandler = new SettingsHandler(this, SettingsView);
        startup();
    }

    private void startup(){
        this.SetUUID();
        this.SetWeather();
        this.SetBus();
        this.SetTextNews();
        this.BuildNews();
        this.BuildStop();

    }

    public void SetTextNews() {
        SettingsView.SetNews((((NavigationActivity) SettingsView.getActivity()).getNews()));
    }

    public void SetUUID() {
        SettingsView.SetUUID((((NavigationActivity) SettingsView.getActivity()).getMirror()));
    }

    public void SetBus() {
        SettingsView.SetBus((((NavigationActivity) SettingsView.getActivity()).getBus()));
    }

    public void SetWeather() {
        SettingsView.SetWeather(((NavigationActivity) SettingsView.getActivity()).getWeather());
    }

    public void BuildNews() {
        SettingsView.Buildnews();
    }

    public void BuildStop() {
        SettingsView.Buildstop();
    }

    public void WeatherOnLoc() {
        SettingsHandler.WeatherOnLoc();
    }

    public void StartNews() {
        SettingsHandler.StartNews();

    }

    public void StartBus() {
        SettingsHandler.StartBus();

    }

    public void StoreSettings(String user, String News, String Weather, String Bus){
        SettingsHandler.StoreSettings(user, News, Weather, Bus);
    }
    public void ShowBus() {
        SettingsView.ShowBus();
    }

    public void ShowNews() {
        SettingsView.ShowNews();
    }

    public void PublishNews(String News, String Topic) {
        SettingsHandler.PublishNews(News, Topic);
    }

    public void PublishBus(String Bus, String Topic) {
        SettingsHandler.PublishBus(Bus, Topic);
    }

    public void displaySuccPub(String S) {
        SettingsView.displaySuccPub(S);
    }

    public void NoMirrorChosen() {
        SettingsView.NoMirrorChosen();
    }

    public void ChangeToQR() {
        SettingsView.ChangeToQR();
    }

    public void ChangeToSearch() {
        SettingsView.ChangeToSearch();
    }

    public void SetNewsCNN() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("CNN");
    }

    public void SetNewsGoogle() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("GOOGLE");
    }

    public void SetNewsDN() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("DN");
    }
    public void SetNewsSVT() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("SVT");
    }
    public void SetNewsExpressen() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("EXPRESSEN");
    }
    public void SetNewsABC() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("ABC");
    }

    public void UpdateScreen(){
        SettingsView.UpdateScreen();
    }

    public void BusByLoc() {
        SettingsHandler.SetLocalStop();
    }

}

