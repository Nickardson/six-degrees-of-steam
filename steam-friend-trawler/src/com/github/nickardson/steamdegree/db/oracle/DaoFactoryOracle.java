package com.github.nickardson.steamdegree.db.oracle;

import com.github.nickardson.steamdegree.db.DaoFactory;
import com.github.nickardson.steamdegree.db.SteamFriendshipDao;
import com.github.nickardson.steamdegree.db.SteamUserDao;

public class DaoFactoryOracle implements DaoFactory {

	@Override
	public SteamUserDao getSteamUserDao() {
		return new SteamUserDaoOracle();
	}
	
	@Override
	public SteamFriendshipDao getSteamFriendshipDao() {
		return new SteamFriendshipDaoOracle();
	}

}
