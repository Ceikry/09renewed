package com.runescape;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import com.runescape.cache.Cache;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.net.ConnectionHandler;
import com.runescape.net.ConnectionThrottleFilter;
import com.runescape.util.log.Logger;


/**
 * The central class of the server.
 * @author Graham
 *
 */
public class Server {
	/**
	 * Logger instance.
	 */
	private Logger logger = Logger.getInstance();
	
	/**
	 * The game engine: where all the game logic processing takes place.
	 */
	private GameEngine engine;
	
	/**
	 * The port to listen on.
	 */
	private static final int PORT = 43594;
	
	/**
	 * We do a major update every 500ms.
	 */
	private static final long MAJOR_UPDATE_TIME = 500;
	
	/**
	 * We do a minor update every 100ms.
	 */
	private static final long MINOR_UPDATE_TIME = 100;
	
	/**
	 * We do a gc update every minute.
	 */
	private static final long GC_UPDATE_TIME = 3600;
	
	/**
	 * Acceptor.
	 */
	private IoAcceptor acceptor;
	
	/**
	 * Connection handler.
	 */
	private ConnectionHandler connectionHandler;
	
	/**
	 * Connection throttle filter.
	 */
	private ConnectionThrottleFilter throttleFilter;

	private static Cache cache;

	/**
	 * Create this server.
	 * @throws Exception 
	 */
	public Server() throws Exception {
		/*
		 * Start everything rolling.
		 */
		logger.info("RuneScape 530");
        setCache(new Cache(new File("./data/cache/")));
		engine = new GameEngine();
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler(engine);
		listen(PORT);
	}
	
	/**
	 * Start listening on the specified port.
	 * @param port
	 * @throws IOException 
	 */
	public void listen(int port) throws IOException {
		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);
		
		throttleFilter = new ConnectionThrottleFilter(Constants.THROTTLE_FILTER_INTERVAL*1000);
		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		acceptor.bind(new InetSocketAddress(port), connectionHandler, sac);
		logger.info("Listening on port " + port + ".");		
	}
	
	/**
	 * The main loop of the server.
	 * 
	 * Processors major and minor update events, calls the game engine, handles IO etc.
	 * 
	 * Waits 30 ms per tick.
	 */
	public void go() {
		/*
		 * While we are running.
		 */
		while(engine.isRunning()) {
			try {
				processEvents();
				} catch (Exception e) {
					e.printStackTrace();
				}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				break;
			}
		}
		/*
		 * Shut down!
		 */
		logger.info("Unbinding all ports...");
		acceptor.unbindAll();
		/*
		 * Cleanup.
		 */
		logger.info("Interrupting all threads...");
		engine.cleanup();
	}
	
	/**
	 * When the last major update was.
	 * 
	 * These updates are done every 500 ms.
	 * 
	 * They include sending the player update packet, handling walking, handling npcs, etc.
	 */
	private long lastMajorUpdate = System.currentTimeMillis();
	
	/**
	 * When the last minor update was.
	 * 
	 * These updates are done every 200 ms.
	 */
	private long lastMinorUpdate = System.currentTimeMillis();
	
	/**
	 * When the last gc update was.
	 * 
	 * These updates are done every minute.
	 */
	private long lastGcUpdate = System.currentTimeMillis();
	
	/**
	 * We check for major and minor updates here.
	 * 
	 * We also poll the IO every iteration.
	 */
	public void processEvents() {
		if(lastMajorUpdate + MAJOR_UPDATE_TIME < System.currentTimeMillis()) {
			lastMajorUpdate = System.currentTimeMillis();
			engine.majorUpdate();
		}
		if(lastMinorUpdate + MINOR_UPDATE_TIME < System.currentTimeMillis()) {
			lastMinorUpdate = System.currentTimeMillis();
			engine.minorUpdate();
		}
		if(lastGcUpdate + GC_UPDATE_TIME < System.currentTimeMillis()) {
			lastGcUpdate = System.currentTimeMillis();
			System.gc();
			System.runFinalization();
		}
		engine.tick();
	}
	
	/**
	 * Get the game engine.
	 * @return
	 */
	public GameEngine getEngine() {
		return this.engine;
	}

	@SuppressWarnings("static-access")
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public static Cache getCache() {
		return cache;
	}

}
