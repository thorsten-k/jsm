package de.kisner.github.jsm.model.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class JsonNetatmoToken implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("error")
	private String error;
	public String getError() {return error;}
	public void setError(String error) {this.error = error;}

	@JsonProperty("access_token")
	private String token;
	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
	
	@JsonProperty("expires_in")
	private int expires;
	public int getExpires() {return expires;}
	public void setExpires(int expires) {this.expires = expires;}
	
	@JsonProperty("expire_in")
	private int expires2;
	public int getExpires2() {return expires2;}
	public void setExpires2(int expires2) {this.expires2 = expires2;}
	
	@JsonProperty("refresh_token")
	private String tokenRefresh;
	public String getTokenRefresh() {return tokenRefresh;}
	public void setTokenRefresh(String tokenRefresh) {this.tokenRefresh = tokenRefresh;}
	
	@JsonProperty("scope")
	private List<String> scopes;
	public List<String> getScopes() {return scopes;}
	public void setScopes(List<String> scopes) {this.scopes = scopes;}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}