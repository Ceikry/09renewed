package com.runescape.io;

import com.runescape.model.player.Player;
import com.runescape.model.player.PlayerDetails;

/**
 * Player load/save interface.
 * @author Graham
 *
 */
public interface PlayerLoader {
	
	public PlayerLoadResult load(PlayerDetails p);
	public boolean save(Player p);

}
