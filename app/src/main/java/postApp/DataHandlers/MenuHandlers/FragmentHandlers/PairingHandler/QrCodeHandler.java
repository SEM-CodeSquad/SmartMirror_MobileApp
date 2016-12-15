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

package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PairingHandler;

import android.os.CountDownTimer;
import android.os.Handler;

import com.google.zxing.Result;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;

/**
 * The QrCodeHandler is responsible for the logic and result handling of the QrCodeView
 */

public class QrCodeHandler implements ZXingScannerView.ResultHandler, Observer {
    private ZXingScannerView scannerView;
    private QrCodePresenter qrPres;
    private Boolean echoed = false;
    private Echo echo;
    private String user;
    private String qrcode;

    /**
     * Constructor that sets scannerview and presenter. Sets this class as handler aswell
     * @param scannerView the view
     * @param qrPres the presenter
     */
    public QrCodeHandler(ZXingScannerView scannerView, QrCodePresenter qrPres, String user){
        this.scannerView = scannerView;
        this.qrPres = qrPres;
        this.setHandler();
        this.user = user;
    }

    /**
     * Register ourselves as a handler for scan results.
     */
    private void setHandler() {
        scannerView.setResultHandler(this);
    }

    /*
    When we get result we handle it here, we start a echo and call echo for a result. We also execute a httprequest to pair with the mirror
     */
    @Override
    public void handleResult(Result rawResult) {
        qrcode = rawResult.getText();
        qrPres.Loading();
        echo = new Echo("dit029/SmartMirror/" + qrcode + "/echo", user);
        echo.addObserver(this);
        AwaitEcho();
        //Use retrieve data class to execute the pairing publishing
        JsonBuilder Ret = new JsonBuilder();
        try {
            Ret.execute(qrcode, "pairing", user).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
    /**
     * Method used for awaiting a echo. if echoed is true after 2 seconds we know we got a echo. If we get it we set the qrcode in the activity.
     * Else we tell the user pairing failed by calling the presenter method for no echo. If it succeed we store the qr code aswell in the shared preferences
     */
    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    qrPres.UpdateSharedPrefs(qrcode);
                    qrPres.DoneLoading();
                    //set new mirror
                    qrPres.setMirror(qrcode);
                    //switch back to drawer from backbutton
                    qrPres.toggledrawer();
                    // switch activity back to settings
                    qrPres.SwitchActivity();
                    echoed = false;
                } else {
                    qrPres.DoneLoading();
                    qrPres.NoEcho();
                    //after 0.5 seconds we switch to settingsactivity by calling the qrpres method
                    new CountDownTimer(1500,1000){
                        @Override
                        public void onTick(long millisUntilFinished){}

                        @Override
                        public void onFinish(){
                            qrPres.toggledrawer();
                            // switch activity back to settings
                            qrPres.SwitchActivity();
                        }
                    }.start();
                }
                Disc();
            }
        }, 3000); // 2000 milliseconds delay
    }

    /**
     * Method for removing observer and disconnecting
     */
    private void Disc() {
        echo.disconnect();
    }

    /**
     * Just a observable that waits for a notification from the echo class and sets echoed to true.
     * @param observable The observable
     * @param o The object
     */
    @Override
    public void update(Observable observable, Object o) {
        echoed = true;
        echo.deleteObserver(this);
    }
}
