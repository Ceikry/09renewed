package com.runescape.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.runescape.Constants;
import com.runescape.GameEngine;
import com.runescape.content.events.AreaVariables;
import com.runescape.content.events.LevelChangeEvent;
import com.runescape.content.events.LowerPotionCycles;
import com.runescape.content.events.RunEnergyEvent;
import com.runescape.content.events.SkullCycle;
import com.runescape.event.AreaEvent;
import com.runescape.event.CoordinateEvent;
import com.runescape.event.Event;
import com.runescape.model.npc.NPC;
import com.runescape.model.npc.NPCDefinition;
import com.runescape.model.npc.NPCDrop;
import com.runescape.model.player.Player;
import com.runescape.packetbuilder.NPCUpdate;
import com.runescape.packetbuilder.PlayerUpdate;
import com.runescape.util.EntityList;
import com.runescape.util.XStreamUtil;
import com.runescape.util.log.Logger;
import com.runescape.world.ClanManager;
import com.runescape.world.GroundItemManager;
import com.runescape.world.ObjectLocations;
import com.runescape.world.ObjectManager;
import com.runescape.world.ShopManager;

/**
 * Represents the 'game world'.
 * @author Graham
 *
 */
public class World {
	/**
	 * Amount of NPCs at last spawn
	 */
	private int npcSpawns;
	/**
	 * Logger instance.
	 */
	private Logger logger = Logger.getInstance();
	
	/**
	 * The world instance.
	 */
	private static World instance = null;
	
	/**
	 * A list of connected players.
	 */
	private EntityList<Player> players;
	
	/**
	 * A list of npcs.
	 */
	private EntityList<NPC> npcs;
	
	/**
	 * A list of pending events.
	 */
	private List<Event> events;
	private List<Event> eventsToAdd;
	private List<Event> eventsToRemove;
	
	/**
	 * The game engine.
	 */
	private GameEngine engine;
	
	/**
	 * The item manager.
	 */
	private GroundItemManager itemManager;
	
	/**
	 * The global object manager.
	 */
	private ObjectManager objectManager;
	
	/**
	 * Manages pre-loaded object coordinates .
	 * (Rare objects that could be spawned client side, Rune rocks for example).
	 */
	private ObjectLocations objectLocations;
	
	/**
	 * The clan manager.
	 */
	private ClanManager clanManager;
	/**
	 * The shop manager.
	 */
	private ShopManager shopManager;
	
	
	private boolean updateInProgress;
	
	/**
	 * We create the world here.
	 */
	private World() {
		players = new EntityList<Player>(Constants.PLAYER_CAP);
		npcs = new EntityList<NPC>(Constants.NPC_CAP);
		events = new ArrayList<Event>();
		eventsToAdd = new ArrayList<Event>();
		eventsToRemove = new ArrayList<Event>();
	}
	
	/**
	 * Register our global events.
	 */
	public void registerGlobalEvents() {
		registerEvent(new RunEnergyEvent());
		registerEvent(new LevelChangeEvent());
		registerEvent(new SkullCycle());
		registerEvent(new AreaVariables());
		registerEvent(new LowerPotionCycles());
		//registerEvent(new GroundItems());
	}
	
	/**
	 * Registers an event.
	 * @param event
	 */
	public void registerEvent(Event event) {
		eventsToAdd.add(event);
	}
	
