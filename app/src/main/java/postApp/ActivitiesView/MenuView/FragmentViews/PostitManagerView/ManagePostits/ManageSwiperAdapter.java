package postApp.ActivitiesView.MenuView.FragmentViews.PostitManagerView.ManagePostits;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Created by adinH on 2016-11-29.
 */

public class ManageSwiperAdapter extends FragmentStatePagerAdapter {

    JSONArray json;
    public ManageSwiperAdapter(FragmentManager fm, JSONArray json){
       super(fm);
        this.json = json;
    }

    @Override
    public Fragment getItem(int position) {

        JSONObject finalobj = (JSONObject) json.get(position);
        Fragment fragment = new PageFragment();
        Long timestamp = Long.parseLong(finalobj.get("Timestamp").toString());
        Date date = new Date ();
        date.setTime(timestamp*1000);
        Bundle bundle = new Bundle();
        bundle.putString("Timestamp", date.toString());
        bundle.putString("ID", finalobj.get("PostitID").toString());
        bundle.putString("Text", finalobj.get("Text").toString());
        bundle.putString("Color", finalobj.get("Color").toString());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return json.size();
    }
}
