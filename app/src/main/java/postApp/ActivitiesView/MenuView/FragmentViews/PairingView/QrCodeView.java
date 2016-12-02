package postApp.ActivitiesView.MenuView.FragmentViews.PairingView;

/**
 * Created by Emanuel on 19/11/2016.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PairingPresenter.QrCodePresenter;


public class QrCodeView extends Fragment  {
    private ZXingScannerView mScannerView;
    private QrCodePresenter qrPres;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());   // Programmatically initialize the scanner view
        mScannerView.startCamera();         // Start camera
        qrPres = new QrCodePresenter(this);
        return mScannerView;
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
