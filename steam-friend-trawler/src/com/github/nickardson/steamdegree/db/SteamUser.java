package com.github.nickardson.steamdegree.db;

import java.util.Date;

/**
 * A steam user, and information used to determine their API trawled status.
 */
public class SteamUser {
	public enum Visibility {
		PUBLIC('Y'),
		PRIVATE('N'),
		UNKNOWN('U');
		
		private char character;
		private Visibility(char c) {
			character = c;
		}
		
		public char getCharacter() {
			return character;
		}
	}
	
	public SteamUser() {
		
	}
	
	public SteamUser(long steamid, Date lastcrawl, Visibility visibility,
			String name, String avatar, Date lastmetacrawl) {
		this.steamid = steamid;
		this.lastcrawl = lastcrawl;
		this.visibility = visibility;
		this.name = name;
		this.avatar = avatar;
		this.lastmetacrawl = lastmetacrawl;
	}

	private long steamid;
	private Date lastcrawl;
	private Visibility visibility = Visibility.UNKNOWN;
	private String name;
	private String avatar;
	private Date lastmetacrawl;
	
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
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getLastmetacrawl() {
		return lastmetacrawl;
	}

	public void setLastmetacrawl(Date lastmetacrawl) {
		this.lastmetacrawl = lastmetacrawl;
	}

	@Override
	public String toString() {
		return "SteamUser [steamid=" + steamid + ", lastcrawl=" + lastcrawl
				+ ", visibility=" + visibility + ", name=" + name + ", avatar="
				+ avatar + ", lastmetacrawl=" + lastmetacrawl + "]";
	}
}
