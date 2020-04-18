package com.runescape.content;

import com.runescape.event.Event;
import com.runescape.model.ItemDefinition;
import com.runescape.model.World;
import com.runescape.model.player.Player;

public class Consumables {

	public Consumables() {
		
	}
	
    public static final int[][] FOOD = {
        {
            319, 2142, 315, 2140, 2309, 325, 347, 2325, 333, 351, 
            2327, 329, 2003, 361, 2323, 2289, 379, 1891, 373, 2293, 
            1897, 2297, 3151, 3146, 3228, 7072, 355, 7062, 3363, 7078, 
            3365, 339, 3367, 7064, 3379, 7088, 7170, 2878, 3144, 7530, 
            7178, 5003, 7568, 7054, 7084, 365, 7082, 7188, 1985, 2343, 
            2149, 7066, 1885, 2011, 7058, 7056, 2301, 7068, 7060, 7198, 
            385, 397, 7208, 391, 7218, 1942, 1957, 1965, 1982, 5504, 1963,
            2108, 2114, 5972
        }, {
            -1, -1, -1, -1, -1, -1, -1, 2333, -1, -1, 
            2331, -1, 1923, -1, 2335, 2291, -1, 1893, -1, 2295, 
            1899, 2299, -1, -1, -1, 1923, -1, 1923, -1, 1923, 
            -1, -1, -1, 1923, -1, -1, 2313, -1, -1, -1, 
            7180, -1, -1, -1, 1923, -1, 1923, 7190, -1, -1, 
            -1, 1923, -1, 1923, -1, -1, 2303, 1923, -1, 7200, 
            -1, -1, 7210, -1, 7220, -1, -1, -1, -1, -1, -1,
            -1, -1, -1
        }, {
            -1, -1, -1, -1, -1, -1, -1, 2313, -1, -1, 
            2313, -1, -1, -1, 2313, -1, -1, 1895, -1, -1, 
            1901, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
            2313, -1, -1, -1, -1, -1, -1, 2313, -1, -1, 
            -1, -1, -1, -1, -1, -1, -1, -1, -1, 2313, 
            -1, -1, 2313, -1, 2313, -1, -1, -1, -1, -1, -1,
            -1, -1, -1
        }
    };
	
    public static final int[] FOOD_HEAL = {
        1, 3, 3, 3, 5, 4, 5, 5, 7, 8, 
        12, 9, 11, 10, 7, 7, 12, 4, 14, 8, 
        5, 9, 3, 0, 5, 2, 6, 5, 5, 5, 
        6, 7, 8, 8, 8, 5, 0, 10, 18, 11, 
        6, 8, 15, 14, 5, 13, 5, 6, 2, 14, 
        11, 11, 19, 19, 20, 16, 11, 13, 22, 8, 
        20, 21, 10, 22, 10, 1, 1, 1, 2, 1, 3,
        2, 2, 2, 8
    };
    
	public static final int[][] POTIONS = {
		{125, 179, 119, 131, 3014, 137, 3038, 9745, 143, 12146, 149, 185, 155, 3022, 10004, 161, 3030, 167, 2458, 173, 3046, 193, 6691}, // 1 dose
		{123, 177, 117, 129, 3012, 135, 3036, 9743, 141, 12144, 147, 183, 153, 3020, 10002, 159, 3028, 165, 2456, 171, 3044, 191, 6689}, // 2 dose
		{121, 175, 115, 127, 3010, 133, 3034, 9741, 139, 12142, 145, 181, 151, 3018, 10000, 157, 3026, 163, 2454, 169, 3042, 189, 6687}, // 3 dose
		{2428, 2446, 113, 2430, 3008, 2432, 3032, 9739, 2434, 12140, 2436, 2448, 2438, 3016, 9998, 2440, 3024, 2442, 2452, 2444, 3040, 2450, 6685} // 4 dose
	};
    
    public static boolean isEating(Player p, int item, int slot) {
    	for (int i = 0; i < FOOD.length; i++) {
    		for (int j = 0; j < FOOD[i].length; j++) {
    			if (item == FOOD[i][j]) {
    				eatFood(p, i, j, slot);
    				return true;
    			}
    		}
    	}
    	for (int i = 0; i < POTIONS.length; i++) {
        	for (int j = 0; j < POTIONS[i].length; j++) {
        		if (item == POTIONS[i][j]) {
        			drinkPotion(p, i, j, slot);
        			return true;
        		}
        	}
    	}
		return false;
    }

