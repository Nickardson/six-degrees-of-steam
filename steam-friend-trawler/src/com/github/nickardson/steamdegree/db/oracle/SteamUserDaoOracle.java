package com.github.nickardson.steamdegree.db.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection()
					.prepareStatement(
							"INSERT INTO "
									+ TABLE_NAME
									+ " (steamid, lastcrawl, visibility, name, avatar, lastmetacrawl, loccountrycode, locstatecode, loccityid) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setLong(1, steamUser.getSteamid());
			if (steamUser.getLastcrawl() != null) {
				statement.setDate(2, new java.sql.Date(steamUser.getLastcrawl()
						.getTime()));
			} else {
				statement.setNull(2, Types.DATE);
			}
			statement.setString(3,
					charStringFromVisibility(steamUser.getVisibility()));
			statement.setString(4, steamUser.getName());
			statement.setString(5, steamUser.getAvatar());
			if (steamUser.getLastmetacrawl() != null) {
				statement.setDate(6, new java.sql.Date(steamUser
						.getLastmetacrawl().getTime()));
			} else {
				statement.setNull(6, Types.DATE);
			}
			statement.setString(7, steamUser.getLoccountrycode());
			statement.setString(8, steamUser.getLocstatecode());
			if (steamUser.getLoccityid() != SteamUser.NO_LOCCITYID) {
				statement.setInt(9, steamUser.getLoccityid());
			} else {
				statement.setNull(9, Types.INTEGER);
			}
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateSteamUser(SteamUser steamUser) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection()
					.prepareStatement(
							"UPDATE "
									+ TABLE_NAME
									+ " "
									+ "SET lastcrawl = ?, visibility = ?, name = ?, avatar = ?, lastmetacrawl = ?, loccountrycode = ?, locstatecode = ?, loccityid = ? "
									+ "WHERE steamid = ?");
			if (steamUser.getLastcrawl() != null) {
				statement.setDate(1, new java.sql.Date(steamUser.getLastcrawl()
						.getTime()));
			} else {
				statement.setNull(1, Types.DATE);
			}
			statement.setString(2,
					charStringFromVisibility(steamUser.getVisibility()));
			statement.setString(3, steamUser.getName());
			statement.setString(4, steamUser.getAvatar());
			if (steamUser.getLastmetacrawl() != null) {
				statement.setDate(5, new java.sql.Date(steamUser
						.getLastmetacrawl().getTime()));
			} else {
				statement.setNull(5, Types.DATE);
			}
			statement.setLong(6, steamUser.getSteamid());
			statement.setString(7, steamUser.getLoccountrycode());
			statement.setString(8, steamUser.getLocstatecode());
			if (steamUser.getLoccityid() != SteamUser.NO_LOCCITYID) {
				statement.setInt(9, steamUser.getLoccityid());
			} else {
				statement.setNull(9, Types.INTEGER);
			}
			
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSteamUser(SteamUser steamUser) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection().prepareStatement(
							"DELETE FROM " + TABLE_NAME + "WHERE steamid = ?");
			statement.setLong(1, steamUser.getSteamid());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<SteamUser> itemsFromResultSet(ResultSet results)
			throws SQLException {
		List<SteamUser> items = new ArrayList<>();

		while (results.next()) {
			items.add(new SteamUser(
					results.getLong("SteamID"), 
					results.getDate("LastCrawl", GregorianCalendar.getInstance()),
					visibilityFromChar(results.getString("Visibility").charAt(0)),
					results.getString("Name"),
					results.getString("Avatar"),
					results.getDate("LastMetaCrawl", GregorianCalendar.getInstance()),
					results.getString("loccountrycode"),
					results.getString("locstatecode"),
					results.getInt("loccityid")));
		}
		
		results.close();

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

	private String charStringFromVisibility(Visibility visibility) {
		return Character.toString(visibility.getCharacter());
	}
}
