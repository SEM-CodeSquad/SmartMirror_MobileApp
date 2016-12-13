package postApp.ActivitiesView.MenuView.FragmentViews.PairingView;

/**
 * Created by Emanuel on 19/11/2016.
 */
import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;


public class QrCodeView extends Fragment  {
    private ZXingScannerView mScannerView;
    private QrCodePresenter qrPres;
    public static final int PERMISSION_REQUEST_CAMERA = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CAMERA);

            }
        else{
            mScannerView.startCamera();
        }

        qrPres = new QrCodePresenter(this);
        return mScannerView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.length == 0 || grantResults.length == 0)
            return;

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CAMERA:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    mScannerView.startCamera();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"To use this application you must add a permission for the camera",Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    public void onBackPressed() {
        ((NavigationActivity) getActivity()).toggleDrawerUse(true);
    }
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror ID");
    }

    public ZXingScannerView getScannerView(){
        return this.mScannerView;
    }
}
