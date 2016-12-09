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
 * Created by adinH on 2016-11-09.
 */

public class ParseJson {

    private String busID;
    private String busName;
    private Map<String, String> SearchMap;

    private static String[] clean(final String[] v) {
        List<String> list = new ArrayList<>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

    public String[] parseSearch(String json, String search) throws ParseException {
        JSONParser parser = new JSONParser();
        SearchMap = new HashMap<>();
        String newlist[] = new String[20];
        System.out.println(json);
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONArray jsarr = (JSONArray) obj.get("StopLocation");
        for (int i = 0; i < jsarr.size(); i++) {

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
        return clean(newlist);
    }

    public String getBusIDfromSearch(String BusStop) {
        return SearchMap.get(BusStop);
    }

    //parses json for the closest stop location return a string with a name of a stop
    public void parseLoc(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONObject obj2 = (JSONObject) obj.get("StopLocation");
        Object name = obj2.get("name");
        busName = name.toString();
        busID = obj2.get("id").toString();
    }

    public String getID() {
        return busID;
    }

    public String getName() {
        return busName;
    }


}
