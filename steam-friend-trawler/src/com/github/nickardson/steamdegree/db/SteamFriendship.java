package com.github.nickardson.steamdegree.db;

public class SteamFriendship {
	private long frienda;
	private long friendb;
	
	public SteamFriendship() {
		
	}

	/**
	 * The two friends may be swapped in order, if frienda is larger than friendb.
	 * @param frienda
	 * @param friendb
	 */
	public SteamFriendship(long frienda, long friendb) {
		this.setFriends(frienda, friendb);
	}

	public long getFriendA() {
		return frienda;
	}

	public long getFriendB() {
		return friendb;
	}

	/**
	 * The two friends may be swapped in order, if frienda is larger than friendb. To avoid con
	 * @param frienda
	 * @param friendb
	 */
	public void setFriends(long frienda, long friendb) {
		this.frienda = frienda;
		this.friendb = friendb;
		fixFriendOrder();
	}
	
	/**
	 * Ensures that the friends are ordered from A->B in increasing value
	 */
	private void fixFriendOrder() {
		if (frienda > friendb) {
			long oldA = frienda;
			frienda = friendb;
			friendb = oldA;
		}
	}

	@Override
	public String toString() {
		return "SteamFriendship [frienda=" + frienda + ", friendb=" + friendb
				+ "]";
	}
}
