package postApp.DataHandlers.MenuHandlers.FragmentHandlers.PreferencesHandler;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
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
 * Class used as a handler for the settings view that communicates with the presenter
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

    /**
     * The constructor that start a echo and adds a observer, we then disconnect to just keep the echo and don't have to instantiate it again
     * @param SettingsPresenter the presenter
     * @param SettingsView the view
     * @param mirror the mirror ID
     * @param user the user
     */
    public SettingsHandler(SettingsPresenter SettingsPresenter, SettingsView SettingsView, String mirror, String user) {
        this.SettingsPresenter = SettingsPresenter;
        this.SettingsView = SettingsView;
        echo = new Echo("dit029/SmartMirror/" + mirror + "/echo", user);
        echo.addObserver(this);
        echo.disconnect();
    }

    /**
     * This function uses library io.nlopez.smartlocation:library:3.2.9 that gets the location and only one time and sets the location in the
     * navigationactivity and calls the settings presenter to update the text
     */
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
                            SettingsPresenter.SetWeather(city);
                        }
                    });
    }

    /**
     * To publish all settings we call this function which starts a JSon builder then executes it with the below values
     * @param mirror the mirror
     * @param User the user
     * @param News the news
     * @param Weather the weather
     * @param BusID the bus ID
     * @param Busname the busname
     */
    public void PublishAll(String mirror, String User, String News, String Weather, String BusID , String Busname) {

        if (!mirror.equals("No mirror chosen")) {
            if(!News.equals("No feed selected") && !Weather.equals("No city selected") && !Busname.equals("No bus stop selected")) {
                SettingsPresenter.Loading();
                AwaitEcho();
                this.news = News;
                this.weather = Weather;
                this.user = User;
                this.busID = BusID;
                this.bus = Busname;
                JsonBuilder R = new JsonBuilder();
                R.execute(mirror, "config", User, "newsedit", News);

                JsonBuilder E = new JsonBuilder();
                E.execute(mirror, "config", User, "weatheredit", Weather);

                JsonBuilder P = new JsonBuilder();
                P.execute(mirror, "config", User, "busedit", BusID);
            }
            else{
                //If not all settings are chosen we call the presenters function
                SettingsPresenter.ChoseAllSettings();
            }
        } else {
            // if no mirror is chosen a.k.a topic is null we display a toast with chose a mirror
            SettingsPresenter.NoMirrorChosen();
        }
    }

    /**
     * This method uses io.nlopez.smartlocation:library:3.2.9 to get the locations latitude and logitude and then we use TravelByLoc and execute it
     * with the latitude and logitude gotten. And also the generated accesscode from the class GenerateAccessCode
     */
    public void SetLocalStop() {
        //the first two lines generete a authorization key for the Västtrafik API.
        GenerateAccessCode gen = new GenerateAccessCode();
        try {
            auth = gen.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

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

                            //we set the Navigationactivities stop and bus ID
                            ((NavigationActivity) SettingsView.getActivity()).SetBusID(js.getID());
                            ((NavigationActivity) SettingsView.getActivity()).setBus(js.getName());
                            //and the we set the bustext in the app to stop
                            SettingsPresenter.SetBus(js.getName());
                        } catch (Exception v) {
                            v.printStackTrace();
                        }
                    }

                });
    }

    /**
     * Uses androids geoCode that gets the city and country we are in. Sets the activities weather and calls the presenter to set the textfields weather.
     * @param Lat the latitude
     * @param Long the longitude
     * @return the adress
     */
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

    /**
     * Function used to storesettings and add a observer
     */
    private void StoreSettings(){
        StoreSettings set = new StoreSettings(user, news, bus + ":" + busID, weather);
    }

    /**
     * Function used to await a echo from the broker. We connect to the broker here and then we start a handler that is delayed by 2 seconds.
     * We do this because we give the echo 2 seconds to reach the broker. If reached we call the presenter to be done loading, if not we do the same but call also the
     * presenters function for no echo. At the end we call function disc()
     */
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
     * Method for disconnecting from the broker
     */
    private void Disc(){
        echo.disconnect();
    }

    /**
     * If we get a echo we store the settings by calling the function StoreSettings()
     * @param observable the observable
     * @param o the object
     */
    @Override
    public void update(Observable observable, Object o) {
            echoed = true;
            StoreSettings();
    }
}
