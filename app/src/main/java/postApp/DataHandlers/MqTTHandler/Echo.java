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
    private TEMPNAMEMQTTSub subscriber;

    public Echo(String topic, String ClientID)
    {
        this.echoTopic = topic;
        MemoryPersistence persistence = new MemoryPersistence();
        String Uuid = UUID.randomUUID().toString();
        client = new MQTTClient("tcp://codehigh.ddns.me", ClientID + Uuid, persistence);  //change this to prata broker later
        subscriber = new TEMPNAMEMQTTSub(client, echoTopic);
        subscriber.addObserver(this);
    }


    @Override
    public void update(Observable o, Object data) {
            setChanged();
            notifyObservers();
    }
}