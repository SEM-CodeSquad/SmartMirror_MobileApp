package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.os.Handler;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;


public class PreferencesHandler implements Observer {
    PreferencesPresenter PreferencesPresenter;
    String topic;
    String user;
    String news;
    String bus;
    String weather;
    String clock;
    String greetings;
    String postit;
    String device;
    Handler handler;
    public PreferencesHandler(PreferencesPresenter PreferencesPresenter){
        this.PreferencesPresenter = PreferencesPresenter;
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String device, String greetings ,String postit) {
        if (topic != "No mirror chosen") {

        PreferencesPresenter.Loading();
            handler.postDelayed(new Runnable() {
                public void run() {
                    PreferencesPresenter.DoneLoading();
                }
            }, 2000); // 3000 milliseconds delay
        this.topic = topic;
        this.user = user;
        this.news = news;
        this.bus = bus;
        this.weather = weather;
        this.clock = clock;
        this.greetings = greetings;
        this.postit = postit;
        this.device = device;
        JsonBuilder R = new JsonBuilder();
        String S;

            try {
                S = R.execute(topic, "preferences", user, news, bus, weather, clock, device, greetings, postit).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            PreferencesPresenter.ShowMessage(S);
        } else {
            PreferencesPresenter.NoMirror();
        }
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
