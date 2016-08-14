package de.kisner.jsm.controller.web.mqtt;

import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.jsm.JsmBootstrap;
import de.kisner.jsm.controller.web.netatmo.CliNetatmoRest;

public class CliMqttSubscriber
{	
	final static Logger logger = LoggerFactory.getLogger(CliMqttSubscriber.class);
	
	public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
	 public static final String TOPIC_TEMPERATURE = "home/temperature";
	 
    private MqttClient client;

    public CliMqttSubscriber()
    {
    	String clientId = UUID.randomUUID().toString();
         try
         {
        	 logger.info("Creating "+MqttClient.class.getSimpleName());
        	 client = new MqttClient(BROKER_URL, clientId);
              
        	 MqttConnectOptions options = new MqttConnectOptions();
        	 options.setCleanSession(false);
        	 
        	 client.setCallback(new CliMqttCallback());
        	 
        	 logger.info("Connecting "+MqttClient.class.getSimpleName());
        	 client.connect();
        	 logger.info("Connected "+MqttClient.class.getSimpleName());
        	 
        	 
        	 client.subscribe("home/#");
        	 logger.info("Subscribed "+MqttClient.class.getSimpleName());
        	 
         }
         catch (MqttException e)
        {
             e.printStackTrace();
             System.exit(1);
         }
    }
    
    public static void main(String[] args) throws Exception
	{
		Configuration config = JsmBootstrap.init();
		
		CliMqttSubscriber cli = new CliMqttSubscriber();
	}
}