	private static void drinkPotion(final Player p, final int i, final int j, final int slot) {
		//TODO antipoisons/antifire
		int lastPotion = -1;
		int delay = 500;
		long lastDrink = 0;
		if (p.isDead() || p.getTemporaryAttribute("willDie") != null) {
			return;
		}
		
		if (p.getTemporaryAttribute("lastDrankPotion") != null) {
			lastPotion = (Integer) p.getTemporaryAttribute("lastDrankPotion");
		}
		if (p.getTemporaryAttribute("drinkPotionTimer") != null) {
			lastDrink = (Long) p.getTemporaryAttribute("drinkPotionTimer");
		}
		int time = (j == lastPotion) ? 1000 : 500;
		if (System.currentTimeMillis() - lastDrink < time) {
			return;
		}
		p.getActionSender().closeInterfaces();
		p.setTemporaryAttribute("drinkPotionTimer", System.currentTimeMillis());
		p.setTemporaryAttribute("lastDrankPotion", j);
		p.setTarget(null);
		p.resetCombatTurns();
		p.setEntityFocus(65535);
		p.removeTemporaryAttribute("autoCasting");
		if (j == 22) {
			p.getLevels().setTempHealth(p.getLevels().getLevel(3) + statBoost(p, 3, 0.17, false));
		}
		World.getInstance().registerEvent(new Event(delay) {
			@Override
			public void execute() {
				int newLevel;			
				this.stop();
				if (p.isDead() || p.getLevels().getLevel(3) <= 0) {
					return;
				}
				int item = i != 0 && POTIONS[i - 1][j] != -1 ? POTIONS[i - 1][j] : 229;
				if (!p.getInventory().replaceItemSlot(POTIONS[i][j],  item, slot)) {
					return;
				}
				switch(j) {
					case 0: 
						statBoost(p, 0, 0.125, false); 
						break;
						
					case 1: // anti poison
						p.setPoisonAmount(0);
						break;
						
					case 2: 
						statBoost(p, 2, 0.125, false); 
						break;
						
					case 3:
						for (int k = 0; k < 25; k++) {
							p.getLevels().setLevel(k, p.getLevels().getLevelForXp(k));
						}
						break;
						
					case 4:
						p.setRunEnergy(p.getRunEnergy() + 10);
						if (p.getRunEnergy() >= 100) {
							p.setRunEnergy(100);
						}
						break;
						
					case 5:
						statBoost(p, 1, 0.125, false);
						break;
						
					case 6:
						if (p.getLevels().getLevel(16) < (p.getLevels().getLevelForXp(16) + 3)) {
							p.getLevels().setLevel(16, p.getLevels().getLevel(16) + 3);
						}
						break;
						
					case 7:
						statBoost(p, 0, 0.125, false);
						statBoost(p, 2, 0.125, false); 
						break;
						
					case 8:
						newLevel = p.getLevels().getLevel(5) + (int)Math.round((double)p.getLevels().getLevelForXp(5) / 3 - 2);
						if (p.getLevels().getLevel(5) < newLevel) {
							p.getLevels().setLevel(5, newLevel);
							checkOverdose(p, 5);
						}
						break;
						
					case 9:
						newLevel = p.getLevels().getLevel(23) + (int)Math.round((double)p.getLevels().getLevelForXp(23) / 3 - 2);
						if (p.getLevels().getLevel(23) < newLevel) {
							p.getLevels().setLevel(23, newLevel);
						}
						break;
						
					case 10:
						statBoost(p, 0, 0.20, false);
						break;
						
					case 11: // super antipoison
						p.setPoisonAmount(0);
						p.getSettings().setSuperAntipoisonCycles(20);
						break;
						
					case 12:
						if (p.getLevels().getLevel(10) < (p.getLevels().getLevelForXp(10) + 3)) {
							p.getLevels().setLevel(10, p.getLevels().getLevel(10) + 3);
						}
						break;
						
					case 13:
						p.setRunEnergy(p.getRunEnergy() + 20);
						if (p.getRunEnergy() >= 100) {
							p.setRunEnergy(100);
						}
						break;
						
					case 14:
						if (p.getLevels().getLevel(21) < (p.getLevels().getLevelForXp(21) + 3)) {
							p.getLevels().setLevel(21, p.getLevels().getLevel(21) + 3);
						}
						break;
						
					case 15:
						statBoost(p, 2, 0.20, false);
						break;
						
					case 16:
						superRestore(p, 5, 0.33);
						superRestore(p, 1, 0.33);
						superRestore(p, 2, 0.33);
						superRestore(p, 0, 0.33);
						superRestore(p, 4, 0.33);
						superRestore(p, 6, 0.33);
						break;
						
					case 17:
						statBoost(p, 1, 0.20, false);
						break;
						
					case 18: // antifire
						p.getSettings().setAntifireCycles(20);
						break;
						
					case 19:
						statBoost(p, 4, 0.135, false);
						break;
						
					case 20:
						if (p.getLevels().getLevel(6) < (p.getLevels().getLevelForXp(6) + 4)) {
							p.getLevels().setLevel(6, p.getLevels().getLevel(6) + 4);
						}
						break;
						
					case 21:
						statBoost(p, 2, 0.14, false);
						statBoost(p, 0, 0.22, false);
						statBoost(p, 1, 0.106, true);
						statBoost(p, 3, 0.106, true);
						break;
			
					case 22:
						statBoost(p, 3, 0.17, false);
						statBoost(p, 1, 0.22, false);
						statBoost(p, 2, 0.09, true);
						statBoost(p, 0, 0.09, true);
						statBoost(p, 4, 0.09, true);
						statBoost(p, 6, 0.09, true);
						break;
				}
				p.animate(829);
				p.getActionSender().sendSkillLevels();
			}
		});
	}		

