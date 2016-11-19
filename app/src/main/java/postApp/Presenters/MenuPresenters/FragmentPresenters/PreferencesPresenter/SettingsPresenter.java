package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.SettingsHandler;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsPresenter {
    private SettingsHandler SettingsHandler;

    public SettingsPresenter(SettingsView SettingsView) {
        this.SettingsHandler = new SettingsHandler(SettingsView);
    }
    public void BuildNews(){
        SettingsHandler.Buildnews();
    }
    public void BuildStop(){
        SettingsHandler.Buildstop();
    }
    public void WeatherOnLoc(){
        SettingsHandler.WeatherOnLoc();
    }
    public void ChangeToQr(){
        SettingsHandler.ChangeToQr();
    }
    public void StartNews(){
        SettingsHandler.StartNews();

    }
    public void StartBus(){
        SettingsHandler.StartBus();

    }
}