	/**
	 * Registers a 'coordiante' event.
	 * @param event
	 */
	public void registerCoordinateEvent(final CoordinateEvent event) {
		registerEvent(new Event(0) {
			@Override
			public void execute() {
				boolean standingStill = event.getPlayer().getSprites().getPrimarySprite() == -1 && event.getPlayer().getSprites().getSecondarySprite() == -1;
				if(event.getPlayer().getDistanceEvent() == null || !event.getPlayer().getDistanceEvent().equals(event)) {
					this.stop();
					return;
				}
				if (standingStill) {
					if((event.getPlayer().getLocation().equals(event.getTargetLocation()) && event.getPlayer().getLocation().equals(event.getOldLocation())) || event.getFailedAttempts() >= 15) {
						if(this.getTick() == 0) {
							event.run();
							this.stop();
							event.setPlayerNull();
						} else {
							if(!event.hasReached()) {
								event.setReached(true);
							} else {
								event.run();
								this.stop();
								event.setPlayerNull();
							}
						}
					}
				} else {
					if(!event.getPlayer().getLocation().equals(event.getOldLocation())) {
						event.setOldLocation(event.getPlayer().getLocation());
					} else {
						event.incrementFailedAttempts();
					}
				}
				this.setTick(200);
			}
		});
	}
	
	public void registerCoordinateEvent(final AreaEvent event) {
		registerEvent(new Event(0) {
			@Override
			public void execute() {
				boolean standingStill = event.getPlayer().getSprites().getPrimarySprite() == -1 && event.getPlayer().getSprites().getSecondarySprite() == -1;
				if(event.getPlayer().getDistanceEvent() == null || !event.getPlayer().getDistanceEvent().equals(event)) {
					this.stop();
					return;
				}
				if (standingStill) {
					if(event.inArea()) {
						event.run();
						this.stop();
						event.setPlayerNull();
						return;
					}
				}
				this.setTick(500);
			}
		});
	}
	
	/**
	 * Processes any pending events.
	 */
	public void processEvents() {
		for(Event e : eventsToAdd) {
			events.add(e);
		}
		eventsToAdd.clear();
		for(Event e : events) {
			if(e.isStopped()) {
				eventsToRemove.add(e);
			} else if(e.isReady()) {
				e.run();
			}
		}
		for(Event e : eventsToRemove) {
			events.remove(e);
		}
		eventsToRemove.clear();
	}
	
	/**
	 * Get the world instance.
	 * @return
	 */
	public static World getInstance() {
		if(instance == null) {
			instance = new World();
		}
		return instance;
	}
	
	/**
	 * Called whenever there is a major update.
	 */
	public void majorUpdate() {
		for(Player p : players) {
			p.tick();
			p.processQueuedHits();
			p.getWalkingQueue().getNextPlayerMovement();
		}
		for(NPC n : npcs) {
			n.tick();
			n.processQueuedHits();
		}
		for(Player p : players) {
			// sometimes players aren't removed always: do that here
			if(!p.getPlayerDetails().getSession().isConnected()) {
				unregister(p);
			} else {
				PlayerUpdate.update(p);
				NPCUpdate.update(p);
			}
		}
		for(Player p : players) {
			p.getUpdateFlags().clear();
			p.getHits().clear();
			p.setRebuildNpcList(false);
		}
		for(NPC n : npcs) {
			n.getUpdateFlags().clear();
			n.getHits().clear();
		}
	}
	
	/**
	 * Called whenever there is a minor update.
	 */
	public void minorUpdate() {
		
	}
	
	/**
	 * Called every tick.
	 */
	public void tick() {
		for(Player p : players) {
			p.processQueuedPackets();
		}
		processEvents();
	}

	/**
	 * Gets the players list.
	 * @return
	 */
	public EntityList<Player> getPlayerList() {
		return players;
	}
	
	/**
	 * Gets the npcs list.
	 * @return
	 */
	public EntityList<NPC> getNpcList() {
		return npcs;
	}
	
	/**
	 * Register a player.
	 * @param p
	 * @return the player slot
	 */
	public int register(Player p) {
		int slot = -1;
		players.add(p);
		slot = p.getIndex();
		if(slot != -1) {
			logger.info(p.getPlayerDetails().getDisplayName() + " is online" + " [Total online: "+players.size()+", This Player's ID: "+slot+"]");
		} else {
			logger.info("Could not register " + p.getPlayerDetails().getDisplayName() + " - too many online [Total online: "+players.size()+"]");
		}
		return slot;
	}
	
