package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PostitManagerHandler.ManagePostits;

import android.os.Handler;

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
 * This is a handler for the pagefragment
 */

public class PageFragmentHandler implements Observer {
    private PageFragmentPresenter PageFragmentPresenter;
    private String idOne;
    private String text;
    private DeletePostit RemovePost;
    private EditPostit editPostit;
    private Boolean echoed = false;
    private Echo echo;
    private String editordelete;
    private String message;

    /**
     * Constructor for the handler that injects the presenter and start a echo with a topic
     *
     * @param PageFragmentPresenter the presenter
     */
    public PageFragmentHandler(PageFragmentPresenter PageFragmentPresenter, String topic) {
        this.PageFragmentPresenter = PageFragmentPresenter;
        String topic123 = "dit029/SmartMirror/" + topic + "/echo";
        echo = new Echo(topic123, topic);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * Call the presenter to change different colors
     * @param color according to which color is in this string we change
     */
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

    /**
     * Method That publishes a edit to server
     *
     * @param topic the topic to publish to
     * @param id    The id of the postit
     * @param Text  The text of postit to change
     */
    public void EditPostit(String topic, String id, String Text) {
        if (!topic.equals("No mirror chosen")) {
            message = "Could not edit postit";
            PageFragmentPresenter.loading("Editing postit");
            editordelete = "edit";
            AwaitEcho();
            idOne = id;
            text = Text;
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "postIt action", id, "edit", Text).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }

    /**
     * Method That publishes a delete to server
     *
     * @param topic the topic to publish to
     * @param UUID  the uuid of the postit
     */
    public void DeletePostit(String topic, String UUID) {
        if (!topic.equals("No mirror chosen")) {
            message = "Could not delete postit";
            PageFragmentPresenter.loading("Deleting postit");
            editordelete = "delete";
            idOne = UUID;
            JsonBuilder R = new JsonBuilder();
            try {
                R.execute(topic, "postIt action", UUID, "delete", "none").get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            PageFragmentPresenter.NoMirror();
        }
    }

    /**
     * method for removing post
     */
    private void RemovePost() {
        RemovePost = new DeletePostit(idOne);
        RemovePost.addObserver(this);
    }

    /**
     * Method for editing post
     */
    private void EditPost() {
        editPostit = new EditPostit(text, idOne);
        editPostit.addObserver(this);
    }

    /**
     * Method used for awaiting a echo. if echoed is true after 2 seconds we know we got a echo else its false
     */
    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    PageFragmentPresenter.DoneLoading();
                    PageFragmentPresenter.ReloadScreen();
                    echoed = false;
                } else {
                    PageFragmentPresenter.NoEcho(message);
                    PageFragmentPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    private void Disc() {
        echo.disconnect();
    }

    /**
     * Method that fetches the observers update and if we first receive a echo we start a edit or delete based on function
     * If the data is instanceof editpost we check if getedited status, if its true then we know everything went well
     * If the data is instanceof deletepostit we check if getedited status, if its true then we know everything went well
     * @param observable the observable
     * @param data the data object
     */
    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof EditPostit) {
            if(editPostit.getEditedStatus()){
                echoed = true;
            }
        }
        else if (data instanceof DeletePostit) {
            if(RemovePost.getDeletedStatus()){
                echoed = true;
            }
        }
        else if (data instanceof Echo) {
            if(editordelete.equals("edit")){
                EditPost();
            }
            else if(editordelete.equals("delete")){
                RemovePost();
            }
        }
    }
}
