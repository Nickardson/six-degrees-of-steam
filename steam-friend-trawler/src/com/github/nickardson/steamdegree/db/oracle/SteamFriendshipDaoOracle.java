package com.github.nickardson.steamdegree.db.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.nickardson.steamdegree.db.SteamFriendship;
import com.github.nickardson.steamdegree.db.SteamFriendshipDao;

public class SteamFriendshipDaoOracle implements SteamFriendshipDao {
	private static final String TABLE_NAME = "steam_friendship";
	
	@Override
	public SteamFriendship getSteamFriendship(long frienda, long friendb) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection().prepareStatement(
							"SELECT * FROM " + TABLE_NAME
									+ " WHERE frienda = ? AND friendb = ?");
			statement.setLong(1, Math.min(frienda, friendb));
			statement.setLong(2, Math.max(frienda, friendb));
			
			ResultSet results = statement.executeQuery();
			
			List<SteamFriendship> friendships = itemsFromResultSet(results);
			if (!friendships.isEmpty()) {
				return friendships.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void createSteamFriendship(SteamFriendship friendship) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection().prepareStatement(
							"INSERT INTO " + TABLE_NAME
									+ "(frienda, friendb) VALUES (?, ?)");
			statement.setLong(1, friendship.getFriendA());
			statement.setLong(2, friendship.getFriendB());
			
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSteamFriendship(SteamFriendship friendship) {
		try {
			PreparedStatement statement = ConnectionFactoryOracle
					.getConnection().prepareStatement(
							"DELETE FROM " + TABLE_NAME + "WHERE frienda = ? AND friendb = ?");
			statement.setLong(1, friendship.getFriendA());
			statement.setLong(2, friendship.getFriendB());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<SteamFriendship> itemsFromResultSet(ResultSet results) throws SQLException {
		List<SteamFriendship> items = new ArrayList<>();
        
        while (results.next()) {
            items.add(new SteamFriendship(
            		results.getLong("frienda"), 
            		results.getLong("friendb")
                    ));
        }
        
        return items;
	}
}
