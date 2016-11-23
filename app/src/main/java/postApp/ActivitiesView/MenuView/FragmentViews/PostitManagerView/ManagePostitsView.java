package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import adin.postApp.R;
import postApp.DataHandlers.Postits.ReadPostits;

/**
 * Created by adinH on 2016-10-26.
 */
public class ManagePostitsView extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mirror_postit, container, false);
        ReadPostits r = new ReadPostits(getActivity().getApplicationContext());
        String[] Aa = r.getPostitArray();
        System.out.println(Arrays.toString(Aa));
        return myView;
    }
}
