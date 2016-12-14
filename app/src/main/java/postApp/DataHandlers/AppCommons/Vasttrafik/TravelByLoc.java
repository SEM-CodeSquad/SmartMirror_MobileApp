/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.DataHandlers.AppCommons.Vasttrafik;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that Uses västtrafiks api to get the currents locations closest stop, using the authorization bearer we got from
 * generateaccesscode

 */
public class TravelByLoc extends AsyncTask<String, Void, String> {

    /**
     * Asynctask that gets the closest stop
     * @param args args[0] is generateaccess code, args[1] is lat cordinate, third is args[2] coordinate.
     * @return the closest stop as a string
     */
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        URL url;
        try {

            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/location.nearbystops?originCoordLat=" + args[1] + "&originCoordLong=" + args[2] + "&maxNo=1&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty( "Authorization:", "Bearer " + args[0]);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }


}