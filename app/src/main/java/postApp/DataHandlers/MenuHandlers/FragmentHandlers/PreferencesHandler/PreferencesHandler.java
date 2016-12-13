package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.os.Handler;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;

/**
 * This class is used as a handler for the preferences view and calls function from the presenter
 */
public class PreferencesHandler implements Observer {
    private PreferencesPresenter PreferencesPresenter;
    private Boolean echoed = false;
    private Echo echo;

    /**
     * Constructor for this class that start a echo and adds a observer.
     * @param PreferencesPresenter the presenter
     * @param mirror the mirror
     * @param user the user
     */
    public PreferencesHandler(PreferencesPresenter PreferencesPresenter, String mirror, String user) {
        this.PreferencesPresenter = PreferencesPresenter;
        String topic123 = "dit029/SmartMirror/" + mirror + "/echo";
        echo = new Echo(topic123, user);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * Method used for publishing prefs through the JsonBuilder with the below values. We also call the presenter function loading and start a echo
     * @param topic the topic
     * @param user the user
     * @param news the news
     * @param bus the bus
     * @param weather the weather
     * @param clock the clock
     * @param device the device
     * @param greetings the greetings message
     * @param postit the postit
     * @param shoppinglist the shoppinglist
     */
    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String device, String greetings, String postit, String shoppinglist) {
        if (!topic.equals("No mirror chosen")) {
            PreferencesPresenter.Loading();
            AwaitEcho();
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "preferences", user, news, bus, weather, clock, device, greetings, postit, shoppinglist).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PreferencesPresenter.NoMirror();
        }
    }

    /**
     * function that connects the echo to the broker. Starts a handler that post 2 seconds Either if the echo is true calls the presenters method for doneloading
     * Or calls it also but also the method for no echo
     */
    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    PreferencesPresenter.DoneLoading();
                    echoed = false;
                } else {
                    PreferencesPresenter.NoEcho();
                    PreferencesPresenter.DoneLoading();
                }
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * A update function that sets echoed to true when we get a update
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
            echoed = true;
    }
}

