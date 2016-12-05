package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;


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
    private PostitView PostitView;
    private PostitPresenter PostitPresenter;
    private String text;
    private String idOne;
    private Echo echo;
    private long start;
    private long end;
    private StorePostits storePostits;
    private DBConnection conn;
    private String topic;
    private String user;
    private String date;
    private String timestamp;


    public PostitHandler(PostitView PostitView, PostitPresenter PostitPresenter){
        this.PostitView = PostitView;
        this.PostitPresenter = PostitPresenter;
    }



    public void SetColor(String color){
        this.color = color;
    }

    public void PublishPostit(String text, String topic, String user, String date) {
        this.user = user;
        this.text = text;
        this.topic = topic;
        this.date = date;

        if(date == "standard"){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 5); // Adding 5 days
            this.timestamp = String.valueOf(c.getTimeInMillis()/1000);
            this.date = sdf.format(c.getTime());
        }
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
            Date dateTemp;
            try {
                dateTemp = dateFormat.parse(date);
                long unixTime =  (dateTemp.getTime())/1000;
                this.date = String.valueOf(unixTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }



        this.idOne = UUID.randomUUID().toString();
        StorePost();
        JsonBuilder R = new JsonBuilder();
        String S;
        System.out.println("here");
        System.out.println(date);
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "postit", text, color, date, idOne).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println("HI");
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            PostitPresenter.ShowMessage(S);
        } else {
            PostitPresenter.NoMirror();
        }
    }


    public void AwaitEcho(){
        String topic123 = "dit029/SmartMirror/" +topic + "/echo";
        echo = new Echo(topic123, topic);
        echo.addObserver(this);
        start = System.currentTimeMillis();
        end = start + 3*1000; // 3 seconds * 1000 ms/sec
        if (System.currentTimeMillis() > end){
            PostitView.NoEcho();
        }

    }
    public void StorePost(){
        storePostits = new StorePostits(user,idOne, color, text, timestamp);

        System.out.println(storePostits.getStoreStatus());   //returns boolean saved, if saved postit or not
    }


    @Override
    public void update(Observable observable, Object data) {

    }
}
