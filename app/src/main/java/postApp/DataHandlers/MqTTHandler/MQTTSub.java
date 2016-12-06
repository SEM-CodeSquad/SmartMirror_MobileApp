package postApp.DataHandlers.MqTTHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;



public class MQTTSub extends Observable implements MqttCallback
{
    /**
     *
     */
    private MQTTClient client;

    public MQTTSub(MQTTClient client, String topic)
    {
        try
        {
            System.out.println(topic);
            this.client = client;
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        setChanged();
        notifyObservers(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}
