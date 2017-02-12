package com.github.nickardson.steamdegree.db.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.github.nickardson.steamdegree.db.SteamUser;
import com.github.nickardson.steamdegree.db.SteamUser.Visibility;
import com.github.nickardson.steamdegree.db.SteamUserDao;

public class SteamUserDaoOracle implements SteamUserDao {
	private static final String TABLE_NAME = "steam_user";
	
	@Override
	public SteamUser getSteamUser(long steamid) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection().prepareStatement(
							"SELECT * FROM " + TABLE_NAME
									+ " WHERE steamid = ?");
			statement.setLong(1, steamid);
			
			ResultSet results = statement.executeQuery();
			
			List<SteamUser> users = itemsFromResultSet(results);
			if (!users.isEmpty()) {
				return users.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void createSteamUser(SteamUser steamUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSteamUser(SteamUser steamUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSteamUser(SteamUser steamUser) {
		// TODO Auto-generated method stub

	}

	private List<SteamUser> itemsFromResultSet(ResultSet results) throws SQLException {
		List<SteamUser> items = new ArrayList<>();
        
        while (results.next()) {
            items.add(new SteamUser(
            		results.getLong("SteamID"), 
                    results.getDate("LastCrawl", GregorianCalendar.getInstance()), 
                    visibilityFromChar(results.getString("Visibility").charAt(0)), 
                    results.getString("Name")));
        }
        
        return items;
	}
	
	private SteamUser.Visibility visibilityFromChar(char c) {
		switch (c) {
		case 'Y':
			return Visibility.PUBLIC;
		case 'N':
			return Visibility.PRIVATE;
		default:
			return Visibility.UNKNOWN;
		}
	}
}
