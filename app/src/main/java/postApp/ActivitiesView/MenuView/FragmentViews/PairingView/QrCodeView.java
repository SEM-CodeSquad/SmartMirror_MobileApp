package postApp.ActivitiesView.MenuView.FragmentViews.PairingView;

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

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import adin.postApp.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;

/**
 * This class is responsible for the QrCode Fragment view. This class handles the QR code reading
 */
public class QrCodeView extends Fragment  {
    private ZXingScannerView mScannerView;
    private QrCodePresenter qrPres;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private ProgressDialog progress;
    /**
     * When fragment is created this function is ran that starts the camera and sets the presenter
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance
     * @return the view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view
        progress = new ProgressDialog(getActivity());
        /**
         * Checks if you have camera permission, if you have you start camera and if you dont you ask for the permission
         */
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

    /**
     * Android function for requesting permission, if granted starts camera, else shows a toast that says you need to give permission
     * @param requestCode the request code
     * @param permissions the permissiom
     * @param grantResults the result
     */
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
                    //switch screen to QrCodeView frame
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new QrCodeView(), "QRFRAG").addToBackStack(null).commit();
                }
                else
                {
                    qrPres.ShowToast();
                }
            }
            break;
        }
    }

    /**
     * Shows a toast that you need to add a permission
     */
    public void ShowToast(){
        Toast.makeText(getActivity().getApplicationContext(),"To use this application you must add a permission for the camera",Toast.LENGTH_LONG).show();
    }

    /***
     * On pause we stop the camera
     */
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /**
     * We set the action bar title to MirroR ID
     */
    @Override
    public void onResume(){
        super.onResume();
        ((NavigationActivity) getActivity()).getSupportActionBar().setTitle("Mirror ID");
    }

    /**
     * @return this mScannerView
     */
    public ZXingScannerView getScannerView(){
        return this.mScannerView;
    }

    /**
     * Method to switch activity to settings
     */
    public void SwitchActivity() {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsView()).addToBackStack(null).commit();
    }


    /**
     * Method that shows a alertdialog that says wrong username or password.
     */
    public void UnsuccessfulPublish(){
        //if user types wrong login we show a alertdialog some text
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed to pair")
                .setMessage("Check connectivity on mirror and phone")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Loading method that shows a progressdialog
     */
    public void Loading(){
        progress.setMessage("Pairing");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    /**
     * method that dismisses the progressbar
     */
    public void DoneLoading(){
        progress.dismiss();
    }

    /**
     * Method to store the qrcode in the shared prefs
     * @param qrcode the code
     */
    public void UpdateSharedPrefs(String qrcode) {

        SharedPreferences prefs = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);
        prefs.edit().putString("Qrcode", qrcode).apply();
    }
}