	private static void eatFood(final Player p, final int i, final int j, final int slot) {
		int delay = 500;
		if (p.isDead() || p.getLevels().getLevel(3) <= 0 || p.getTemporaryAttribute("willDie") != null) {
			return;
		}
		if (p.getTemporaryAttribute("eatFoodTimer") != null) {
			if (System.currentTimeMillis() - (Long) p.getTemporaryAttribute("eatFoodTimer") < 1200) {
				return;
			}
		}
		
		p.setTemporaryAttribute("eatFoodTimer", System.currentTimeMillis());
		p.setTarget(null);
		p.resetCombatTurns();
		p.setEntityFocus(65535);
		p.getActionSender().closeInterfaces();
		p.removeTemporaryAttribute("autoCasting");
		p.getLevels().setTempHealth(p.getLevels().getLevel(3) + FOOD_HEAL[j]);
		World.getInstance().registerEvent(new Event(delay) {
			@Override
			public void execute() {
				this.stop();
				if (p.isDead() || p.getLevels().getLevel(3) <= 0) {
					return;
				}
				int newHealth = p.getLevels().getLevel(3) + FOOD_HEAL[j];
				int item = i != 2 && FOOD[i + 1][j] != -1 ? FOOD[i + 1][j] : -1;
				if (!p.getInventory().replaceItemSlot(FOOD[i][j],  item, slot)) {
					return;
				}
				p.getActionSender().sendMessage("You eat the " + ItemDefinition.forId(FOOD[i][j]).getName().toLowerCase() + ".");
				p.getLevels().setLevel(3, newHealth > p.getLevels().getLevelForXp(3) ? p.getLevels().getLevelForXp(3) : newHealth);
				p.animate(829);
				p.getActionSender().sendSkillLevel(3);
			}
		});
	}
	
	public static int statBoost(Player p, int i, double j, boolean decreaseStat) {
		if (!decreaseStat) {
			double d = (double)p.getLevels().getLevelForXp(i) + (double)p.getLevels().getLevelForXp(i) * j;
			double d1 = (double)p.getLevels().getLevel(i) + (double)p.getLevels().getLevelForXp(i) * j;
			if (p.getLevels().getLevelForXp(i) == 1) {
				d1 = d = p.getLevels().getLevelForXp(i) + d;
			}
			if(d1 > d) {
				d1 = d;
			}
			p.getLevels().setLevel(i, (int)d1);
			return (int) d1;
		}
		if (decreaseStat) {
			double d2 = (double)p.getLevels().getLevelForXp(i) * j;
			p.getLevels().setLevel(i, p.getLevels().getLevel(i) - (int)d2);
			if (p.getLevels().getLevel(i) <= 0) {
				p.getLevels().setLevel(i, 1);
			}
		}
		return 0;
	}
	
	public static void superRestore(Player p, int i, double j) {
		double d = (double)p.getLevels().getLevelForXp(i) * j;
		if(p.getLevels().getLevel(i) == p.getLevels().getLevelForXp(i) * j) {
	        return;
		}
		if (p.getLevels().getLevel(i) >= p.getLevels().getLevelForXp(i)) {
			return;
		}
		p.getLevels().setLevel(i, p.getLevels().getLevel(i) + (int)d);
	    checkOverdose(p, i);
		if (p.getLevels().getLevel(i) <= 0) {
			p.getLevels().setLevel(i, 1);
		}
	}
	
	public static void checkOverdose(Player p, int i) {
		if (p.getLevels().getLevel(i) >=  p.getLevels().getLevelForXp(i)) {
			p.getLevels().setLevel(i, p.getLevels().getLevelForXp(i));
		}
	}
		
}
