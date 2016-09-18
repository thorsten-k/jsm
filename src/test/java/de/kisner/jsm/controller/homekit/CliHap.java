package de.kisner.jsm.controller.homekit;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

import de.kisner.jsm.JsmBootstrap;

public class CliHap
{	
	final static Logger logger = LoggerFactory.getLogger(CliHap.class);
	
	private static final int PORT = 9123;
	
	public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException
	{
		Configuration config = JsmBootstrap.init();
		
		HomekitServer homekit = new HomekitServer(PORT);
		HomekitRoot bridge = homekit.createBridge(new MockAuthInfo(), "Test Bridge", "TestBridge, Inc.", "G6", "111abe234");
		bridge.addAccessory(new MockSwitch(2,"Flur"));
		bridge.addAccessory(new MockSwitch(3,"Esszimmer"));
		bridge.addAccessory(new MockSwitch(4,"Seitenstreifen"));
		bridge.addAccessory(new MockSwitch(5,"Teichpumpe"));
		bridge.addAccessory(new MockSwitch(6,"Terasse"));
		bridge.addAccessory(new MockSwitch(7,"Teichbeleuchtung"));
		bridge.addAccessory(new MockSwitch(8,"Garten Hintergrund"));
		bridge.start();
		
	}
}