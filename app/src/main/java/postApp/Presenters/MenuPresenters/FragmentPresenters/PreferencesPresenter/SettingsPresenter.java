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

    public SettingsPresenter(SettingsView SettingsView, String topic, String user) {
        this.SettingsView = SettingsView;
        this.SettingsHandler = new SettingsHandler(this, SettingsView, topic, user);
        startup();
    }

    private void startup(){
        this.SetUUID();
        this.SetWeather(((NavigationActivity) SettingsView.getActivity()).getWeather());
        this.SetBus((((NavigationActivity) SettingsView.getActivity()).getBus()));
        this.SetTextField((((NavigationActivity) SettingsView.getActivity()).getNews()));
        this.BuildNews();
        this.BuildStop();

    }

    public void SetTextField(String news) {
        SettingsView.SetNews(news);
    }

    public void SetUUID() {
        SettingsView.SetUUID((((NavigationActivity) SettingsView.getActivity()).getMirror()));
    }

    public void SetBus(String bus) {
        SettingsView.SetBus(bus);
    }

    public void SetWeather(String weather) {
        SettingsView.SetWeather(weather);
    }

    private void BuildNews() {
        SettingsView.Buildnews();
    }

    private void BuildStop() {
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

    public void ChoseAllSettings(){
        SettingsView.ChoseAllSettings();
    }

    public void PublishAll(String topic, String user, String News, String Weather, String BusID, String busname){
        SettingsHandler.PublishAll(topic, user, News, Weather, BusID, busname);
    }
    public void ShowBus() {
        SettingsView.ShowBus();
    }

    public void ShowNews() {
        SettingsView.ShowNews();
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

    public void SetNews(String news){
        ((NavigationActivity) SettingsView.getActivity()).setNews(news);
    }
    public void BusByLoc() {
        SettingsHandler.SetLocalStop();
    }
    public void NoEcho(){
        SettingsView.UnsuccessfulPublish();
    }
    public void Loading() {
        SettingsView.Loading();
    }

    public void DoneLoading() {
        SettingsView.DoneLoading();
    }

}

