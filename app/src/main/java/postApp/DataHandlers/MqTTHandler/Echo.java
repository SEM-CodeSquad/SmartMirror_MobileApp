package postApp.DataHandlers.MqTTHandler;


import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 In progress will finish asap
 */
public class Echo extends  Observable implements Observer
{
    private MQTTClient client;
    private String echoTopic;
    private MQTTSub subscriber;
    private String clientID;
    private String Uuid;

    public Echo(String topic, String ClientID)
    {
        this.clientID = ClientID;
        this.echoTopic = topic;
        MemoryPersistence persistence = new MemoryPersistence();
        Uuid = UUID.randomUUID().toString();
        client = new MQTTClient("tcp://codehigh.ddns.me", clientID + Uuid, persistence);  //change this to prata broker later
        subscriber = new MQTTSub(client, echoTopic);
        subscriber.addObserver(this);
    }

    @Override
    public void update(Observable o, Object data) {
            setChanged();
            notifyObservers();
    }
}