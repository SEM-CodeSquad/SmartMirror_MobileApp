package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;


import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;

import postApp.DataHandlers.Postits.StorePostits;

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

    private String topic;
    private String user;

    public PostitHandler(PostitView PostitView, PostitPresenter PostitPresenter){
        this.PostitView = PostitView;
        this.PostitPresenter = PostitPresenter;
    }



    public void SetColor(String color){
        this.color = color;
    }

    public void PublishPostit(String text, String topic, String user) {
        this.user = user;
        this.text = text;
        this.topic = topic;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        this.idOne = UUID.randomUUID().toString();

        String date = c.getTime().toString();
        AwaitEcho();
        JsonBuilder R = new JsonBuilder();
        String S;
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
        storePostits = new StorePostits(user,idOne, color, text);

        System.out.println(storePostits.getStoreStatus());   //returns boolean saved, if saved postit or not
    }


    @Override
    public void update(Observable observable, Object data) {
        System.out.println("here");
            StorePost();
    }
}
