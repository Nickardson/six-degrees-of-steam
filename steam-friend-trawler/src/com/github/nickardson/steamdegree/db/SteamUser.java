package com.github.nickardson.steamdegree.db;

import java.util.Date;

/**
 * A steam user, and information used to determine their API trawled status.
 */
public class SteamUser {
	public enum Visibility {
		PUBLIC,
		PRIVATE,
		UNKNOWN
	}
	
	public SteamUser() {
		
	}
	
	public SteamUser(long steamid, Date lastcrawl, Visibility visibility, String name) {
		this.steamid = steamid;
		this.lastcrawl = lastcrawl;
		this.visibility = visibility;
		this.name = name;
	}

	private long steamid;
	private Date lastcrawl;
	private Visibility visibility;
	private String name;
	public long getSteamid() {
		return steamid;
	}

	public void setSteamid(long steamid) {
		this.steamid = steamid;
	}

	public Date getLastcrawl() {
		return lastcrawl;
	}

	public void setLastcrawl(Date lastcrawl) {
		this.lastcrawl = lastcrawl;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SteamUser [steamid=" + steamid + ", lastcrawl=" + lastcrawl
				+ ", visibility=" + visibility + ", name=" + name + "]";
	}
}
