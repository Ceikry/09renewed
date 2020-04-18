package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;

/**
 * Packets relating to client operation.
 * @author Luke132
 */
public class ClientAction implements PacketHandler {

	private static final int IDLE = 245; // d
	private static final int MOVE_CAMERA = 21; // d
	private static final int PING = 93; // d
	private static final int FOCUS = 22; // d
	private static final int CLICK_MOUSE = 75; // d
	private static final int WINDOW_TYPE = 243; // d
	private static final int SOUND_SETTINGS = 98; // d
	private static final int IDLE_LOGOUT = 47; // d
	
	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		switch(packet.getId()) {
			case IDLE:
			case MOVE_CAMERA:
			case PING:
			case FOCUS:
			case CLICK_MOUSE:
			case SOUND_SETTINGS:
				break;
				
			case WINDOW_TYPE:
				handleScreenSettings(player, packet);
				break;
				
			case IDLE_LOGOUT:
				handleIdleLogout(player, packet);
				break;
		}
		
	}

	private void handleScreenSettings(Player player, Packet packet) {
		int windowType = packet.readByte() & 0xff;
		int windowWidth = packet.readShort();
		int windowHeight = packet.readShort();
		int junk = packet.readByte() & 0xff;
		player.getActionSender().configureGameScreen(windowType);
	}
	
	private void handleIdleLogout(Player player, Packet packet) {
		player.getActionSender().sendIdleLogout(player);
	}
}
