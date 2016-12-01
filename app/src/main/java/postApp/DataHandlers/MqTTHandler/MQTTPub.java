package postApp.DataHandlers.MqTTHandler;

/**
 * Created by Geoffrey on 2016/12/1.
 */


import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Collections;
import java.util.Observable;



public class MQTTPub extends Observable {
    private MQTTClient client;


    public MQTTPub(MQTTClient client) {
        this.client = client;
    }

    public void publish(String topic, String content) {
        try {
            byte[] payload = content.getBytes();
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(payload);
            this.client.getClient().publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
            setChanged();
            notifyObservers(this);
        }
    }


    @SuppressWarnings({"unchecked", "MismatchedQueryAndUpdateOfCollection"})
    public void publishPresenceMessage(String version, String groupName, int groupNumber, String clientVersion,
                                       String clientId, String... rfcs) {
        String gNum = String.valueOf(groupNumber);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", version);
        jsonObject.put("groupName", groupName);
        jsonObject.put("groupNumber", gNum);
        JSONArray rfcArray = new JSONArray();
        Collections.addAll(rfcArray, rfcs);
        jsonObject.put("rfcs", rfcArray.toString());
        jsonObject.put("clientVersion", clientVersion);
        jsonObject.put("clientSoftware", "SmartMirror");

        System.out.println(jsonObject.toString());

        byte[] presenceMessage = jsonObject.toString().getBytes();

        String topic = "presence/" + clientId;
        try {
            this.client.getClient().publish(topic, presenceMessage, 1, true);

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
