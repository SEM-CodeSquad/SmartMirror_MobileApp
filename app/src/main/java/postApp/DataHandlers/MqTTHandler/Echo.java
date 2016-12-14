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