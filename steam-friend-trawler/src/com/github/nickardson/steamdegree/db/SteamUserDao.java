package com.github.nickardson.steamdegree.db;

import java.util.List;

public interface SteamUserDao {
	/**
	 * @param steamid
	 * @return The steam user with the given steamid, or null if none exists.
	 */
	public SteamUser getSteamUser(long steamid);
	
	/**
	 * @param steamid
	 * @return All users who are direct friends of the given user.
	 */
	public List<SteamUser> getFriendsOfUser(long steamid);
	
	/**
	 * Get users whose friend lists haven't been updated.
	 * @param n Max number to get
	 * @param days Minimum number of days old the data must be
	 * @return
	 */
	public List<SteamUser> getStaleFriendListUsers(int n, int days);
	
	/**
	 * Get users whose metadata hasn't been updated.
	 * @param n Max number to get
	 * @param days Minimum number of days old the data must be
	 * @return
	 */
	public List<SteamUser> getStaleMetadataUsers(int n, int days);
	
	public void createSteamUser(SteamUser steamUser);
	public void updateSteamUser(SteamUser steamUser);
	public void deleteSteamUser(SteamUser steamUser);
}
