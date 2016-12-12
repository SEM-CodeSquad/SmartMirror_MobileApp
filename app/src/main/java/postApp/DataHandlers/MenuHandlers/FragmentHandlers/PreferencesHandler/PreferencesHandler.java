package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.os.Handler;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;


public class PreferencesHandler implements Observer {
    private PreferencesPresenter PreferencesPresenter;
    private Boolean echoed = false;
    private Echo echo;

    public PreferencesHandler(PreferencesPresenter PreferencesPresenter, String topic) {
        this.PreferencesPresenter = PreferencesPresenter;
        String topic123 = "dit029/SmartMirror/" + topic + "/echo";
        Echo echo = new Echo(topic123, topic);
        echo.addObserver(this);
        echo.disconnect();
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String device, String greetings, String postit) {
        if (!topic.equals("No mirror chosen")) {
            PreferencesPresenter.Loading();
            AwaitEcho();
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "preferences", user, news, bus, weather, clock, device, greetings, postit).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PreferencesPresenter.NoMirror();
        }
    }

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

    @Override
    public void update(Observable observable, Object o) {
            echoed = true;
    }
}

