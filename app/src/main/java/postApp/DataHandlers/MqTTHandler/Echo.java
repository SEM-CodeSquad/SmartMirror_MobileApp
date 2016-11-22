package postApp.DataHandlers.MqTTHandler;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Observable;
import java.util.Observer;

import postApp.DataHandlers.Postits.StorePostits;

/**
 In progress will finish asap
 */
public class Echo extends  Observable implements Observer
{
    private MQTTClient client;
    private String echoTopic;
    private MQTTSub subscriber;

    public Echo(String topic)
    {
        this.echoTopic = topic;
        String ClientID = topic.substring(19, topic.length() - 7);
        client = new MQTTClient("tcp://codehigh.ddns.me", ClientID );  //change this to prata broker later
        subscriber = new MQTTSub(client, echoTopic);
        subscriber.addObserver(this);
    }




    @Override
    public void update(Observable o, Object data) {
        if(data instanceof MqttMessage){
            setChanged();
            notifyObservers();
        }

    }
}