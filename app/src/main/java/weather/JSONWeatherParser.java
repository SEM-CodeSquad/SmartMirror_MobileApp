package weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nimish on 04/11/2016.
 */
public class JSONWeatherParser {

    public Weather [] getWeather(String data) throws JSONException {
        Weather[] wArray = new Weather[3];
        JSONObject jObject = new JSONObject(data);
        String cityName = getString("name", jObject);
        JSONArray jArray = jObject.getJSONArray("list");

        JSONObject jArrayObj1 = jArray.getJSONObject(0);
        Weather weather1 = new Weather();
        JSONObject tempObject = jArrayObj1.getJSONObject("temp");
        weather1.setTemp(tempObject.getDouble("day"));
        weather1.setMaxTemp(tempObject.getDouble("max"));
        weather1.setMinTemp(tempObject.getDouble("min"));
        wArray[0] = weather1;

        JSONObject jArrayObj2 = jArray.getJSONObject(1);
        Weather weather2 = new Weather();
        JSONObject tempObject2 = jArrayObj2.getJSONObject("temp");
        weather2.setTemp(tempObject2.getDouble("day"));
        weather2.setMaxTemp(tempObject2.getDouble("max"));
        weather2.setMinTemp(tempObject2.getDouble("min"));
        wArray[1] = weather1;

        JSONObject jArrayObj3 = jArray.getJSONObject(2);
        Weather weather3 = new Weather();
        JSONObject tempObject3 = jArrayObj3.getJSONObject("temp");
        weather3.setTemp(tempObject3.getDouble("day"));
        weather3.setMaxTemp(tempObject3.getDouble("max"));
        weather3.setMinTemp(tempObject3.getDouble("min"));
        wArray[2] = weather1;


        return wArray;
    }


    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

}