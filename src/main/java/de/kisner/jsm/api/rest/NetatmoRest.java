package de.kisner.jsm.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.kisner.jsm.model.json.JsonNetatmoToken;

@Path("")
public interface NetatmoRest
{	
	public static String url = "netatmo.api.url";
	public static String appId = "netatmo.app.id";
	public static String appSecret = "netatmo.app.secret";
	public static String userName = "netatmo.user.name";
	public static String userPwd = "netatmo.user.pwd";
	public static String token = "netatmo.token";
	public static String device = "netatmo.device";
	
	@POST @Path("/oauth2/token") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	JsonNetatmoToken token(
			@FormParam("grant_type") String type,
			@FormParam("client_id") String id,
			@FormParam("client_secret") String secret,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("scope") String scope);
}