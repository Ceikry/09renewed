package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.Constants;
import com.runescape.event.AreaEvent;
import com.runescape.model.World;
import com.runescape.model.masks.FaceLocation;
import com.runescape.model.player.Player;
import com.runescape.model.player.TradeSession;
import com.runescape.net.Packet;
import com.runescape.world.Trade;

public class AcceptRequest implements PacketHandler {

	private static final int ACCEPT_TRADE = 71; // d
	
	@Override
	public void handlePacket(Player player, IoSession session, Packet packet) {
		switch(packet.getId()) {
			case ACCEPT_TRADE:
				handleAcceptTrade(player, packet);
				break;
		}
	}

	private void handleAcceptTrade(final Player player, Packet packet) {
		int index = packet.readLEShortA();
		if(index < 0 || index >= Constants.PLAYER_CAP || player.isDead() || player.getTemporaryAttribute("cantDoAnything") != null) {
			return;
		}
		
	}

}
