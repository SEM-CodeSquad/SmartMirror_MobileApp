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
