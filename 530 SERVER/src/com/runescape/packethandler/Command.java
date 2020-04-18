package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;
import com.runescape.packethandler.commands.CommandManager;

/**
 * 
 * @author Luke132
 */
public class Command implements PacketHandler {

	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		String command = packet.readRS2String().toLowerCase();
		CommandManager.execute(player, command);
	}

}
