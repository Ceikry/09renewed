package com.runescape.packethandler.commands;

import com.runescape.model.player.Player;

/**
 * 
 * @author Luke132
 */
public interface Command {

	/**
	 * The body of the command.
	 */
	public void execute(Player player, String command);
	
	/**
	 * The minimum rights required to perform the command.
	 */
	public int minimumRightsNeeded();
	
	public static final int NONE = 0, PLAYER_MOD = 1, ADMIN = 2;
}
