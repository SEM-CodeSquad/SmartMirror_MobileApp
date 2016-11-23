package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.widget.Toast;

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
    StorePostits storePostits;
    private String topic;

    public PostitHandler(PostitView PostitView, PostitPresenter PostitPresenter){
        this.PostitView = PostitView;
        this.PostitPresenter = PostitPresenter;
    }



    public void SetColor(String color){
        this.color = color;
    }

    public void PublishPostit(String text, String topic) {

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
                S = R.execute(topic, "postit", text, color, "importantstring", date, idOne).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                S = "Warning: Did Not Publish";
            }
            PostitPresenter.ShowMessage(S);
        } else {
            PostitPresenter.NoMirror();
        }
    }


    public void AwaitEcho(){
        topic = "dit029/SmartMirror/" +topic + "/";
        echo = new Echo(topic.substring(0, topic.length() - 6) + "echo");
        echo.addObserver(this);
        start = System.currentTimeMillis();
        end = start + 3*1000; // 3 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            // run
        }
        if (System.currentTimeMillis() > end){
            PostitView.NoEcho();
        }

    }
    public void StorePost(){
        storePostits = new StorePostits(PostitView.getActivity().getApplicationContext(), color, text, idOne);
    }

    @Override
    public void update(Observable observable, Object data) {
        if(System.currentTimeMillis() < end) {
            StorePost();
        }
    }
}
