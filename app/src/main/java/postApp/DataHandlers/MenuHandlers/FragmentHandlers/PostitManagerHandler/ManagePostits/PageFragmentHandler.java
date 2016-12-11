package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits.PageFragment;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.AppCommons.Postits.EditPostit;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.DataHandlers.AppCommons.Postits.DeletePostit;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PostitManagerPresenter.ManagePostits.PageFragmentPresenter;

/**
 * Created by adinH on 2016-11-30.
 */

public class PageFragmentHandler implements Observer {
    private PageFragmentPresenter PageFragmentPresenter;
    private String idOne;
    private String text;
    private DeletePostit RemovePost;
    private EditPostit editPostit;

    public PageFragmentHandler(PageFragmentPresenter PageFragmentPresenter) {
        this.PageFragmentPresenter = PageFragmentPresenter;
    }


    public void SetColor(String color) {
        if (color.equals("yellow")) {
            PageFragmentPresenter.YellowClick();
        } else if (color.equals("purple")) {
            PageFragmentPresenter.PurpleClick();
        } else if (color.equals("pink")) {
            PageFragmentPresenter.PinkClick();
        } else if (color.equals("blue")) {
            PageFragmentPresenter.BlueClick();
        } else if (color.equals("green")) {
            PageFragmentPresenter.GreenClick();
        } else if (color.equals("orange")) {
            PageFragmentPresenter.OrangeClick();
        }
    }


    public void EditPostit(String topic, String id, String Text) {
        idOne = id;
        text = Text;
        JsonBuilder R = new JsonBuilder();
        String S;
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "postIt action", id, "edit", Text).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            if(S.length() > 2) {
                EditPost();
            }
            PageFragmentPresenter.ShowMessage(S);
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }


    public void DeletePostit(String topic, String UUID) {
        idOne = UUID;
        JsonBuilder R = new JsonBuilder();
        String S;
        if (topic != "No mirror chosen") {
            try {
                S = R.execute(topic, "postIt action", UUID, "delete", "none").get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println(e);
                S = "Warning: Did Not Publish";
            }
            if(S.length() > 2){
                RemovePost();
            }
            PageFragmentPresenter.ShowMessage(S);
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }


    public void RemovePost() {
        RemovePost = new DeletePostit(idOne);
        RemovePost.addObserver(this);
    }
    public void EditPost() {
        editPostit = new EditPostit(text, idOne);
        editPostit.addObserver(this);
    }


    @Override
    public void update(Observable observable, Object data) {
        PageFragmentPresenter.ReloadScreen();
    }
}
