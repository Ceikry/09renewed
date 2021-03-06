package com.runescape.world;

import com.runescape.model.player.Player;
import com.runescape.util.log.Logger;

public class ClanUser {

	private Clan clan;
	private Player p;
	private int rights;
	
	public ClanUser(Player p, Clan clan) {
		this.p = p;
		this.clan = clan;
		this.rights = Clan.NO_RANK;
		
	}

	public Player getClanMember() {
		return p;
	}

	public void setClanRights(int rights) {
		if (rights == Clan.NO_RANK) {
			if (clan.getOwnerFriends().contains(p.getPlayerDetails().getUsernameAsLong())) {
				rights = Clan.FRIEND;
			}
		} else {
			clan.getUsersWithRank().put(p.getUsername(), rights);
			if (clan.getUsersWithRank().size() >= 250) {
				Logger.getInstance().info("Clan 'usersWithRank' map size needs increasing!");
			}
		}
		this.rights = rights;
	}

	public int getClanRights() {
		return rights;
	}
	
	public Clan getClan() {
		return clan;
	}
}
