package com.runescape.model;

import com.runescape.Constants;
import com.runescape.model.npc.NPC;
import com.runescape.model.player.Player;

/**
 * Handles local entities.
 * @author Graham
 *
 */
public class LocalEntityList {

	public int playerListSize;
	public Player[] playerList;
	public byte[] playersInList;
	
	public int npcListSize;
	public NPC[] npcList;
	public byte[] npcsInList;
	
	public boolean rebuildNpcList;
	
	public LocalEntityList() {
		playerList = new Player[Constants.PLAYER_CAP+1];
		playersInList = new byte[Constants.PLAYER_CAP+1];
		playerListSize = 0;
		
		npcList = new NPC[Constants.NPC_CAP+1];
		npcsInList = new byte[Constants.NPC_CAP+1];
		npcListSize = 0;
		
		rebuildNpcList = false;
	}

}
