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

package postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PairingHandler.QrCodeHandler;

/**
 * Class that is the presenter for the QrCodeView
 */

public class QrCodePresenter {
    private QrCodeView qrView;

    /**
     * Constructor for this class that instantiates a QrCodeHandler with the view
     * @param qrCodeView the view
     */
    public QrCodePresenter(QrCodeView qrCodeView) {
        this.qrView = qrCodeView;
        ZXingScannerView scannerView = qrView.getScannerView();
        new QrCodeHandler(scannerView,this, getUser());
    }

    /**
     * Calls the views function to show toast
     */
    public void ShowToast() {
        qrView.ShowToast();
    }

    /**
     * Calls the views function to switch activity
     */
    public void SwitchActivity() {
        qrView.SwitchActivity();
    }
    /**
     * Calls the views function to toggle drawer
     */
    public void toggledrawer() {
        ((NavigationActivity) qrView.getActivity()).toggleDrawerUse(true);
    }
    /**
     * Sets navigationactivites mirror
     */
    public void setMirror(String text) {
        ((NavigationActivity) qrView.getActivity()).setMirror(text);
    }
    /**
     * Gets the mirror id
     */
    public String getUser() {
        return ((NavigationActivity) qrView.getActivity()).getUser();
    }

    public void Loading() {
        qrView.Loading();
    }

    /**
     * Call the views function when we receive no echo
     */
    public void NoEcho(){
        qrView.UnsuccessfulPublish();
    }

    /**
     * Call the views function when we are done loading
     */
    public void DoneLoading() {
        qrView.DoneLoading();
    }

    /**
     * Calls the method in the view to update SharedPreferences
     * @param qrcode the code to store
     */
    public void UpdateSharedPrefs(String qrcode) {
        qrView.UpdateSharedPrefs(qrcode);
    }
}
