package postApp.Controllers.Fragments;

/**
 * Created by adinH on 2016-10-27.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import adin.postApp.R;
import postApp.Controllers.Fragments.QrCode;
import postApp.Controllers.NavigationActivity;
import postApp.MainActivity;


public class Settings extends Fragment {
    View myView;
    QrCode newQr;
    public EditText UUID;
    private View.OnClickListener mOriginalListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.settings, container, false);
        Button QrCodebtn = (Button)myView.findViewById(R.id.mirrorbtn);
        EditText edit = (EditText)myView.findViewById(R.id.mirrortext);
        edit.setText(((NavigationActivity) getActivity()).getMirror());

        QrCodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQr = new QrCode();
                mOriginalListener = ((NavigationActivity) getActivity()).toggle.getToolbarNavigationClickListener();
                ((NavigationActivity) getActivity()).toggleDrawerUse(false);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, newQr).commit();
            }
        });
        UUID = (EditText)myView.findViewById(R.id.mirrortext);
        UUID.setText(((NavigationActivity)getActivity()).getMirror());

        return myView;
    }



}
