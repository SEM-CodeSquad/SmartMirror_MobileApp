package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.os.Handler;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.HidePostitPresenter;

/**
 * Created by adinH on 2016-12-03.
 */

public class HidePostitHandler implements Observer {

    HidePostitPresenter HidePostitPresenter;
    private Boolean waitforecho = false;
    private Boolean echoed = false;


    public HidePostitHandler(HidePostitPresenter HidePostitPresenter, String topic){
        this.HidePostitPresenter = HidePostitPresenter;
        String topic123 = "dit029/SmartMirror/" + topic + "/echo";
        Echo echo = new Echo(topic123, topic);
        echo.addObserver(this);
    }

    public void FilterPost(String topic, String user, String yellow, String blue, String orange, String pink, String green, String purple){
        String integer;
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
        HidePostitPresenter.Loading();
        AwaitEcho();
        JsonBuilder R = new JsonBuilder();
        if (topic != "No mirror chosen") {
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


    private void AwaitEcho() {
        waitforecho = true;
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
                waitforecho = false;
            }
        }, 2000); // 2000 milliseconds delay
    }

    @Override
    public void update(Observable observable, Object o) {
        if (waitforecho) {
            echoed = true;
        }
    }
}
