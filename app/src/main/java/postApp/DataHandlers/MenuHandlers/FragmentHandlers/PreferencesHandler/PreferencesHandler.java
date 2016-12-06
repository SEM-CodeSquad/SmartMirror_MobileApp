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
    Runnable run;
    private Echo echo;
    public PreferencesHandler(PreferencesPresenter PreferencesPresenter){
        this.PreferencesPresenter = PreferencesPresenter;
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String device, String greetings ,String postit) {
        PreferencesPresenter.Loading();
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
        if (topic != "No mirror chosen") {
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
        handler.removeCallbacks(run);
        PreferencesPresenter.DoneLoading();

    }
}
