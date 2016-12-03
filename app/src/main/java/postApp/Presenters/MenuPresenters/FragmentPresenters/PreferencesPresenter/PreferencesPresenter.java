package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.PreferencesView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.PreferencesHandler;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class PreferencesPresenter {

    PreferencesView PreferencesView;
    PreferencesHandler PreferencesHandler;
    public PreferencesPresenter(PreferencesView PreferencesView){
        this.PreferencesView = PreferencesView;
        this.PreferencesHandler = new PreferencesHandler(this);
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String device, String clock, String calender ,String external){
        PreferencesHandler.PublishPrefs(topic, user, news, bus, weather, device, clock, calender, external);
    }
    public void PrefBtn(){
        PreferencesView.PublishPrefs();
    }
    public void DisBtnTrue(){
        PreferencesView.DisBtnTrue();
    }
    public void DisBtnFalse(){
        PreferencesView.DisBtnFalse();
    }
    public void ShowMessage(String S){
        PreferencesView.displaySuccPub(S);
    }
    public void NoMirror(){
        PreferencesView.NoMirrorChosen();
    }

}
