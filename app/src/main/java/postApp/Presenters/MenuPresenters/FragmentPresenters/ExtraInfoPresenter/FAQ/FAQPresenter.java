package postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.FAQ;

import java.util.HashMap;
import java.util.List;

import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.FAQView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExtraInfoHandler.FAQHandler;

/**
 * Created by adinH on 2016-12-14.
 */

public class FAQPresenter {
    private FAQView FAQView;
    private FAQHandler FAQHandler;

    public FAQPresenter(FAQView FAQView) {
        this.FAQView = FAQView;
        FAQHandler = new FAQHandler();
    }

    public void setAdapter() {
        HashMap<String, List<String>> faqQuestions = FAQHandler.getInfo();
        FAQView.SetAdapter(faqQuestions, FAQHandler.GetAnswer(faqQuestions));
    }
}
