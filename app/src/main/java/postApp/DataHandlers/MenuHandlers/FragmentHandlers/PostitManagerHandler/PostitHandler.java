package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;


public class PostitHandler {

    String color;
    PostitView PostitView;
    PostitPresenter PostitPresenter;
    public PostitHandler(PostitView PostitView, PostitPresenter PostitPresenter){
        this.PostitView = PostitView;
        this.PostitPresenter = PostitPresenter;

    }
    public void SetColor(String color){
        this.color = color;
    }
    public void PublishPostit(String topic, String text) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        String idOne = UUID.randomUUID().toString();

        String date = c.getTime().toString();

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
}
