package com.github.nickardson.steamdegree.steam.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.github.nickardson.steamdegree.Settings;

public class SteamAPI {
	/**
	 * The number of daily requests allowed.
	 */
	private static final int MAX_REQUESTS = 100_000;
	// TODO: honor the max requests limit.
	
	/**
	 * The number of requests allowed in a second. ie 0.5 being one request every 2 seconds.
	 */
	private static final float REQUESTS_PER_SECOND = 200 / 5.0f / 60.0f; // 200 requests per 5 minutes.
	// TODO: honor the rate limit.
	
	private static final String API_KEY;
	private static final String HOST = "http://api.steampowered.com";
	static {
		API_KEY = Settings.getConfigSettings().getString("steamapikey");
	}
	
	public static List<SteamFriend> GetFriendList(long steamid) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet req = new HttpGet();
		try {
			URIBuilder uriBuilder = new URIBuilder(HOST)
					.setPath("/ISteamUser/GetFriendList/v0001/")
					.setParameter("key", API_KEY)
					.setParameter("format", "json")
					.setParameter("relationship", "friend")
					.setParameter("steamid", Long.toString(steamid));
			req.setURI(uriBuilder.build());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		try {
			CloseableHttpResponse response = client.execute(req);
			
			List<SteamFriend> friendList = new ArrayList<>();
			
			try {
				HttpEntity entity = response.getEntity();
				JSONObject json = new JSONObject(IOUtils.toString(entity.getContent(), "UTF-8"));
				JSONArray friends = json.getJSONObject("friendslist").getJSONArray("friends");
				friends.forEach(f -> friendList.add(SteamFriend.fromJSON((JSONObject) f)));
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
			
			return friendList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// TODO: GetPlayerSummaries
	// http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=XXXXXXXXXXX&steamids=12345,321312321,312321323
	// Up to 100
}
