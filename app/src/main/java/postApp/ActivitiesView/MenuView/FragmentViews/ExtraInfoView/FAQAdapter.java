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

package postApp.ActivitiesView.MenuView.FragmentViews.ExtraInfoView;

    import java.util.HashMap;
    import java.util.List;

    import android.content.Context;
    import android.graphics.Typeface;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseExpandableListAdapter;
    import android.widget.TextView;
    import adin.postApp.R;

/**
 * Class responsible for the adapter that is used for the expandable listview.
 */
class FAQAdapter extends BaseExpandableListAdapter {
        private Context ctx;
        private HashMap<String, List<String>> FAQuestions;
        private List<String> FaqAnswers;

    /**
     * Sets the context, the questions and the answers.
     * @param ctx the context(Activity) passed
     * @param FAQuestions The FAQ questions as a hashmap
     * @param FaqAnswers And the Faq Answers
     */
        FAQAdapter(Context ctx, HashMap<String, List<String>> FAQuestions, List<String> FaqAnswers)
        {
            this.ctx = ctx;
            this.FAQuestions = FAQuestions;
            this.FaqAnswers = FaqAnswers;

        }

    /**
     * Gets item at the index of parent and child
     * @param parent the parent
     * @param child the child
     * @return Child Object
     */
        @Override
        public Object getChild(int parent, int child) {
            return FAQuestions.get(FaqAnswers.get(parent)).get(child);
        }

        @Override
        public long getChildId(int parent, int child) {
            // TODO Auto-generated method stub
            return child;
        }

        @Override
        public View getChildView(int parent, int child, boolean lastChild, View convertview,
                                 ViewGroup parentview)
        {
            String child_title =  (String) getChild(parent, child);
            if(convertview == null)
            {
                LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertview = inflator.inflate(R.layout.faqchild, parentview,false);
            }
            TextView child_textview = (TextView) convertview.findViewById(R.id.child_txt);
            child_textview.setText(child_title);


            return convertview;
        }

        @Override
        public int getChildrenCount(int arg0) {

            return FAQuestions.get(FaqAnswers.get(arg0)).size();
        }

        @Override
        public Object getGroup(int arg0) {
            // TODO Auto-generated method stub
            return FaqAnswers.get(arg0);
        }

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return FaqAnswers.size();
        }

        @Override
        public long getGroupId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview) {
            // TODO Auto-generated method stub
            String group_title = (String) getGroup(parent);
            if(convertview == null)
            {
                LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertview = inflator.inflate(R.layout.faqparent, parentview,false);
            }
            TextView parent_textview = (TextView) convertview.findViewById(R.id.parent_txt);
            parent_textview.setTypeface(null, Typeface.BOLD);
            parent_textview.setText(group_title);
            return convertview;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            // TODO Auto-generated method stub
            return false;
        }
}
