package postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import postApp.ActivitiesView.MenuView.FragmentViews.PairingView.QrCodeView;
import postApp.DataHandlers.MenuHandlers.FragmentHandlers.PairingHandler.QrCodeHandler;

/**
 * Created by Emanuel on 19/11/2016.
 */

public class QrCodePresenter {
    private QrCodeView qrView;
    private QrCodeHandler qrHandler;
    private ZXingScannerView scannerView;

    public QrCodePresenter(QrCodeView qrCodeView) {
        this.qrView = qrCodeView;
        scannerView = qrView.getScannerView();
        qrHandler = new QrCodeHandler(qrView, scannerView ,this);
    }


}
