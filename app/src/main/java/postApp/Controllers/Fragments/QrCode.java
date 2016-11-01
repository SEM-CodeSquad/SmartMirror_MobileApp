package postApp.Controllers.Fragments;

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

import android.util.Log;

import com.google.zxing.Result;


import adin.postApp.R;
import postApp.Controllers.NavigationActivity;
import postApp.MainActivity;


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

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        ((NavigationActivity) getActivity()).setMirror(rawResult.getText());
        System.out.println(((NavigationActivity) getActivity()).getMirror());

        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new Settings()).commit();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }
}



