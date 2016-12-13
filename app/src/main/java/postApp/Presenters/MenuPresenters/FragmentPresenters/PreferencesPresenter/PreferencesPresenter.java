package postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter;

import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.PreferencesView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler.PreferencesHandler;

/**
 * Class that presents the view to the handler and interacts with both of them
 */

public class PreferencesPresenter {

    private PreferencesView PreferencesView;
    private PreferencesHandler PreferencesHandler;

    /**
     * Constructor that injects this presenter into the handler and the topic
     * @param PreferencesView The view we get from Preferences
     * @param topic The topic
     * @param user The user
     */
    public PreferencesPresenter(PreferencesView PreferencesView, String topic, String user){
        this.PreferencesView = PreferencesView;
        this.PreferencesHandler = new PreferencesHandler(this, topic, user);
    }

    /**
     * Called when wanting to call the handlers method for publishing preferences
     * @param topic the topic
     * @param user the user
     * @param news the news
     * @param bus the bus
     * @param weather the weather
     * @param device the device
     * @param clock the clock
     * @param calender the calender
     * @param external the external system
     */
    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String device, String clock, String calender ,String external, String ShoppingList){
        PreferencesHandler.PublishPrefs(topic, user, news, bus, weather, device, clock, calender, external, ShoppingList);
    }

    /**
     * Call the views method when publish preferences button is pressed
     */
    public void PrefBtn(){
        PreferencesView.PublishPrefs();
    }
    /**
     * Call the views method when enable all is turned to true
     */
    public void DisBtnTrue(){
        PreferencesView.DisBtnTrue();
    }
    /**
     * Call the views method when enable all is turned to false
     */
    public void DisBtnFalse(){
        PreferencesView.DisBtnFalse();
    }
    /**
     * Call the views method when when theres no mirror chosen
     */
    public void NoMirror(){
        PreferencesView.NoMirrorChosen();
    }
    /**
     * Call the views method when we want to show a loading bar
     */
    public void Loading() {
        PreferencesView.Loading();
    }
    /**
     * Call the views method when its a unsuccessfull publish
     */
    public void NoEcho(){
        PreferencesView.UnsuccessfulPublish();
    }
    /**
     * Call the views method when we are done loading
     */
    public void DoneLoading() {
        PreferencesView.DoneLoading();
    }

}
