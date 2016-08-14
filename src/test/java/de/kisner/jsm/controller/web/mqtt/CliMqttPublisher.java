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

public class CliMqttPublisher
{	
	final static Logger logger = LoggerFactory.getLogger(CliMqttPublisher.class);
	
	public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
	 public static final String TOPIC_TEMPERATURE = "home/temperature";
	 
    private MqttClient client;

    public CliMqttPublisher()
    {
    	String clientId = UUID.randomUUID().toString();
         try
         {
        	 logger.info("Creating "+MqttClient.class.getSimpleName());
        	 client = new MqttClient(BROKER_URL, clientId);
              
        	 MqttConnectOptions options = new MqttConnectOptions();
        	 options.setCleanSession(false);
        	 options.setWill(client.getTopic("home/LWT"),"I'm gone".getBytes(), 2, true);
        	 
        	 logger.info("Connecting "+MqttClient.class.getSimpleName());
        	 client.connect(options);
        	 logger.info("Connected "+MqttClient.class.getSimpleName());
         }
         catch (MqttException e)
        {
             e.printStackTrace();
             System.exit(1);
         }
    }
    
    public void send() throws MqttException, InterruptedException
    {
    	for(int i=20;i<30;i++)
    	{
    		publishTemperature(i);
    		Thread.sleep(1000);
    	}
    }
    
    private void publishTemperature(int i) throws MqttException
    {
        final MqttTopic temperatureTopic = client.getTopic(TOPIC_TEMPERATURE);
        final String temperature = i + "°C";
        logger.info("Sending to "+temperatureTopic.toString());
        temperatureTopic.publish(new MqttMessage(temperature.getBytes()));
        logger.info("Done to "+temperatureTopic.toString());
    }
    
    public static void main(String[] args) throws Exception
	{
		Configuration config = JsmBootstrap.init();
		
		CliMqttPublisher cli = new CliMqttPublisher();
		cli.send();
	}
}