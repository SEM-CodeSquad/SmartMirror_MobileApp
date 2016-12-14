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

import com.google.zxing.Result;
import java.util.concurrent.ExecutionException;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;

/**
 * The QrCodeHandler is responsible for the logic and result handling of the QrCodeView
 */

public class QrCodeHandler implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scannerView;
    private QrCodePresenter qrPres;

    /**
     * Constructor that sets scannerview and presenter. Sets this class as handler aswell
     * @param scannerView the view
     * @param qrPres the presenter
     */
    public QrCodeHandler(ZXingScannerView scannerView, QrCodePresenter qrPres){
        this.scannerView = scannerView;
        this.qrPres = qrPres;
        this.setHandler();
    }

    /**
     * Register ourselves as a handler for scan results.
     */
    private void setHandler() {
        scannerView.setResultHandler(this);
    }

    /*
    When we get result we handle it here, and set the mirror id with SetMirror, to the Id we get.
    Then we switch back fragment when done.
     */
    @Override
    public void handleResult(Result rawResult) {

        //set new mirror
        qrPres.setMirror(rawResult.getText());
        //Use retrieve data class to execute the pairing publishing
        JsonBuilder Ret = new JsonBuilder();
        String topic = qrPres.getMirrorID();
        String user = qrPres.getUser();
        try {
            Ret.execute(topic, "pairing", user).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //switch back to drawer from backbutton
        qrPres.toggledrawer();
        // switch activity back to settings
        qrPres.SwitchActivity();

    }


}
