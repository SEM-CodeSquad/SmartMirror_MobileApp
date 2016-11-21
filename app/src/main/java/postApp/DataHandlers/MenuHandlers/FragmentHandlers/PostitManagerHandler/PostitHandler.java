package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler;

import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.PostitView;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.PostitPresenter;


public class PostitHandler {
    private static String postitInfo;
    private String color;
    private PostitView PostitView;
    private PostitPresenter PostitPresenter;
    private String text;
    private String idOne;


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
                setPostitInfo();
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

    private void setPostitInfo(){
        this.postitInfo = this.color + "%" + this.text + "%" + this.idOne;
    }
    public static String getPostitInfo(){
        return postitInfo;
    }

}
