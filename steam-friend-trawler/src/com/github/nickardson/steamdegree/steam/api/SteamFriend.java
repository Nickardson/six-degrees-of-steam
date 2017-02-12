package com.github.nickardson.steamdegree.steam.api;

import java.util.Date;

import org.json.JSONObject;

/**
 * A friend data, as returned by ISteamUser/GetFriendList
 */
public class SteamFriend {
	public enum Relationship {
		FRIEND,
		ALL
	}
	
	private long steamid;
	private SteamFriend.Relationship relationship;
	private Date friend_since;
	
	public SteamFriend() {
		
	}

	public SteamFriend(long steamid, SteamFriend.Relationship relationship, Date friend_since) {
		this.steamid = steamid;
		this.relationship = relationship;
		this.friend_since = friend_since;
	}

	public static SteamFriend fromJSON(JSONObject f) {
		return new SteamFriend(
				Long.parseLong(f.getString("steamid")),
				Relationship.valueOf(f.getString("relationship").toUpperCase()),
				new Date(f.getInt("friend_since")));
	}
	
	public long getSteamid() {
		return steamid;
	}

	public void setSteamid(long steamid) {
		this.steamid = steamid;
	}

	public SteamFriend.Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(SteamFriend.Relationship relationship) {
		this.relationship = relationship;
	}

	public Date getFriendSince() {
		return friend_since;
	}

	public void setFriendSince(Date friend_since) {
		this.friend_since = friend_since;
	}

	@Override
	public String toString() {
		return "SteamFriend [steamid=" + steamid + ", relationship="
				+ relationship + ", friend_since=" + friend_since + "]";
	}
}
