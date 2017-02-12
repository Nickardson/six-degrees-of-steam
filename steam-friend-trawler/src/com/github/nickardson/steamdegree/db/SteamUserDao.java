package com.github.nickardson.steamdegree.db;

public interface SteamUserDao {
	public SteamUser getSteamUser(long steamid);
	public void createSteamUser(SteamUser steamUser);
	public void updateSteamUser(SteamUser steamUser);
	public void deleteSteamUser(SteamUser steamUser);
}
