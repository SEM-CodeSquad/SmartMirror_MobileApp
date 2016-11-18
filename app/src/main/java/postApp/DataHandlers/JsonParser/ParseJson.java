package postApp.DataHandlers.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by adinH on 2016-11-09.
 */

public class ParseJson {

    public String[] parseSearch(String json, String search) throws ParseException {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        String newlist[] = new String[20];
        System.out.println(json);
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONArray jsarr = (JSONArray) obj.get("StopLocation");
        for (int i = 0; i < jsarr.size(); i++) {

            JSONObject finalobj = (JSONObject) jsarr.get(i);
            Object name = finalobj.get("name");
            if (search.toLowerCase().equals(name.toString().substring(0,search.length()).toLowerCase())){
                newlist[i] =  name.toString();
            }
        }
        return clean(newlist);


    }

    //parses json for the closest stop location return a string with a name of a stop
    public String parseLoc(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject obj = (JSONObject) jsonObject.get("LocationList");
        JSONObject obj2 = (JSONObject) obj.get("StopLocation");
        Object name = obj2.get("name");
        return name.toString();
    }

    public static String[] clean(final String[] v) {
        List<String> list = new ArrayList<String>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new String[list.size()]);
    }

}
