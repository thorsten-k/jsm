package de.kisner.jsm.controller.web.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMqttCallback implements MqttCallback
{	
	final static Logger logger = LoggerFactory.getLogger(CliMqttCallback.class);
	
	@Override
    public void connectionLost(Throwable cause) {}

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception
    {
         System.out.println("Message arrived. Topic: " + topic + " Message: " + message.toString());

         if ("home/LWT".equals(topic))
         {
              System.err.println("Sensor gone!");
         }
    }

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}