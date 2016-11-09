package postApp.Activities.NavigationActivity.Fragments;

/**
 * Created by adinH on 2016-10-27.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import adin.postApp.R;


public class Preferences extends Fragment {
        View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preferences, container, false);
        return myView;
        }
}
