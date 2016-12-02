package postApp.DataHandlers.AppCommons.Vasttrafik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by adinH on 2016-11-02.
 */

public class GenerateAccessCode {
    String code;

    public String getAccess(){
        String[] command = {"curl", "-k", "-d", "grant_type=client_credentials", "-H", "Authorization: Basic VTJyWUFoYXNPem43dDFzX3pFZGJxNjRSR2k0YTpURXFmV0NkdkNzMlc4WjVtUlNrZ1NFN3MxSm9h", "https://api.vasttrafik.se:443/token"
        };
        String result = "";
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
             result = builder.substring(builder.lastIndexOf(":")+2, builder.length()-3);

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        code = result;
        return code;
    }
}