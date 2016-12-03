package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.HidePostitPresenter;

/**
 * Created by adinH on 2016-12-03.
 */

public class HidePostitHandler {

    HidePostitPresenter HidePostitPresenter;
    String topic;
    String user;
    String integer;


    public HidePostitHandler(HidePostitPresenter HidePostitPresenter){
        this.HidePostitPresenter = HidePostitPresenter;
    }

    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple){
        this.topic = topic;
        this.user = user;
        if(yellow == "true"){
           integer = "6";
        }
        else if(blue == "true"){
            integer = "1";
        }
        else if(orange == "true"){
            integer = "4";
        }
        else if(pink == "true"){
            integer = "5";
        }else if(green == "true"){
            integer = "2";
        }
        else if(purple == "true"){
            integer = "3";
        }
        else {
            integer = "0";
        }
        //AwaitEcho();
        JsonBuilder R = new JsonBuilder();
        String S;
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "preferencesHide", user, integer).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            HidePostitPresenter.ShowMessage(S);
        } else {
            HidePostitPresenter.NoMirror();
        }
    }
}
