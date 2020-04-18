package com.runescape.util;

import com.thoughtworks.xstream.XStream;

/**
 * Util class to get the xstream object.
 * @author Graham
 *
 */
public class XStreamUtil {
	
	private XStreamUtil() {}
	
	private static XStream xstream = null;
	
	public static XStream getXStream() {
		if(xstream == null) {
			xstream = new XStream();
			/*
			 * Set up our aliases.
			 */
			xstream.alias("packet", com.runescape.packethandler.PacketHandlerDef.class);
			xstream.alias("player", com.runescape.model.player.Player.class);
			xstream.alias("itemDefinition", com.runescape.model.ItemDefinition.class);
			xstream.alias("item", com.runescape.model.Item.class);
			xstream.alias("npcDefinition", com.runescape.model.npc.NPCDefinition.class);
			xstream.alias("npc", com.runescape.model.npc.NPC.class);
			xstream.alias("shop", com.runescape.world.Shop.class);
			xstream.alias("npcDrop", com.runescape.model.npc.NPCDrop.class);
		}
		return xstream;
	}

}
