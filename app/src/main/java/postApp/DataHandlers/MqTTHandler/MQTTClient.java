/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package postApp.DataHandlers.MqTTHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Class resposible for making a MQTTClient connection.
 */
public class MQTTClient {
    private MqttConnectOptions options;
    private MqttClient client;
    private String clientid;

    /**
     * Constructor that connect the MQTT client and instantiates it.
     * @param url the url we connect to
     * @param id the id
     * @param persistence A new persistance to avoid errors
     */
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

    /**
     * Method for disconnecting and publishing a presence message to prata
     */
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

    /**
     * Method for reconnecting
     */
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

    /**
     * @return the client
     */
    MqttClient getClient()
    {
        return client;
    }

}
