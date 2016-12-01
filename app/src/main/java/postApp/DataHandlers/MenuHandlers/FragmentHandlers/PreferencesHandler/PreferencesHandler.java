package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class PreferencesHandler {
    PreferencesPresenter PreferencesPresenter;
    private String topic;
    private String user;
    private String news;
    private String bus;
    private String weather;
    private String clock;
    private String calender;
    private String external;
    public PreferencesHandler(PreferencesPresenter PreferencesPresenter){
        this.PreferencesPresenter = PreferencesPresenter;
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String calender ,String external) {
        this.topic = topic;
        this.user = user;
        this.news = news;
        this.bus = bus;
        this.weather = weather;
        this.clock = clock;
        this.calender = calender;
        this.external = external;

        //AwaitEcho();
        JsonBuilder R = new JsonBuilder();
        String S;
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "preferences", user, news, bus, weather, clock, calender, external).get();
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
}
