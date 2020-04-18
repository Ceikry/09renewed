package com.runescape.content.events;

import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.world.GroundItem;

public class GroundItems {

	public static void loadItems() {
		if (World.getInstance().getGroundItems().itemExists(Location.location(3205, 3212, 0), 946) == null) {
			GroundItem item = new GroundItem(946, 1, Location.location(3205, 3212, 0), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3208, 3214, 0), 1923) == null) {
			GroundItem item = new GroundItem(1923, 1, Location.location(3208, 3214, 0), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3209, 3214, 0), 1931) == null) {
			GroundItem item = new GroundItem(1931, 1, Location.location(3209, 3214, 0), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3211, 3212, 0), 1935) == null) {
			GroundItem item = new GroundItem(1935, 1, Location.location(3211, 3212, 0), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3206, 3208, 0), 558) == null) {
			GroundItem item = new GroundItem(558, 1, Location.location(3206, 3208, 0), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3213, 3216, 1), 1205) == null) {
			GroundItem item6 = new GroundItem(1205, 1, Location.location(3213, 3216, 1), null);
			World.getInstance().getGroundItems().newWorldItem(item6);
			item6.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3208, 3225, 2), 1511) == null) {
			GroundItem item = new GroundItem(1511, 1, Location.location(3208, 3225, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3209, 3224, 2), 1511) == null) {
			GroundItem item = new GroundItem(1511, 1, Location.location(3209, 3224, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3205, 3224, 2), 1511) == null) {
			GroundItem item = new GroundItem(1511, 1, Location.location(3205, 3224, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3205, 3226, 2), 1511) == null) {
			GroundItem item = new GroundItem(1511, 1, Location.location(3205, 3226, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3229, 3223, 2), 1265) == null) {
			GroundItem item = new GroundItem(1265, 1, Location.location(3229, 3223, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
		if (World.getInstance().getGroundItems().itemExists(Location.location(3229, 3215, 2), 1265) == null) {
			GroundItem item = new GroundItem(1265, 1, Location.location(3229, 3215, 2), null);
			World.getInstance().getGroundItems().newWorldItem(item);
			item.setRespawn(true);
		}
	if (World.getInstance().getGroundItems().itemExists(Location.location(3132, 9862, 0), 983) == null) {
		GroundItem item = new GroundItem(983, 1, Location.location(3132, 9862, 0), null);
		World.getInstance().getGroundItems().newWorldItem(item);
		item.setRespawn(true);
	}
		
		
	}
	
}
