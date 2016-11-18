package postApp.Activities.NavigationActivity.Fragments;

/**
 * Created by adinH on 2016-10-30.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import com.google.zxing.Result;


import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import postApp.Activities.NavigationActivity.Fragments.SettingsFrag.SettingsFrag;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.JsonBuilder;

/*
Simple qrcode scanner that implements the ZXING Scanner library.
 */
public class QrCode extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

        return mScannerView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /*
    When we get result we handle it here, and set the mirror id with SetMirror, to the Id we get.
    Then we switch back fragment when done.

     */
    @Override
    public void handleResult(Result rawResult) {

        //set new mirror
        ((NavigationActivity) getActivity()).setMirror(rawResult.getText());
        //Use retrieve data class to execute the pairing publishing
        JsonBuilder Ret = new JsonBuilder();
        String topic = ((NavigationActivity) getActivity()).getMirror();
        try {
           Ret.execute(topic, "pairing", "new client").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }
       //swithc back to drawer from backbutton
        ((NavigationActivity) getActivity()).toggleDrawerUse(true);
        // swithc activity back to settings
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();


        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}



