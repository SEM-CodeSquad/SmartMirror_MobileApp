package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.os.Handler;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.HidePostitPresenter;

/**
 * Class that is used for the logic for the HidePostit View
 */

public class HidePostitHandler implements Observer {

    private HidePostitPresenter HidePostitPresenter;
    private Boolean echoed = false;
    private Echo echo;

    /**
     * Constructor that start a echo and sets the postitpresenter. Disconnects the echo so we are not connected all the time
     *
     * @param HidePostitPresenter The presenter we interact with
     * @param topic               the topic we echo to
     */
    public HidePostitHandler(HidePostitPresenter HidePostitPresenter, String topic) {
        this.HidePostitPresenter = HidePostitPresenter;
        String topic123 = "dit029/SmartMirror/" + topic + "/echo";
        echo = new Echo(topic123, topic);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * This method checks with filter is active and publishes it to the broker
     *
     * @param topic  String with the topic
     * @param user   String with the user
     * @param yellow String with either true or false
     * @param blue   String with either true or false
     * @param orange String with either true or false
     * @param pink   String with either true or false
     * @param green  String with either true or false
     * @param purple String with either true or false
     */
    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple) {
        String integer;
        if (topic != "No mirror chosen") {
            AwaitEcho();
            if (yellow == "true") {
                integer = "6";
            } else if (blue == "true") {
                integer = "1";
            } else if (orange == "true") {
                integer = "4";
            } else if (pink == "true") {
                integer = "5";
            } else if (green == "true") {
                integer = "2";
            } else if (purple == "true") {
                integer = "3";
            } else {
                integer = "0";
            }
            HidePostitPresenter.Loading();
            JsonBuilder R = new JsonBuilder();

            try {
                R.execute(topic, "preferencesHide", user, integer).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e);
            }
        } else {
            HidePostitPresenter.NoMirror();
        }
    }

    /**
     * Method used for awaiting a echo. if echoed is true after 2 seconds we know we got a echo
     */
    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    HidePostitPresenter.DoneLoading();
                    echoed = false;
                } else {
                    HidePostitPresenter.NoEcho();
                    HidePostitPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    public void Disc() {
        echo.disconnect();
    }
    /**
     * Just a observable that waits for a notification from the echo class.
     * @param observable The observable
     * @param o The object
     */
    @Override
    public void update(Observable observable, Object o) {
        echoed = true;
    }
}
