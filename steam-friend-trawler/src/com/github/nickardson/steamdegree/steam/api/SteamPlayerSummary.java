package com.github.nickardson.steamdegree.steam.api;

import org.json.JSONObject;

public class SteamPlayerSummary {
	public enum CommunityVisibilityState {
		NOT_VISIBLE,
		VISIBLE
	}
	
	public SteamPlayerSummary() {
		
	}
	
	private long steamid;
	private String personaname;
	private String avatarfull;
	private CommunityVisibilityState communityvisibilitystate;
	private boolean profilestate;
	private String loccountrycode, locstatecode;
	private int loccityid;
	
	public static SteamPlayerSummary fromJSON(JSONObject f) {
		SteamPlayerSummary summary = new SteamPlayerSummary();
		
		summary.setSteamid(f.getLong("steamid"));
		summary.setPersonaname(f.getString("personaname"));
		summary.setAvatarfull(f.getString("avatarfull"));
		
		CommunityVisibilityState cvState;
		switch (f.getInt("communityvisibilitystate")) {
		case 3:
			cvState = CommunityVisibilityState.VISIBLE;
			break;
		default:
			cvState = CommunityVisibilityState.NOT_VISIBLE;
		}
		summary.setCommunityvisibilitystate(cvState);
		
		summary.setProfilestate(f.has("profilestate"));
		if (f.has("loccountrycode")) {
			summary.setLoccountrycode(f.getString("loccountrycode"));
		}
		if (f.has("locstatecode")) {
			summary.setLocstatecode(f.getString("locstatecode"));
		}
		if (f.has("loccityid")) {
			summary.setLoccityid(f.getInt("loccityid"));
		}
		
		return summary;
	}
	
	public long getSteamid() {
		return steamid;
	}
	public void setSteamid(long steamid) {
		this.steamid = steamid;
	}
	public String getPersonaname() {
		return personaname;
	}
	public void setPersonaname(String personaname) {
		this.personaname = personaname;
	}
	public String getAvatarfull() {
		return avatarfull;
	}
	public void setAvatarfull(String avatarfull) {
		this.avatarfull = avatarfull;
	}
	public CommunityVisibilityState getCommunityvisibilitystate() {
		return communityvisibilitystate;
	}
	public void setCommunityvisibilitystate(CommunityVisibilityState communityvisibilitystate) {
		this.communityvisibilitystate = communityvisibilitystate;
	}
	public boolean getProfilestate() {
		return profilestate;
	}
	public void setProfilestate(boolean profilestate) {
		this.profilestate = profilestate;
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
		return "SteamPlayerSummary [steamid="
				+ steamid
				+ ", "
				+ (personaname != null ? "personaname=" + personaname + ", "
						: "")
				+ (avatarfull != null ? "avatarfull=" + avatarfull + ", " : "")
				+ "communityvisibilitystate="
				+ communityvisibilitystate
				+ ", profilestate="
				+ profilestate
				+ ", "
				+ (loccountrycode != null ? "loccountrycode=" + loccountrycode
						+ ", " : "")
				+ (locstatecode != null ? "locstatecode=" + locstatecode + ", "
						: "") + "loccityid=" + loccityid + "]";
	}
	
	
}
