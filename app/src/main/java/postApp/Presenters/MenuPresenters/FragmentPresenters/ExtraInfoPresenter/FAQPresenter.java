/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter;

import java.util.HashMap;
import java.util.List;

import postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView.FAQView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExtraInfoHandler.FAQHandler;

/**
 * Class responsible to interact with the handler and present the information to the view
 */

public class FAQPresenter {
    private FAQView FAQView;
    private FAQHandler FAQHandler;

    /**
     * The constructor that sets the view passed and instantiates a FAQHandler
     * @param FAQView the view
     */
    public FAQPresenter(FAQView FAQView) {
        this.FAQView = FAQView;
        FAQHandler = new FAQHandler();
    }

    /**
     * Sets the views adapter with all the Questions and answers we get from the handler
     */
    public void setAdapter() {
        HashMap<String, List<String>> faqQuestions = FAQHandler.getInfo();
        FAQView.SetAdapter(faqQuestions, FAQHandler.GetAnswer(faqQuestions));
    }
}
