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

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.ExtraInfoHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that just makes a hashmap of a string and a list
 */

public class FAQHandler {

    public FAQHandler() {
    }

    /**
     * @return The hashmap with the all the FAQ strings
     */
    public HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> FAQ = new HashMap<>();

        List<String> Pair_mirror= new ArrayList<>();
        Pair_mirror.add("Press on the options menu on the top right corner and then press pair mirror or go to settings then press Change Mirror.");

        List<String> Hide_prefs = new ArrayList<>();
        Hide_prefs.add("To hide any preferences from the mirror go to Preferences and then chose all widgets you would like to show on the mirror.");

        List<String> BusOrShopping= new ArrayList<>();
        BusOrShopping.add("Because of limited space on the mirror display we chose to have either the Bus Timetable or the Shopping list.");

        List<String> SearchForStop = new ArrayList<>();
        SearchForStop.add("To search for your stop press the options menu in the top right corner and then press settings. After that press Change Bus Stop and then you can search for your stop.");

        List<String> EditPost = new ArrayList<>();
        EditPost.add("To edit your post-it press the Manage Mirror Post-Its menu button and then type in your text and press Edit Post-it.");

        List<String> DeletePost = new ArrayList<>();
        DeletePost.add("To delete your post-it press the Manage Mirror Post-Its menu button and press Delete Post-it.");

        List<String> WeatherCity = new ArrayList<>();
        WeatherCity.add("The weather is using your GPS location to localize the city you are in and the weather displayed on the mirror will be based on that city.");

        List<String> NewsOutlet = new ArrayList<>();
        NewsOutlet.add("Currently we support only those six news sources. In the future we plan to expand to the customers needs.");

        List<String> DatePick = new ArrayList<>();
        DatePick.add("If you do not pick a date the post-it will expire in 5 days as that is our standard value.");

        List<String> NoAnswer = new ArrayList<>();
        NoAnswer.add("If you can not find a answer for your question here. Please send us a email.");



        FAQ.put("How do I pair with my mirror?", Pair_mirror);
        FAQ.put("How do I hide widgets from the mirror?", Hide_prefs);
        FAQ.put("Why can't I display both the Bus timetable and the Shopping list?", BusOrShopping);
        FAQ.put("I want to search for my Bus stop, how do I do it?", SearchForStop);
        FAQ.put("How do I edit a post-it?", EditPost);
        FAQ.put("How do I delete a post-it?", DeletePost);
        FAQ.put("How is the weather city chosen?", WeatherCity);
        FAQ.put("I would like a different news source, is it possible?", NewsOutlet);
        FAQ.put("How long does the post-it last if I don't pick a date?", DatePick);
        FAQ.put("You can not find your question here?", NoAnswer);

        return FAQ;

    }

    /**
     * @param FaqQuestions The hashmap
     * @return All the keys in this hashmap
     */
    public ArrayList<String> GetAnswer(    HashMap<String, List<String>> FaqQuestions  ){
        return new ArrayList<>(FaqQuestions.keySet());
    }

}
