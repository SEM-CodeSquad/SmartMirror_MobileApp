package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.PreferencesPresenter;


public class PreferencesHandler {
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
    public PreferencesHandler(PreferencesPresenter PreferencesPresenter){
        this.PreferencesPresenter = PreferencesPresenter;
    }

    public void PublishPrefs(String topic, String user, String news, String bus, String weather, String clock, String device, String greetings ,String postit) {
        this.topic = topic;
        this.user = user;
        this.news = news;
        this.bus = bus;
        this.weather = weather;
        this.clock = clock;
        this.greetings = greetings;
        this.postit = postit;
        this.device = device;

        //AwaitEcho();
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
}
