package postApp.DataHandlers.Network.MqTTHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/*
In progress will finish asap
 */
public class MqTTSub
{
    private MqttConnectOptions options;
    private MqttClient client;
    private long unixTime;

    public MqTTSub(String url, String id)
    {

    }

    public void disconnect(String clientId)
    {
        System.out.println("Disconnecting...");
        try
        {
            String topic = "dit029/SmartMirror/" + clientId;
            byte[] emptyArray = new byte[0];
            this.client.publish(topic, emptyArray, 1, true);

            client.disconnect();
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

    void reconnect()
    {
        if (!client.isConnected())
        {
            try
            {
                client.connect(options);
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
        }
    }


    MqttClient getClient()
    {
        return client;
    }

    public long getUnixTime()
    {
        return this.unixTime;
    }


}