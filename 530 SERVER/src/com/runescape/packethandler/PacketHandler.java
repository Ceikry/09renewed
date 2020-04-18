package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;

/**
 * Packet handler interface.
 * @author Graham
 *
 */
public interface PacketHandler {
	
	public void handlePacket(Player player, IoSession session, Packet packet);

}
