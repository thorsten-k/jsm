package de.kisner.github.jsm.factory.txt.netatmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.github.jsm.model.json.JsonNetatmoToken;

public class TxtNetatmoTokenFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtNetatmoTokenFactory.class);
	
	public static String build(JsonNetatmoToken token)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("access: ").append(token.getToken());
		sb.append(" refresh: ").append(token.getTokenRefresh());
		return sb.toString();
	}
}