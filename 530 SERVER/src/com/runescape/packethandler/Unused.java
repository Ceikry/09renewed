package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;

/**
 * 
 * Unused packets.
 * @author Graham
 */
public class Unused implements PacketHandler {

	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		
	}

}
