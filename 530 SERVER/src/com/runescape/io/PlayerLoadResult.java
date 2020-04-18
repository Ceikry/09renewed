package com.runescape.io;

import com.runescape.Constants;
import com.runescape.model.player.Player;

/**
 * Player load result.
 * @author Graham
 *
 */
public class PlayerLoadResult {
	
	public int returnCode = Constants.ReturnCodes.LOGIN_OK;
	public Player player = null;

}
