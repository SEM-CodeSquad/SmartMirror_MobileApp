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


    public PostitHandler(PostitView PostitView, PostitPresenter PostitPresenter){
        this.PostitView = PostitView;
        this.PostitPresenter = PostitPresenter;
    }



    public void SetColor(String color){
        this.color = color;
    }

    public void PublishPostit(String topic, String text) {

        this.text = text;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        this.idOne = UUID.randomUUID().toString();

        String date = c.getTime().toString();

        JsonBuilder R = new JsonBuilder();
        String S;
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "postit", text, color, "importantstring", date, idOne).get();

                echo = new Echo(topic.substring(0, topic.length() - 6) + "echo");
                echo.addObserver(this);
                start = System.currentTimeMillis();
                end = start + 3*1000; // 3 seconds * 1000 ms/sec
                while (System.currentTimeMillis() < end)
                {
                    // run
                }

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


    @Override
    public void update(Observable observable, Object data) {
        storePostits = new StorePostits(PostitView.getActivity().getApplicationContext(), color, text, idOne);
    }
}
