package postApp.DataHandlers.MqTTHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;

/**
 * MQTT subscriber class that subscribes to a topic
 */

public class MQTTSub extends Observable implements MqttCallback
{

    /**
     * The constructor that setCallback to this and subscribes
     * @param client the mqttclient
     * @param topic the topic
     */
    public MQTTSub(MQTTClient client, String topic)
    {
        try
        {
            client.getClient().setCallback(this);
            client.getClient().subscribe(topic);
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * When conection is lost we printStackTrace
     * @param cause the throwable we printStackTrace from
     */
    @Override
    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    /**
     * When message is arrived we notify the observers
     * @param topic the topic
     * @param message the message
     * @throws Exception the exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        setChanged();
        notifyObservers(message);
    }

    /**
     * Not used but has to be there because of the superclass
     * @param token .
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}
