package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;


import android.os.Handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;

import postApp.DataHandlers.AppCommons.Postits.StorePostits;

import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;

/**
 * Class used as a Handler for the Postit view and presenter. Handles the logic part of the View.
 */

public class PostitHandler implements Observer {
    private String color;
    private PostitPresenter PostitPresenter;
    private String text;
    private String idOne;
    private Echo echo;
    private String user;
    private String date;
    private long timestamp;
    private boolean stored = false;
    private String echotopic;

    /**
     * We set the postitpresenter, set the topic we are posting too. Instantiate a Echo Class that we observe
     * @param PostitPresenter
     * @param topic
     */
    public PostitHandler(PostitPresenter PostitPresenter, String topic) {
        this.PostitPresenter = PostitPresenter;
        this.echotopic = "dit029/SmartMirror/" + topic + "/echo";
        echo = new Echo(echotopic, user);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * We set color with this method
     * @param color the color
     */
    public void SetColor(String color) {
        this.color = color;
    }

    /**
     * We publish postits witht his method.
     * @param text the text of the postit
     * @param topic the topic we are posting to
     * @param user the user
     * @param date the date
     */
    public void PublishPostit(String text, String topic, String user, String date) {
        //Check if a mirror is paired
        if (!topic.equals("No mirror chosen")) {
            // set a loading screen
            PostitPresenter.Loading();
            //await a echo
            AwaitEcho();
            this.user = user;
            this.text = text;
            this.date = date;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            //we make a date here to UNIX time, if its standard
            if (date == "standard") {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, 5); // Adding 5 days
                this.timestamp = c.getTimeInMillis() / 1000;
                this.date = sdf.format(c.getTime());
            }
            //else the date is chosen already by the user and
            else {
                Date dateTemp;
                try {
                    dateTemp = sdf.parse(this.date);
                    this.timestamp = (dateTemp.getTime()) / 1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //A random UUID for the postit
            this.idOne = UUID.randomUUID().toString();
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "postit", text, color, Long.toString(timestamp), idOne, user).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // if theres no mirror we display that through the presenter
        else {
            PostitPresenter.NoMirror();
        }
    }

    /**
     * Method used for awaiting a echo. if stored is true after 2 seconds we know we got a echo
     */
    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (stored) {
                    PostitPresenter.DoneLoading();
                    stored = false;
                } else {
                    PostitPresenter.NoEcho();
                    PostitPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    private void Disc(){
        echo.disconnect();
    }
    /**
     * Method for storing postits in the database
     */
    private void StorePost() {
        StorePostits storePostits = new StorePostits(user, idOne, color, text, timestamp);
    }

    /**
     * Just a observable that waits for a notification from the echo class. If it gets it it stores a postit and sets stored to true
     * @param observable q
     * @param data q
     */
    @Override
    public void update(Observable observable, Object data) {
            stored = true;
            StorePost();
    }
}
