package com.runescape.world;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import com.runescape.event.Event;
import com.runescape.model.Item;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.util.XStreamUtil;
import com.runescape.util.log.Logger;

public class ShopManager {
	
	private Map<Integer, Shop> shops;
	
	@SuppressWarnings("unchecked")
	public ShopManager() throws FileNotFoundException {
		Logger.getInstance().debug("Loading shops...");
		shops = (Map<Integer, Shop>) XStreamUtil.getXStream().fromXML(new FileInputStream("data/shops.xml"));
		World.getInstance().registerEvent(new Event(60000) {
			@Override
			public void execute() {
				updateShopAmounts();
			}
		});
		Logger.getInstance().debug("Loaded " + shops.size() + " shops.");
	}

	private void updateShopAmounts() {
		for(Map.Entry<Integer, Shop> s : shops.entrySet()) {
			s.getValue().updateAmounts();
			for (Player p: World.getInstance().getPlayerList()) {
				if (p == null || p.getShopSession() == null) {
					continue;
				}
				if (p.getShopSession().getShopId() == s.getKey()) {
					p.getActionSender().sendItems(-1, 64271, 31, s.getValue().getStock());
				}
			}
		}
	}
	
	public Shop getShop(int id) {
		return shops.get(id);
		
	}

}
