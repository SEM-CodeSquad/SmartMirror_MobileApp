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
import postApp.Activities.NavigationActivity.NavigationActivity;
import postApp.DataHandlers.Network.MqTTHandler.Retrievedata;

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
        // Do something with the result here

        ((NavigationActivity) getActivity()).setMirror(rawResult.getText());
        Retrievedata Ret = new Retrievedata();
        String topic = ((NavigationActivity) getActivity()).getMirror();
        String S = "";
        try {
            S = Ret.execute(topic, "pairing", "new client").get();
        } catch (InterruptedException e) {
            S = "Did not publish";
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            S = "Warning: Did Not Publish";
        }

        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFrag()).commit();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}



