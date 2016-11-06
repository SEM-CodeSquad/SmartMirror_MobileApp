package postApp.Controllers.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.Controllers.NavigationActivity;
import postApp.Controllers.logic.MqTTHandler.Retrievedata;


/**
 * Created by adinH on 2016-10-26.
 */
public class RemovePostit extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.remove_post, container, false);
        return myView;
    }
}
