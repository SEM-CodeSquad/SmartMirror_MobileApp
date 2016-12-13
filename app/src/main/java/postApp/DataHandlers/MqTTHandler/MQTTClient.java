package postApp.DataHandlers.MqTTHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTClient {
    private MqttConnectOptions options;
    private MqttClient client;
    private String clientid;

    public MQTTClient(String url, String id, MemoryPersistence persistence)
    {
        try
        {
            this.clientid = id;
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(20);
            client = new MqttClient(url, id, persistence);
            client.connect(options);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
            disconnect();
            reconnect();
        }
    }

    void disconnect()
    {
        if (this.client.isConnected())
        {
            try
            {
                String topic = "presence/" + this.clientid;
                byte[] emptyArray = new byte[0];
                this.client.publish(topic, emptyArray, 1, true);

                client.disconnect();
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
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


    public MqttClient getClient()
    {
        return client;
    }

}
