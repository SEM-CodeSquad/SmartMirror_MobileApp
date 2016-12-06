package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;


import android.os.Handler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.DBConnection.DBConnection;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;

import postApp.DataHandlers.AppCommons.Postits.StorePostits;

import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;


public class PostitHandler implements Observer{
    private String color;
    private PostitPresenter PostitPresenter;
    private String text;
    private String idOne;
    private Echo echo;
    private StorePostits storePostits;
    private String topic;
    private String user;
    private String date;
    private long timestamp;
    private boolean stored = false;


    public PostitHandler(PostitPresenter PostitPresenter, String topic){
        this.PostitPresenter = PostitPresenter;
        String topic123 = "dit029/SmartMirror/" +topic + "/echo";
        echo = new Echo(topic123, topic);
        echo.addObserver(this);
    }



    public void SetColor(String color){
        this.color = color;
    }

    public void PublishPostit(String text, String topic, String user, String date) {

        if (topic != "No mirror chosen") {
            PostitPresenter.Loading();
        this.user = user;
        this.text = text;
        this.topic = topic;
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if(date == "standard"){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 5); // Adding 5 days
            this.timestamp = c.getTimeInMillis()/1000;
            this.date = sdf.format(c.getTime());
        }else {
            Date dateTemp;
            try {
                dateTemp = sdf.parse(this.date);
                long unixTime = (dateTemp.getTime()) / 1000;
                this.timestamp = unixTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        this.idOne = UUID.randomUUID().toString();
        JsonBuilder R = new JsonBuilder();
        String S;
            try {
                S = R.execute(topic, "postit", text, color, Long.toString(timestamp), idOne, user).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            PostitPresenter.ShowMessage(S);
        } else {
            PostitPresenter.NoMirror();
        }
    }


    public void AwaitEcho() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(stored){
                    PostitPresenter.DoneLoading();
                    stored = false;
                }
                else{
                    PostitPresenter.NoEcho();
                    PostitPresenter.DoneLoading();
                }
            }
        }, 2000); // 2000 milliseconds delay
    }

    public void StorePost(){
        storePostits = new StorePostits(user,idOne, color, text, timestamp);
    }


    @Override
    public void update(Observable observable, Object data) {
        stored = true;
        StorePost();
    }
}
