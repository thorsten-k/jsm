package de.kisner.github.jsm.controller.provider.netatmo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.github.jsm.JsmBootstrap;
import de.kisner.github.jsm.api.rest.NetatmoRest;
import de.kisner.github.jsm.factory.txt.netatmo.TxtNetatmoTokenFactory;
import de.kisner.github.jsm.model.json.JsonNetatmoToken;
import net.sf.exlp.util.io.StringUtil;

public class CliNetatmoRest
{	
	final static Logger logger = LoggerFactory.getLogger(CliNetatmoRest.class);
	
	private NetatmoRest rest;
	
	String appId,appSecret;
	String username,password;
	String deviceId,token;

	public CliNetatmoRest(Configuration config) throws FileNotFoundException
	{
		String restUrl = config.getString(NetatmoRest.url);
		
		appId = config.getString(NetatmoRest.appId);
		appSecret = config.getString(NetatmoRest.appSecret);
		username = config.getString(NetatmoRest.userName);
		password = config.getString(NetatmoRest.userPwd);
		deviceId = config.getString(NetatmoRest.device);
		token = config.getString(NetatmoRest.token);
		
		logger.info("Connecting to "+restUrl);		
				
		ResteasyClient client = new ResteasyClientBuilder().disableTrustManager().build();
		ResteasyWebTarget restTarget = client.target(restUrl);
		rest = restTarget.proxy(NetatmoRest.class);

	}
	
	public void token()
	{		
		JsonNetatmoToken x = rest.token("password", appId, appSecret, username, password, "read_station");
		logger.info(TxtNetatmoTokenFactory.build(x));
	}
	
	private Header[] buildHeader()
	{
		Header[] headers = {
        		new BasicHeader("Host", "api.netatmo.com")
			    ,new BasicHeader("Content-Type", "application/x-www-form-urlencoded")};
		return headers;
	}
	
	public void plainToken()
	{
        try
        {
        	CloseableHttpClient httpclient = HttpClients.custom().build();
            HttpHost target = new HttpHost("api.netatmo.com", 443, "https");
            HttpPost post = new HttpPost("/oauth2/token");
            post.setHeaders(buildHeader());
            
        	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        	urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        	urlParameters.add(new BasicNameValuePair("client_id", appId));
        	urlParameters.add(new BasicNameValuePair("client_secret", appSecret));
        	urlParameters.add(new BasicNameValuePair("username", username));
        	urlParameters.add(new BasicNameValuePair("password", password));
        	urlParameters.add(new BasicNameValuePair("scope", "read_station"));
        	post.setEntity(new UrlEncodedFormEntity(urlParameters));

        	logger.info(StringUtil.stars());
        	System.out.println(post.getRequestLine());
        	for(Header h : post.getAllHeaders())
        	{
        		System.out.println("\t"+h.getName()+": "+h.getValue());
        	}
        	System.out.println(EntityUtils.toString(post.getEntity()));

            
            logger.debug("Executing request " + post.getRequestLine() + " to " + target );
            CloseableHttpResponse response = httpclient.execute(target, post);
            logger.debug(StringUtil.stars());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (IOException  e) {e.printStackTrace();}
	}
	
	public void plainData()
	{
        try
        {
        	CloseableHttpClient httpclient = HttpClients.custom().build();
            HttpHost target = new HttpHost("api.netatmo.com", 443, "https");
            HttpPost post = new HttpPost("/api/getstationsdata");
            post.setHeaders(buildHeader());
            
        	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        	urlParameters.add(new BasicNameValuePair("access_token", token));
        	urlParameters.add(new BasicNameValuePair("device_id", deviceId));
        	post.setEntity(new UrlEncodedFormEntity(urlParameters));        	 
        	
        	logger.info(StringUtil.stars());
        	System.out.println(post.getRequestLine());
        	for(Header h : post.getAllHeaders())
        	{
        		System.out.println("\t"+h.getName()+": "+h.getValue());
        	}
        	System.out.println(EntityUtils.toString(post.getEntity()));

            
            logger.debug("Executing request " + post.getRequestLine() + " to " + target );
            CloseableHttpResponse response = httpclient.execute(target, post);
            logger.debug(StringUtil.stars());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (IOException  e) {e.printStackTrace();}
	}

	public static void main(String[] args) throws Exception
	{
		Configuration config = JsmBootstrap.init();
		
		CliNetatmoRest cli = new CliNetatmoRest(config);
//		cli.plain();
//		cli.token();
		
		cli.plainData();
	}
}