package com.runescape.packethandler;

import org.apache.mina.common.IoSession;

import com.runescape.model.player.Player;
import com.runescape.net.Packet;

	/**
	 * Handles removing of interfaces.
	 * @author Graham
	 *
	 */
	public class RemoveInterface implements PacketHandler {
		@Override
		public void handlePacket(Player player, IoSession session, Packet packet) {
			// TODO Auto-generated method stub
	        switch(packet.getId()) {
	        case 63:
	            if(player.getTemporaryAttribute("leveledUp") != null) {
	                player.getActionSender().sendConfig(1230,
	                        (Integer)player.getTemporaryAttribute("LEVEL_SKILL"));
	                player.removeTemporaryAttribute("LEVEL_SKILL");
	                player.removeTemporaryAttribute("leveledUp");
	            }
	            player.getActionSender().sendCloseChatboxInterface();
	            break;
	        case 108:
	            if(player.getTemporaryAttribute("skillMenu") != null) {
	                player.getActionSender().sendConfig(1179, 0);
	                player.removeTemporaryAttribute("skillMenu");
	                player.removeTemporaryAttribute("LEVEL_SKILL");
	            }
	            player.getActionSender().closeInterfaces();
	            break;
	        }
		}

}
