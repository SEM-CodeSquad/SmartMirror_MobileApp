package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
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
import postApp.DataHandlers.MqTTHandler.Echo;
import postApp.Presenters.MenuPresenters.FragmentPresenters.PreferencesPresenter.SettingsPresenter;

/**
 * Created by adinH on 2016-11-18.
 */

public class SettingsHandler implements Observer {

    private String auth;
    private String news;
    private String bus;
    private String busID;
    private String weather;
    private String user;
    private SettingsPresenter SettingsPresenter;
    private SettingsView SettingsView;
    private Echo echo;
    private boolean echoed = false;
    public SettingsHandler(SettingsPresenter SettingsPresenter, SettingsView SettingsView, String topic, String user) {
        this.SettingsPresenter = SettingsPresenter;
        this.SettingsView = SettingsView;
        echo = new Echo("dit029/SmartMirror/" + topic + "/echo", user);
        echo.addObserver(this);
        echo.disconnect();
    }

    public void StartBus() {
            SettingsPresenter.ShowBus();
    }

    public void StartNews() {
            SettingsPresenter.ShowNews();
    }

    public void WeatherOnLoc() {
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
    }

    public void PublishAll(String Topic, String User, String News, String Weather, String BusID , String Busname) {

        if (!Topic.equals("No mirror chosen")) {
            if(!News.equals("No feed selected") && !Weather.equals("No city selected") && !Busname.equals("No bus stop selected")) {
                SettingsPresenter.Loading();
                AwaitEcho();
                this.news = News;
                this.weather = Weather;
                this.user = User;
                this.busID = BusID;
                this.bus = Busname;
                StoreSettings();

                JsonBuilder R = new JsonBuilder();
                try {
                    R.execute(Topic, "config", User, News, Weather, BusID).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            else{
                SettingsPresenter.ChoseAllSettings();
            }
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
                            SettingsPresenter.SetBus(js.getName());
                        } catch (Exception v) {
                            System.out.println(v);
                        }
                    }

                });
    }

    //here we just get the city for the weather
    private String weathercity(double Lat, double Long){
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
            //returns final adress
            ((NavigationActivity) SettingsView.getActivity()).setWeather(finalAddress);
            SettingsPresenter.SetWeather(finalAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalAddress;
    }

    private void StoreSettings(){
        StoreSettings set = new StoreSettings(user, news, bus + ":" + busID, weather);
        set.addObserver(this);
    }

    private void AwaitEcho() {
        echo.connect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (echoed) {
                    SettingsPresenter.DoneLoading();
                    echoed = false;
                } else {

                    SettingsPresenter.NoEcho();
                    SettingsPresenter.DoneLoading();
                }
                Disc();
            }
        }, 2000); // 2000 milliseconds delay
    }


    /**
     * Method for removing observer and disconnecting
     */
    private void Disc(){
        echo.disconnect();
    }

    @Override
    public void update(Observable observable, Object o) {
            echoed = true;
    }
}
