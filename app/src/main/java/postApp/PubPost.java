package postApp;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
/**
 * Created by adinH on 2016-10-04.
 */
public class PubPost {

        public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";

        public static final String TOPIC = "TestCodeHigh/Hi";

        private MqttClient client;


        public PubPost() {
            try {
                client = new MqttClient(BROKER_URL, MqttClient.generateClientId(), new MemoryPersistence());
            } catch (MqttException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void start() {

            try {
                client.connect();

                publishPostIt();

                client.disconnect();

            } catch (MqttException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void publishPostIt() throws MqttException {
            final MqttTopic temperatureTopic = client.getTopic(TOPIC);

            final MqttMessage message = new MqttMessage(String.valueOf("66666").getBytes());
            temperatureTopic.publish(message);
            System.out.println("done");
        }

    }
