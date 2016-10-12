package postApp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class responsible for establishing http connection with web server and send data to it.
 */
public class HttpRequestSender
{

    private String brokerHostname;
    private String clientId;
    private String topic;
    private String msg;
    private String httpResponse;

    /**
     * Constructor for the http sender.
     * @param brokerHostname String hostname for the broker
     * @param clientId String client id in the broker
     * @param topic String topic to be published on the broker
     * @param msg String message to be published in the broker
     */
    private HttpRequestSender(String brokerHostname, String clientId, String topic, String msg)
    {
        this.brokerHostname = brokerHostname;
        this.clientId = clientId;
        this.topic = topic;
        this.msg = msg;
    }

    /**
     * This method connects to a web server and send data to it the web server to be used is
     * "http://codehigh.ddns.net:5000/"
     * @param targetURL String containing the url and port to the web server
     */
    private void executePost(String targetURL)
    {
        String urlParameters  = "Hostname: " + this.brokerHostname + " ClientId: " + this.clientId + " Topic: " +
                this.topic + " Message: " + this.msg + " end";
        byte[] postData       = new byte[0];
        /*
        On java teh next if statement is not necessary so need to check if still ok on android
         */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        }
        int    postDataLength = postData.length;
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects( false );
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "Application/SmartMirror");
            connection.setRequestProperty("Content-Language", "utf-8");
            connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches(false);

            //send data
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write( postData );

            //Get Response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            this.httpResponse = response.toString();

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    /**
     *  Method to get the String containing the response from the web server.
     * @return String web server response
     */
    private String getHttpResponse()
    {
        return httpResponse;
    }

}

