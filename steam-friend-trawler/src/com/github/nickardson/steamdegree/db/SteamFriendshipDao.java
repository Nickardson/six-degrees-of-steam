package com.github.nickardson.steamdegree.db;

public interface SteamFriendshipDao {
	public SteamFriendship getSteamFriendship(long frienda, long friendb);
	public void createSteamFriendship(SteamFriendship friendship);
	public void deleteSteamFriendship(SteamFriendship friendship);
}
