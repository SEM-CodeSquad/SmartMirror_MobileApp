package postApp.DataHandlers.MqTTHandler;


import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * Class that start a echo mqttclient and listens to a topic
 */
public class Echo extends  Observable implements Observer
{
    private MQTTClient client;

    /**
     * Constructor for the echo that starts a new mqtt client and a new mqtt sub and adds a observer
     * @param topic the topic
     * @param ClientID the clientID
     */
    public Echo(String topic, String ClientID)
    {
        MemoryPersistence persistence = new MemoryPersistence();
        String uuid = UUID.randomUUID().toString();
        client = new MQTTClient("tcp://54.154.153.243", ClientID + uuid, persistence);  //change this to prata broker later
        MQTTSub subscriber = new MQTTSub(client, topic);
        subscriber.addObserver(this);
    }

    /**
     * When a update reaches we notify the observers
     * @param o the observable
     * @param data the object
     */
    @Override
    public void update(Observable o, Object data) {
            setChanged();
            notifyObservers();
    }

    /**
     * To disconenct we call the MQTTclients function to disconnect
     */
    public void disconnect(){
        client.disconnect();
    }
    /**
     * To reconnect we call the MQTTclients function to reconnect
     */
    public void connect(){
        client.reconnect();
    }
}