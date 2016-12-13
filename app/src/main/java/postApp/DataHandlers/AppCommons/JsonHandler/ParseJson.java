package postApp.DataHandlers.AppCommons.JsonHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that parses the json data
 */

public class ParseJson {

    private String busID;
    private String busName;
    private Map<String, String> SearchMap;

    /**
     * Removes all nulls from a string
     * @param v the string
     * @return return a list as a array with the listsize
     */
    private static String[] clean(final String[] v) {
        List<String> list = new ArrayList<>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

    /**
     * Parses a json string and parses it down to just a name and a ID. The string that is passed is from the Västtrafiks API. And this parser method
     * Is adjusted after that. We also add the name and bus id to a map
     * @param json the json string
     * @param search the name we search for
     * @return the list but we use the method to clean the nulls from it first
     * @throws ParseException the exception
     */
    public String[] parseSearch(String json, String search) throws ParseException {
        JSONParser parser = new JSONParser();
        SearchMap = new HashMap<>();
        String newlist[] = new String[20];
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        if(jsonObject.size() > 0) {
            JSONObject obj = (JSONObject) jsonObject.get("LocationList");
            JSONArray jsarr = (JSONArray) obj.get("StopLocation");
            int size = jsarr.size();
            if(size > 20){
                size = 20;
            }

            for (int i = 0; i < size; i++) {

                JSONObject finalobj = (JSONObject) jsarr.get(i);
                String name;
                name = finalobj.get("name").toString();
                String busID;
                busID = finalobj.get("id").toString();
                if (search.toLowerCase().equals(name.substring(0, search.length()).toLowerCase())) {
                    newlist[i] = name;
                    SearchMap.put(name, busID);
                }
            }
        }
        return clean(newlist);
    }

    /**
     * if we want the ID for a busname we call this function. We get the ID from the map made above
     * @param BusStop The busttop name
     * @return the Bus ID
     */
    public String getBusIDfromSearch(String BusStop) {
        return SearchMap.get(BusStop);
    }

    /**
     * Json parser for the vässtrafiks api that gets the name and ID from the object
     * @param json the json string
     * @throws ParseException the exception
     */
    public void parseLoc(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONObject obj2 = (JSONObject) obj.get("StopLocation");
        Object name = obj2.get("name");
        busName = name.toString();
        busID = obj2.get("id").toString();
    }

    /**
     * To get the ID call this
     * @return the busID
     */
    public String getID() {
        return busID;
    }

    /**
     * @return Bus Name
     */
    public String getName() {
        return busName;
    }


}
