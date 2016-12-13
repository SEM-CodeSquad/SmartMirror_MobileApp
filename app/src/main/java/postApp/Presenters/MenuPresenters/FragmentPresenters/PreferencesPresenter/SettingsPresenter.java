package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.SettingsHandler;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;

/**
 * Class responsible as acting as a presenter between the settings fragment and handler
 */
public class SettingsPresenter {
    private SettingsHandler SettingsHandler;
    private SettingsView SettingsView;

    /**
     * The constructor for this class that instantiates a handler, sets this settingsview and calls startup
     * @param SettingsView the view
     * @param topic the topic
     * @param user the user
     */
    public SettingsPresenter(SettingsView SettingsView, String topic, String user) {
        this.SettingsView = SettingsView;
        this.SettingsHandler = new SettingsHandler(this, SettingsView, topic, user);
        startup();
    }

    /**
     * Method used for settings up all initial values in the activity and building the alertdialogs
     */
    private void startup(){
        this.SetUUID();
        this.SetWeather(((NavigationActivity) SettingsView.getActivity()).getWeather());
        this.SetBus((((NavigationActivity) SettingsView.getActivity()).getBus()));
        this.SetTextField((((NavigationActivity) SettingsView.getActivity()).getNews()));
        this.BuildNews();
        this.BuildStop();
        this.SetUserField((((NavigationActivity) SettingsView.getActivity()).getUser()));
    }

    /**
     * Method for settings the views textfield with a news string
     * @param news the news
     */
    public void SetTextField(String news) {
        SettingsView.SetNews(news);
    }

    public void SetUserField(String user) {
        SettingsView.SetUser(user);
    }
    /**
     * Settings the UUID
     */
    private void SetUUID() {
        SettingsView.SetUUID((((NavigationActivity) SettingsView.getActivity()).getMirror()));
    }

    /**
     * Setting the view bus station
     * @param bus the bus
     */
    public void SetBus(String bus) {
        SettingsView.SetBus(bus);
    }
    /**
     * Setting the view weather station
     * @param weather the weather
     */
    public void SetWeather(String weather) {
        SettingsView.SetWeather(weather);
    }
    /**
     * Calling the views method to buld a alertdialog for news
     */
    private void BuildNews() {
        SettingsView.Buildnews();
    }

    /**
     * Calling the views method to buld a alertdialog for bus stops
     */
    private void BuildStop() {
        SettingsView.Buildstop();
    }

    /**
     * Calling the handler method WeatherOnLoc
     */
    public void WeatherOnLoc() {
        SettingsHandler.WeatherOnLoc();
    }

    /**
     * Calls the views function ChoseAllSettings
     */
    public void ChoseAllSettings(){
        SettingsView.ChoseAllSettings();
    }

    /**
     * Call the handlers method for publishing the settings
     * @param topic the topic
     * @param user the user
     * @param News the news
     * @param Weather the weather
     * @param BusID the busid
     * @param busname the busname
     */
    public void PublishAll(String topic, String user, String News, String Weather, String BusID, String busname){
        SettingsHandler.PublishAll(topic, user, News, Weather, BusID, busname);
    }

    /**
     * Calls the viewmethod to show alertdialog for bus
     */
    public void ShowBus() {
        SettingsView.ShowBus();
    }
    /**
     * Calls the viewmethod to show alertdialog for news
     */
    public void ShowNews() {
        SettingsView.ShowNews();
    }
    /**
     * Calls the viewmethod NoMirrorChosen
     */
    public void NoMirrorChosen() {
        SettingsView.NoMirrorChosen();
    }
    /**
     * Calls the viewmethod ChangeToQr
     */
    public void ChangeToQR() {
        SettingsView.ChangeToQR();
    }
    /**
     * Calls the viewmethod ChangeToSearch
     */
    public void ChangeToSearch() {
        SettingsView.ChangeToSearch();
    }
    /**
     * Sets the news in the navigation activity
     * @param news the news
     */
    public void SetNews(String news){
        ((NavigationActivity) SettingsView.getActivity()).setNews(news);
    }
    /**
     * Calls the handler method SetLocalStop
     */
    public void BusByLoc() {
        SettingsHandler.SetLocalStop();
    }
    /**
     * Calls the views method for unsucessfulpublishing
     */
    public void NoEcho(){
        SettingsView.UnsuccessfulPublish();
    }
    /**
     * Calls the views method for loading
     */
    public void Loading() {
        SettingsView.Loading();
    }
    /**
     * Calls the views method for doneloading
     */
    public void DoneLoading() {
        SettingsView.DoneLoading();
    }

}

