package de.kisner.jsm.controller.fritz;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.jsm.JsmBootstrap;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Service;
import net.sf.exlp.util.io.StringUtil;

public class CliFritz
{	
	final static Logger logger = LoggerFactory.getLogger(CliFritz.class);
	
	static String ip = "192.168.188.1";
	static String user = "admin";
	static String password = "admin";
	
	
	public static void main(String[] args) throws  IOException, JAXBException
	{	
		Configuration config = JsmBootstrap.init();
		
		FritzConnection fc = new FritzConnection(ip,user,password);
		fc.init();
//		fc.printInfo();
		
		Map<String,Service> services = fc.getServices();
		for(String key : services.keySet())
		{
			logger.info(key);
			Service service = services.get(key);
			for(String kA : service.getActions().keySet())
			{
				logger.info(StringUtil.tab(1)+kA);
			}
		}
		
	}
}