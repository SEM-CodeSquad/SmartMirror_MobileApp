package postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.HashMap;
import java.util.List;

import adin.postApp.R;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.FAQ.FAQAdapter;
import postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.FAQ.FAQPresenter;

/**
 * Created by adinH on 2016-12-14.
 */

public class FAQView extends Fragment {
    View myView;
    ExpandableListView Exp_list;
    FAQAdapter adapter;
    FAQPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.faq, container, false);
        presenter = new FAQPresenter(this);
        Exp_list = (ExpandableListView) myView.findViewById(R.id.expandablelist);
        presenter.setAdapter();
        Exp_list.setAdapter(adapter);
        return myView;
    }

    public void SetAdapter(HashMap<String, List<String>> QuestionCat, List<String> QuestionAns) {
        adapter = new FAQAdapter(getActivity(), QuestionCat, QuestionAns);
    };
    /**
     * On resume sets title to to FAQ
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("FAQ");
    }
}
