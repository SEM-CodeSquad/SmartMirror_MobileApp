package postApp.Presenters.MenuPresenters.FragmentPresenters.ExtraInfoPresenter.FAQ;

    import java.util.HashMap;
    import java.util.List;

    import android.content.Context;
    import android.graphics.Typeface;
    import android.util.MonthDisplayHelper;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseExpandableListAdapter;
    import android.widget.TextView;

    import adin.postApp.R;

    public class FAQAdapter extends BaseExpandableListAdapter {
        private Context ctx;
        private HashMap<String, List<String>> FAQuestions;
        private List<String> FaqAnswers;

        public FAQAdapter(Context ctx, HashMap<String, List<String>> FAQuestions, List<String> FaqAnswers )
        {
            this.ctx = ctx;
            this.FAQuestions = FAQuestions;
            this.FaqAnswers = FaqAnswers;

        }

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
