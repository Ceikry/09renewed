package com.runescape;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.runescape.content.events.GroundItems;
import com.runescape.io.MapDataLoader;
import com.runescape.io.MapDataPacker;
import com.runescape.io.XStreamPlayerLoader;
import com.runescape.model.ItemDefinition;
import com.runescape.model.World;
import com.runescape.model.npc.NPCDefinition;
import com.runescape.model.objects.Doors;
import com.runescape.model.objects.DoubleDoors;
import com.runescape.packethandler.PacketHandlers;
import com.runescape.util.log.Logger;
import com.runescape.world.NoClipHandler;
import com.runescape.world.clip.region.ObjectDef;
import com.runescape.world.clip.region.Region;

/**
 * A varek has called it before, the 'central motor' of the game.
 * 
 * That means it handles periodic updating and packet handling/creation.
 * 
 * @author Graham
 *
 */
public class GameEngine {

	/**
	 * Logger instance.
	 */
	private Logger logger = Logger.getInstance();
	
	/**
	 * Running flag.
	 */
	private boolean isRunning;
	/**
	 * This makes you wish that Java supported typedefs.
	 */
	private Map<Integer, int[]> mapData;
	
	/**
	 * Our worker thread.
	 */
	private WorkerThread workerThread;
	/**
	 * Thread group.
	 */
	private ThreadGroup threads = new ThreadGroup("RuneShard");
	/**
     * Handles npc noclip by Mad Turnip
     */
    public static NoClipHandler noClipHandler = new NoClipHandler();
	/**
	 * Creates other things vital to the game logic, like the world class.
	 * @throws Exception 
	 */
	public GameEngine() throws Exception {
		/*
		 * We are running.
		 */
		isRunning = true;
		/*
		 * Check if mapdata packed file exists, if not, then we pack it.
		 */
		File packedFile = new File("data/mapdata/packed.dat");
		if(!packedFile.exists()) {
			MapDataPacker.pack("data/mapdata/unpacked/", "data/mapdata/packed.dat");
		}
		/*
		 * Actually load the mapdata.
		 */
		mapData = new HashMap<Integer, int[]>();
		MapDataLoader.load(mapData);
		/*
		 * Load handlers.
		 */
		PacketHandlers.loadHandlers();
		/*
		 * Loads region data
		 * and object data
		 */
		Region.load();
		logger.info("Loading region configs...");
		ObjectDef.loadConfig();
		logger.info("Loading object region configs...");
		/*
		 * Load item definitions.
		 */
		logger.info("Loading item definitions...");
		ItemDefinition.load();
		logger.info("Loading item prices...");
		ItemDefinition.loadPrices();
		logger.info("Loading npc definitions...");
		NPCDefinition.load();
		
		/*
		 * Set up the world.
		 */
		logger.info("Setting up world...");
		World.getInstance().setEngine(this);

		logger.info("Loading ground items...");
		GroundItems.loadItems();
		logger.info("Loading doors...");
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		/*
		 * Start the worker thread.
		 */
		logger.info("Launching worker thread...");
		workerThread = new WorkerThread(new XStreamPlayerLoader());
		newThread("WorkerThread", workerThread);
	}
	
	public void newThread(String name, Runnable r) {
		new Thread(threads, r, name).start();
	}
	
	/**
	 * Handle a major update.
	 */
	public void majorUpdate() {
		World.getInstance().majorUpdate();
	}
	
	/**
	 * Handle a minor update.
	 */
	public void minorUpdate() {
		World.getInstance().minorUpdate();
	}
	
	/**
	 * Called every tick.
	 */
	public void tick() {
		World.getInstance().tick();
	}
	
	/**
	 * Gets the is running flag.
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * Sets the is running flag.
	 * @param isRunning
	 */
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	/**
	 * Gets the worker thread.
	 * @return
	 */
	public WorkerThread getWorkerThread() {
		return workerThread;
	}
	
	/**
	 * Stops threads, saves games, etc.
	 */
	public void cleanup() {
		threads.interrupt();
	}

	public int[] getMapData(int region) {
		return mapData.get(region);
	}
}
