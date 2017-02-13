package com.github.nickardson.steamdegree.steam.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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
	
	/**
	 * The number of requests allowed in a second. ie 0.5 being one request every 2 seconds.
	 */
	private static final float REQUESTS_PER_SECOND = 200 / 5.0f / 60.0f; // 200 requests per 5 minutes.
	
	private static final String API_KEY;
	private static final String HOST = "http://api.steampowered.com";
	static {
		API_KEY = Settings.getConfigSettings().getString("steamapikey");
	}
	
	private static long lastCall;
	private static RateInfo rateInfo = new RateInfo();
	
	static {
		try {
			restoreRateInfo();
			System.out.println("Rate Info: " + rateInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ensures rate limiting and volume limiting.
	 */
	private static void preAPI() {
		final long msBetween = (long) (1000 / REQUESTS_PER_SECOND);
		long nowBefore = System.currentTimeMillis();
		if (nowBefore - lastCall < msBetween) {
			long timeToWait = (long) (lastCall - nowBefore + msBetween);
			try {
				Thread.sleep(timeToWait);
			} catch (InterruptedException ignored) {}
		}
		
		lastCall = System.currentTimeMillis();
		
		if (rateInfo.getCalls() >= MAX_REQUESTS) {
			throw new IllegalStateException("API call limit exceeded.");
		} else {
			rateInfo.countup();
			try {
				saveRateInfo();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void saveRateInfo() throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rateinfo.dat"))) {
			out.writeObject(rateInfo);
		}
	}
	
	private static void restoreRateInfo() throws IOException {
		File inFile = new File("rateinfo.dat");
		if (inFile.exists() && inFile.isFile()) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(inFile))) {
				try {
					rateInfo = (RateInfo) input.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			rateInfo = new RateInfo(new Date(), 0);
		}
	}
	
	/**
	 * An empty "API Call" which still triggers the rate limiting and request count limiting.
	 */
	public static void FakeApiCall() {
		System.out.println("Attempt API call....");
		preAPI();
		System.out.println("\tAPI called.");
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
			preAPI();
			
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
	public static List<SteamPlayerSummary> GetPlayerSummaries(long[] ids) {
		if (ids.length > 100) {
			throw new IllegalArgumentException("Maximum of 100 ids are allowed, got " + ids.length);
		} else if (ids.length == 0) {
			return new ArrayList<SteamPlayerSummary>(0);
		}
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet req = new HttpGet();
		try {
			// build a comma separated list of ids.
			StringBuilder idList = new StringBuilder(ids.length * 17); // most.. all? ids are 17 characters in length.
			for (int i = 0; i < ids.length; i++) {
				idList.append(ids[i]);
				if (i < ids.length - 1) {
					idList.append(',');
				}
			}
			
			URIBuilder uriBuilder = new URIBuilder(HOST)
					.setPath("/ISteamUser/GetPlayerSummaries/v0002/")
					.setParameter("key", API_KEY)
					.setParameter("format", "json")
					.setParameter("steamids", idList.toString());
			req.setURI(uriBuilder.build());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		try {
			preAPI();
			
			CloseableHttpResponse response = client.execute(req);
			
			List<SteamPlayerSummary> friendList = new ArrayList<>();
			
			try {
				HttpEntity entity = response.getEntity();
				JSONObject json = new JSONObject(IOUtils.toString(entity.getContent(), "UTF-8"));
				JSONArray players = json.getJSONObject("response").getJSONArray("players");
				players.forEach(f -> friendList.add(SteamPlayerSummary.fromJSON((JSONObject) f)));
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
}
