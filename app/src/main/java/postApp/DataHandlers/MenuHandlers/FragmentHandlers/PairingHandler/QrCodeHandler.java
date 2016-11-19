package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PairingHandler;

import com.google.zxing.Result;

import java.util.concurrent.ExecutionException;

import adin.postApp.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.JsonHandler.JsonBuilder;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class QrCodeHandler implements ZXingScannerView.ResultHandler{
    QrCodeView qrView;
    ZXingScannerView scannerView;
    QrCodePresenter qrPres;

    public QrCodeHandler(QrCodeView qrView, ZXingScannerView scannerView, QrCodePresenter qrPres){
        this.qrView = qrView;
        this.scannerView = scannerView;
        this.qrPres = qrPres;
        this.setHandler();
    }


    public void setHandler() {
        scannerView.setResultHandler(this);  // Register ourselves as a handler for scan results.
    }

    /*
    When we get result we handle it here, and set the mirror id with SetMirror, to the Id we get.
    Then we switch back fragment when done.
     */
    @Override
    public void handleResult(Result rawResult) {

        //set new mirror
        ((NavigationActivity) qrView.getActivity()).setMirror(rawResult.getText());
        //Use retrieve data class to execute the pairing publishing
        JsonBuilder Ret = new JsonBuilder();
        String topic = ((NavigationActivity) qrView.getActivity()).getMirror();
        try {
            Ret.execute(topic, "pairing", "new client").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }
        //switch back to drawer from backbutton
        ((NavigationActivity) qrView.getActivity()).toggleDrawerUse(true);
        // swithc activity back to settings
        qrView.getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsView()).commit();


        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }


}
