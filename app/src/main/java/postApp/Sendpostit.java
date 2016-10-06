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
public class Sendpostit {


        public Sendpostit() {
            try {

            } catch (Exception e) {
                System.exit(1);
            }
        }

        public void start() {
            try {
                publishPostIt();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void publishPostIt()  {
            System.out.println("done");
        }

    }