	/**
	 * Unregister a player.
	 * @param p
	 */
	public void unregister(Player p) {
		if (p.getTrade() != null) {
			p.getTrade().decline();
			p.setTrade(null);
		}
		
		
		
		
		
		removeAllPlayersNPCs(p);
		clanManager.leaveChannel(p);
		players.remove(p);
		engine.getWorkerThread().savePlayer(p);
		logger.info(p.getPlayerDetails().getDisplayName() + " went offline " + " [Total online: "+players.size()+"]");
		p.getFriends().unregistered();
	}

	/**
	 * Will remove all NPCs which are spawned specifically for this player.
	 */
	public void removeAllPlayersNPCs(Player p) {
		for (NPC n : npcs) {
			if (n != null) {
				if (n.getOwner() != null) {
					if (n.getOwner().equals(p)) {
						n.setHidden(true);
						npcs.remove(n);
					}
				}
			}
		}
	}

	/**
	 * Sets the game engine.
	 * @param gameEngine
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void setEngine(GameEngine gameEngine) throws FileNotFoundException {
		this.engine = gameEngine;
		logger.debug("Loading npc spawns...");
		List<NPC> spawns = (List<NPC>) XStreamUtil.getXStream().fromXML(new FileInputStream("data/npcs.xml"));
		for(NPC n : spawns) {
			npcs.add(n);
		}
		logger.debug("Loaded " + spawns.size() + " npc spawns.");
		this.npcSpawns = spawns.size();
		objectLocations = new ObjectLocations();
		shopManager = new ShopManager();
		itemManager = new GroundItemManager();
		objectManager = new ObjectManager();
		clanManager = new ClanManager();
		registerGlobalEvents();
		//cache = new LoadCache();
	}
	
	public void reloadSpawns(Player player) throws FileNotFoundException {
		List<NPC> spawns = (List<NPC>) XStreamUtil.getXStream().fromXML(new FileInputStream("data/npcs.xml"));
		if (spawns.size() > this.npcSpawns) {
			for (int addI = this.npcSpawns; addI == spawns.size(); addI++) {
				for(NPC n : spawns) {
					npcs.add(n);
				}
			}
		} else {
			player.getActionSender().sendMessage("No new spawns to reload.");
		}
		this.npcSpawns = spawns.size();
	}
	
	public void reloadShops() throws FileNotFoundException {
		shopManager = new ShopManager();
	}
	
	public void reloadNPCDefs() throws FileNotFoundException {
		NPCDefinition.load();
	}
	
	public void reloadDrops() throws FileNotFoundException {
		NPCDrop.load();
	}
	
	private final long serverStartupTime = System.currentTimeMillis();

	/**
	 * Gets mapdata for a region.
	 * @param region
	 * @return
	 */
	public int[] getMapData(int region) {
		return engine.getMapData(region);
	}
	
	/**
	 * Checks if a player is online.
	 * @param name
	 * @return
	 */
	public boolean isOnline(String name) {
		for(Player p : players) {
			if(p != null) {
				if(p.getPlayerDetails().getUsername().equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets the item manager.
	 * @return
	 */
	public GroundItemManager getGroundItems() {
		return itemManager;
	}
	
	/**
	 * Gets the object manager.
	 * @return
	 */
	public ObjectManager getGlobalObjects() {
		return objectManager;
	}
	
	public ShopManager getShopManager() {
		return shopManager;
	}
	
	
	public ObjectLocations getObjectLocations() {
		return objectLocations;
	}

	public ClanManager getClanManager() {
		return clanManager;
	}
	

	public Player getPlayerForName(String name) {
		for (Player p : players) {
			if (p != null && p.getUsername().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	public void setUpdateInProgress(boolean updateInProgress) {
		this.updateInProgress = updateInProgress;
	}

	public boolean isUpdateInProgress() {
		return updateInProgress;
	}

	public GameEngine getGameEngine() {
		return engine;
	}

	public long getServerStartupTime() {
		return serverStartupTime;
	}
}
