package postApp.DataHandlers.AppCommons.Vasttrafik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class that generates a accesscode from vässtrafik
 */

public class GenerateAccessCode {

    String code;

    /**
     * A curl command that request authorization with secret key and access key from västtrafik
     * @return the code that is gotten from västtrafik
     */
    public String getAccess(){
        String[] command = {"curl", "-k", "-d", "grant_type=client_credentials", "-H", "Authorization: Basic VTJyWUFoYXNPem43dDFzX3pFZGJxNjRSR2k0YTpURXFmV0NkdkNzMlc4WjVtUlNrZ1NFN3MxSm9h",
                "https://api.vasttrafik.se:443/token"
        };
        String result = "";
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
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