package com.runescape.packethandler.commands;

import java.util.HashMap;
import java.util.Map;

import com.runescape.model.player.Player;

/**
 * 
 * Command manager.
 * @author Graham
 * @author Luke132
 */
public class CommandManager {

	private static Map<String, Command> commandMap = new HashMap<String, Command>();
	static {
		commandMap.put("item", new Pickup());
		commandMap.put("gfx", new Graphic());
		commandMap.put("anim", new Animation());
		commandMap.put("tele", new Teleport());
		commandMap.put("teleto", new TeleToPlayer());
		commandMap.put("interface", new Interface());
		commandMap.put("under", new Underground());
		commandMap.put("above", new AboveGround());
		commandMap.put("height", new Height());
		commandMap.put("coords", new Coordinates());
		commandMap.put("bank", new Bank());
		commandMap.put("npc", new SpawnNPC());
		commandMap.put("shop", new OpenShop());
		commandMap.put("update", new SystemUpdate());
		commandMap.put("object", new SpawnObject());
		commandMap.put("char", new CharacterAppearance());
		commandMap.put("empty", new EmptyInventory());
		commandMap.put("setlevel", new SetLevel());
		commandMap.put("kick", new Kick());
		commandMap.put("kickall", new KickAll());
		commandMap.put("config", new TestConfig());
		commandMap.put("switch", new SwitchMagic());
		commandMap.put("pnpc", new PlayerAsNPC());
		commandMap.put("master", new Master());
		commandMap.put("value", new InventoryPrice());
		commandMap.put("uptime", new Uptime());
		commandMap.put("players", new Players());
		commandMap.put("yell", new Yell());
		commandMap.put("male", new Male());
		commandMap.put("female", new Female());
		commandMap.put("up", new MoveUp());
		commandMap.put("down", new MoveDown());
		commandMap.put("left", new MoveLeft());
		commandMap.put("right", new MoveRight());
		commandMap.put("addnpc", new AddNPC());
		commandMap.put("addnpc2", new AddNPC2());
		commandMap.put("teleto", new TeleToPlayer());
		commandMap.put("teletome", new TeleToMe());
		commandMap.put("reloadshops", new ReloadShops());
		commandMap.put("reloaddrops", new ReloadNPCDrops());
		commandMap.put("chathead", new ChatHeads());
		commandMap.put("arrow", new HeadArrow());
		commandMap.put("muse", new PlayTrack());
		commandMap.put("me", new PlayMusicEffect());
		commandMap.put("sound", new PlaySound());
		commandMap.put("reloadnpcdefs", new ReloadNPCDefs());
		//commandMap.put("reloadspawns", new ReloadSpawns());
		commandMap.put("system", new SystemAnnounce());
		//commandMap.put("adddoor", new AddDoor());
		//commandMap.put("password", new ChangePassword());
		commandMap.put("ac", new AddClip());
		commandMap.put("showclips", new ShowClips());
	}
	
	public static void execute(Player player, String command) {
		String name = "";
		if (command.indexOf(' ') > -1) {
			name = command.substring(0, command.indexOf(' '));
		} else {
			name = command;
		}
		name = name.toLowerCase();
		if (commandMap.get(name) != null) {
			if (player.getRights() >= commandMap.get(name).minimumRightsNeeded()) {
				commandMap.get(name).execute(player, command);
			}
		}
	}
}
