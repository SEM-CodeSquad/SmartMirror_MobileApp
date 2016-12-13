package postApp.DataHandlers.MenuHandlers;


/**
 * Class responsible as a model for the navigation interactor. The strings in this class are shared by all fragments
 */
public class NavigationInteractor {

    private String mirrorID = "No mirror chosen";
    private String newsID = "No news chosen";
    private String busname = "No bus or tram stop chosen";
    private String weatherID = "No city chosen";
    private String user;
    private String busIDVäst = "No ID";


    public NavigationInteractor(){
    }

    /**
     * @return mirror ID
     */
    public String getMirror(){
        return mirrorID;
    }

    /**
     * Sets mirror id
     * @param UUID the id
     */
    public void setMirror(String UUID){
        this.mirrorID = UUID;
    }

    /**
     * @return The bus stop name
     */
    public String getBus(){
        return busname;
    }

    /**
     * Sets the bus name
     * @param busname the busname
     */
    public void setBus(String busname){
        this.busname = busname;
    }

    /**
     * @return The weather city name
     */
    public String getWeather(){
        return weatherID;
    }

    /**
     * Sets the weather city name
     * @param W the weather name
     */
    public void setWeather(String W){
        weatherID = W ;
    }

    /**
     * @return news source
     */
    public String getNews(){
        return newsID;
    }

    /**
     * Set news source
     * @param N the news source
     */
    public void setNews(String N){
        newsID = N ;
    }

    /**
     * Set the user
     * @param user the username
     */
    public void SetUser(String user){
        this.user = user;
    }

    /**
     * @return username
     */
    public String getUser(){
        return user;
    }

    /**
     * Set the bus id västtrafik gives us from their API
     * @param busID the bus ID
     */
    public void SetBusID(String busID){this.busIDVäst = busID;}

    /**
     * @return Västtrafiks busID
     */
    public String GetBusID(){
        return busIDVäst;
    }

}
