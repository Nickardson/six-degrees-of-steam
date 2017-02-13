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

	public static final int NO_LOCCITYID = 0;
	
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
	
	public SteamUser(long steamid, Date lastcrawl, Visibility visibility,
			String name, String avatar, Date lastmetacrawl, String loccountrycode, String locstatecode, int loccityid) {
		this(steamid, lastcrawl, visibility, name, avatar, lastmetacrawl);
		setLoccountrycode(loccountrycode);
		setLocstatecode(locstatecode);
		setLoccityid(loccityid);
	}

	private long steamid;
	private Date lastcrawl;
	private Visibility visibility = Visibility.UNKNOWN;
	private String name;
	private String avatar;
	private Date lastmetacrawl;
	private String loccountrycode;
	private String locstatecode;
	private int loccityid = NO_LOCCITYID;
	
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
	
	public String getLoccountrycode() {
		return loccountrycode;
	}

	public void setLoccountrycode(String loccountrycode) {
		this.loccountrycode = loccountrycode;
	}

	public String getLocstatecode() {
		return locstatecode;
	}

	public void setLocstatecode(String locstatecode) {
		this.locstatecode = locstatecode;
	}

	public int getLoccityid() {
		return loccityid;
	}

	public void setLoccityid(int loccityid) {
		this.loccityid = loccityid;
	}

	@Override
	public String toString() {
		return "SteamUser [steamid=" + steamid + ", lastcrawl=" + lastcrawl
				+ ", visibility=" + visibility + ", name=" + name + ", avatar="
				+ avatar + ", lastmetacrawl=" + lastmetacrawl
				+ ", loccountrycode=" + loccountrycode + ", locstatecode="
				+ locstatecode + ", loccityid=" + loccityid + "]";
	}

	
}
