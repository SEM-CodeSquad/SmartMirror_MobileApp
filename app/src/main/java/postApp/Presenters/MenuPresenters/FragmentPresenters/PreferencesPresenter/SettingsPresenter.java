package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import android.widget.Toast;

import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.SettingsHandler;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.DataHandlers.Settings.Settings;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsPresenter {
    private SettingsHandler SettingsHandler;
    private SettingsView SettingsView;

    public SettingsPresenter(SettingsView SettingsView) {
        this.SettingsView = SettingsView;
        this.SettingsHandler = new SettingsHandler(this, SettingsView);
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

    public void SetNewsBBC() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("BBC");
    }

    public void SetNewsDailyMail() {
        ((NavigationActivity) SettingsView.getActivity()).setNews("DailyMail");
    }

    public void SetTextNews() {
        SettingsView.SetNews();
    }

    public void BusByLoc() {
        SettingsHandler.SetLocalStop();
    }

    public void SetUUID() {
        SettingsView.SetUUID();

    }

    public void SetBus() {
        SettingsView.SetBus();
    }

    public void SetWeather() {
        SettingsView.SetWeather();
    }
}

