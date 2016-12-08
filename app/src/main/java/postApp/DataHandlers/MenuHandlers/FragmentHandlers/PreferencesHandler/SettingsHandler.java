package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import postApp.ActivitiesView.MenuView.FragmentViews.PreferencesView.SettingsView;
import postApp.ActivitiesView.MenuView.NavigationActivity;
import postApp.DataHandlers.AppCommons.JsonHandler.JsonBuilder;
import postApp.DataHandlers.AppCommons.JsonHandler.ParseJson;
import postApp.DataHandlers.AppCommons.Settings.StoreSettings;
import postApp.DataHandlers.AppCommons.Vasttrafik.GenerateAccessCode;
import postApp.DataHandlers.AppCommons.Vasttrafik.TravelByLoc;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsHandler {
    String auth;
    SettingsPresenter SettingsPresenter;
    SettingsView SettingsView;
    public SettingsHandler(SettingsPresenter SettingsPresenter, SettingsView SettingsView) {
        this.SettingsPresenter = SettingsPresenter;
        this.SettingsView = SettingsView;
    }

    public void StartBus() {
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {
            SettingsPresenter.ShowBus();
        } else {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            SettingsPresenter.NoMirrorChosen();
        }
    }

    public void StartNews() {
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {
            SettingsPresenter.ShowNews();
        } else {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            SettingsPresenter.NoMirrorChosen();
        }
    }

    public void WeatherOnLoc() {
        String topic = ((NavigationActivity) SettingsView.getActivity()).getMirror();
        if (topic != "No mirror chosen") {

            //using lib smartlocation
            SmartLocation.with(SettingsView.getActivity()).location()
                    //used for getting location just ones
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            //we get the city after lat and long
                            String city = weathercity(location.getLatitude(), location.getLongitude());
                            //set the weather to the city
                            ((NavigationActivity) SettingsView.getActivity()).setWeather(city);
                            //set text to city
                            SettingsView.weathertext.setText(city);
                        }
                    });
        } else {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            SettingsPresenter.NoMirrorChosen();
        }
    }

    public void PublishAll(String Topic, String User, String News, String Weather, String Bus) {
        JsonBuilder R = new JsonBuilder();
        String S;
        if (Topic != "No mirror chosen") {
            try {
                S = R.execute(Topic, "config", User, News, Weather, Bus).get();
            } catch (InterruptedException e) {
                S = "Did not publish";
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                S = "Warning: Did Not Publish";
            }
            SettingsPresenter.displaySuccPub(S);
        } else {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            SettingsPresenter.NoMirrorChosen();
        }
    }

    public void SetLocalStop() {
        //the first two lines generete a authorization key for the Västtrafik API.
        GenerateAccessCode gen = new GenerateAccessCode();
        auth = gen.getAccess();

        // if by location is chosen we use the SmartLocation lib once again to get the fixed location
        SmartLocation.with(SettingsView.getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Double Latitude = location.getLatitude();
                        Double Longitude = location.getLongitude();
                        try {
                            //when we got lat and long we execute the TravelByLoc with the parameters.
                            TravelByLoc trav = new TravelByLoc();
                            trav.execute(auth, String.valueOf(Latitude), String.valueOf(Longitude));
                            //our stop the is what we get() from the travelbyloc
                            ParseJson js = new ParseJson();
                            js.parseLoc(trav.get());

                            //we set the activities stop
                            ((NavigationActivity) SettingsView.getActivity()).SetBusID(js.getID());
                            ((NavigationActivity) SettingsView.getActivity()).setBus(js.getName());
                            //and the we set the bustext in the app to stop
                            SettingsPresenter.SetBus();
                        } catch (Exception v) {
                            System.out.println(v);
                        }
                    }

                });
    }

    //here we just get the city for the weather
    public String weathercity(double Lat, double Long){
        //will be final adress
        String finalAddress = "";
        //android lib geocoder that gets the current location
        Locale loc = new Locale("en");
        Geocoder geoCoder = new Geocoder(SettingsView.getActivity(), loc);
        try {
            // addresses with city, country, street etc in a list
            List<Address> address = geoCoder.getFromLocation(Lat, Long, 1);
            //extract adress from list
            Address myadd = address.get(0);
            //we get locality which is city only
            finalAddress = myadd.getLocality() + "," + myadd.getCountryName();
        } catch (IOException e) {}
        //returns final adress
        ((NavigationActivity) SettingsView.getActivity()).setWeather(finalAddress);
        return finalAddress;
    }

    public void StoreSettings(String user, String News, String Weather, String Bus){
        StoreSettings set = new StoreSettings(user, News, Bus, Weather);
        SettingsPresenter.UpdateScreen();
    }

}
