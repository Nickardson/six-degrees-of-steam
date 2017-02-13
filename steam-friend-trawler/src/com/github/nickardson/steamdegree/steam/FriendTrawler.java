package com.github.nickardson.steamdegree.steam;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.github.nickardson.steamdegree.ConsoleMain;
import com.github.nickardson.steamdegree.db.SteamFriendship;
import com.github.nickardson.steamdegree.db.SteamFriendshipDao;
import com.github.nickardson.steamdegree.db.SteamUser;
import com.github.nickardson.steamdegree.db.SteamUser.Visibility;
import com.github.nickardson.steamdegree.db.SteamUserDao;
import com.github.nickardson.steamdegree.db.oracle.ConnectionFactoryOracle;
import com.github.nickardson.steamdegree.steam.api.SteamAPI;
import com.github.nickardson.steamdegree.steam.api.SteamFriend;
import com.github.nickardson.steamdegree.steam.api.SteamPlayerSummary;
import com.github.nickardson.steamdegree.steam.api.SteamPlayerSummary.CommunityVisibilityState;

public class FriendTrawler {
	/**
	 * Discovers all the friends for the given user.
	 * @param steamid
	 */
	public void discoverFriends(long steamid) {
		System.out.println("Discover friends of " + steamid);
		SteamUserDao userDB = ConsoleMain.daoFactory.getSteamUserDao();
		SteamFriendshipDao friendDB = ConsoleMain.daoFactory.getSteamFriendshipDao();
		
		Date now = new Date();
		
		SteamUser thatUser = userDB.getSteamUser(steamid);
		
		// that user does not yet exist.
		if (thatUser == null) {
			thatUser = new SteamUser();
			thatUser.setSteamid(steamid);
			userDB.createSteamUser(thatUser);
		}
		
		System.out.println("That user is " + thatUser);
		
		List<SteamFriend> friends = SteamAPI.GetFriendList(steamid);
		
		for (SteamFriend friend : friends) {
			SteamUser friendUser = userDB.getSteamUser(friend.getSteamid());
			System.out.println("\tFriend: " + friendUser);
			
			if (friendUser == null) {
				System.out.println("\t\tMust create.");
				// friend does not yet exist, create them
				friendUser = new SteamUser();
				friendUser.setSteamid(friend.getSteamid());
				userDB.createSteamUser(friendUser);
			} else {
				// friend already exists.
				System.out.println("\t\tAlready exists.");
			}
			
			// check for, and possibly create relationship entity
			SteamFriendship relate = friendDB.getSteamFriendship(thatUser.getSteamid(), friendUser.getSteamid());
			if (relate == null) {
				relate = new SteamFriendship(thatUser.getSteamid(), friendUser.getSteamid());
				friendDB.createSteamFriendship(relate);				
			} else {
				// friendship already exists.
			}
		}
		
		System.out.println("Done with friends.");
		
		// All friends successfully registered, now update the last crawl time for our user.
		thatUser.setLastcrawl(now);
		userDB.updateSteamUser(thatUser);
		
		System.out.println("Done updating.");
	}
	
	public void updateUsers(long[] users) {
		SteamUserDao userDB = ConsoleMain.daoFactory.getSteamUserDao();
		
		List<SteamPlayerSummary> summaries = SteamAPI.GetPlayerSummaries(users);
		
		Date now = new Date();
		
		for (SteamPlayerSummary s : summaries) {
			SteamUser inUser = userDB.getSteamUser(s.getSteamid());
			SteamUser outUser;
			if (inUser == null) {
				outUser = new SteamUser();
				outUser.setSteamid(s.getSteamid());
			} else {
				outUser = inUser;
			}
			
			if (s.getCommunityvisibilitystate() == CommunityVisibilityState.VISIBLE && s.getProfilestate()) {
				outUser.setVisibility(Visibility.PUBLIC);
			} else {
				outUser.setVisibility(Visibility.PRIVATE);
			}
			
			outUser.setName(s.getPersonaname());
			outUser.setAvatar(s.getAvatarfull());
			outUser.setLoccountrycode(s.getLoccountrycode());
			outUser.setLocstatecode(s.getLocstatecode());
			outUser.setLoccityid(s.getLoccityid());
			
			outUser.setLastmetacrawl(now);
			
			System.out.println("in:  " + inUser);
			System.out.println("out: " + outUser);
			if (inUser == null) {
				System.out.println("creating new");
				userDB.createSteamUser(outUser);
			} else {
				System.out.println("updating old");
				userDB.updateSteamUser(outUser);
			}
		}
	}
}
