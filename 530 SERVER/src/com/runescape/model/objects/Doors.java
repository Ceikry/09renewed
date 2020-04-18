package com.runescape.model.objects;
 
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.model.player.Player;
import com.runescape.util.log.Logger;
import com.runescape.world.WorldObject;

/**
 * 
 * @author Killamess
 * Basic door manipulation
 *
 */

public class Doors {
	
	private Logger logger = Logger.getInstance();
 
    private static Doors singleton = null;
 
    private static List<Doors> doors = new ArrayList<Doors>();
 
    private File doorFile;
     
    public static Doors getSingleton() {
        if (singleton == null) {
            singleton = new Doors("./data/doors.txt");
        }
        return singleton;
    }
 
    private Doors(String file){
        doorFile = new File(file);  
    }
     
    private Doors(int door, int x, int y, int z, int face, int type, int open) {
        this.doorId = door;
        this.originalId = door;
        this.doorX = x;
        this.doorY = y;
        this.originalX = x;
        this.originalY = y;
        this.doorZ = z;
        this.originalFace = face;
        this.currentFace = face;
        this.type = type;
        this.open = open;
    }
     
    private static Doors getDoor(int id, int x, int y, int z) {
        for (Doors d : doors) {
            if (d.doorId == id) {
                if (d.doorX == x && d.doorY == y && d.doorZ == z) {
                    return d;
                }
            }
        }
        return null;
    }
    
    static Player player;
     
    public static boolean handleDoor(int id, int x, int y, int z) {
        Doors d = getDoor(id, x, y, z);
        if (d == null) {
             //if (DoubleDoors.getSingleton().handleDoor(id, x, y, z)) {
             //    return true;
             //}
             return false;
        } 
        int xAdjustment = 0, yAdjustment = 0;
        if (d.type == 0) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = -1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    yAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    yAdjustment = -1;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    yAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    yAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            }
        } else if (d.type == 9) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            }
        }
        if (xAdjustment != 0 || yAdjustment != 0) { 
        	WorldObject o = new WorldObject(-1, Location.location(d.doorX, d.doorY, d.doorZ), 0, d.type, false);
            World.getInstance().getGlobalObjects().add(o);
        }
        if (d.doorX == d.originalX && d.doorY == d.originalY) {
            d.doorX += xAdjustment;
            d.doorY += yAdjustment;
        } else { 
        	WorldObject o = new WorldObject(-1, Location.location(d.doorX, d.doorY, d.doorZ), 0, d.type, false);
            World.getInstance().getGlobalObjects().add(o);
            d.doorX = d.originalX;
            d.doorY = d.originalY;
        }
    	if (id == 2550 || id == 2551 || id == 2556 || id == 2558 || id == 2557 || id == 3014) {
			d.doorId = id;
    	}
        if (d.doorId == d.originalId) {
            if (d.open == 0) {
                d.doorId += 1;
            } else if (d.open == 1) {
                d.doorId -= 1;
            }
        } else if (d.doorId != d.originalId) {
            if (d.open == 0) {
                d.doorId -= 1;
            } else if (d.open == 1) {
                d.doorId += 1;
            }
        }
        World.getInstance().getGlobalObjects().add(new WorldObject(-1, Location.location(d.doorX, d.doorY, d.doorZ), getNextFace(d), d.type, false));
        for (Player p : World.getInstance().getPlayerList()) {
			if (p != null) {
				if (p.getLocation().withinDistance(Location.location(d.doorX, d.doorY, d.doorZ), 60)) {
			        refreshDoorsForPlayer(p);
				}
			}
        }
        return true;
    }
    
	public static void refreshDoorsForPlayer(Player p) {
		for (Doors door : doors) {
	        Location currentLoc = getCurrentLoc(door);
	        Location originalLoc = getOriginalLoc(door);
			if (p.getLocation().withinDistance(Location.location(door.doorX, door.doorY, door.doorZ), 60)) {
		        if (door.currentFace != door.originalFace) {
		        	p.getActionSender().removeObject(originalLoc, door.originalFace, door.type);
					p.getActionSender().createObject(door.doorId, currentLoc, door.currentFace, door.type);
		        } else if (door.currentFace == door.originalFace) {
		        	p.getActionSender().removeObject(Doors.getOtherLoc(door), Doors.getOtherFace(door), door.type);
					p.getActionSender().createObject(door.originalId, originalLoc, door.originalFace, door.type);
		        }
			}
		}
		if (p != null) {

		}
	}
    
    private static int getOtherFace(Doors d) {
    	int f = 0;
        if (d.type == 0) {
            if (d.open == 0) {
                if (d.originalFace == 0) {
                    f = 1;
                } else if (d.originalFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2) {
                    f = 3;
                } else if (d.originalFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            }
        } else if (d.type == 9) {
            if (d.open == 0) {
                if (d.originalFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            }
        }
    	return f;
    }
    
    private static Location getCurrentLoc(Doors d) {
    	Location currentLoc = null;
        currentLoc = Location.location(d.doorX, d.doorY, d.doorZ);
		return currentLoc;
    }
    
    private static Location getOriginalLoc(Doors d) {
    	Location originalLoc = null;
    	originalLoc = Location.location(d.originalX, d.originalY, d.doorZ);
    	return originalLoc;
    }
    
    private static Location getOtherLoc(Doors d) {
    	int xAdjustment = 0;
    	int yAdjustment = 0;
    	if (xAdjustment == 0 && yAdjustment == 0) {
    	        if (d.type == 0) {
    	            if (d.open == 0) {
    	                if (d.originalFace == 0 && d.currentFace == 0) {
    	                    xAdjustment = -1;
    	                } else if (d.originalFace == 1 && d.currentFace == 1) {
    	                    yAdjustment = 1;
    	                } else if (d.originalFace == 2 && d.currentFace == 2) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 3 && d.currentFace == 3) {
    	                    yAdjustment = -1;
    	                }
    	            } else if (d.open == 1) {
    	                if (d.originalFace == 0 && d.currentFace == 0) {
    	                    yAdjustment = 1;
    	                } else if (d.originalFace == 1 && d.currentFace == 1) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 2 && d.currentFace == 2) {
    	                    yAdjustment = -1;
    	                } else if (d.originalFace == 3 && d.currentFace == 3) {
    	                    xAdjustment = -1;
    	                }
    	            }
    	        } else if (d.type == 9) {
    	            if (d.open == 0) {
    	                if (d.originalFace == 0 && d.currentFace == 0) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 1 && d.currentFace == 1) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 2 && d.currentFace == 2) {
    	                    xAdjustment = -1;
    	                } else if (d.originalFace == 3 && d.currentFace == 3) {
    	                    xAdjustment = -1;
    	                }
    	            } else if (d.open == 1) {
    	                if (d.originalFace == 0 && d.currentFace == 0) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 1 && d.currentFace == 1) {
    	                    xAdjustment = 1;
    	                } else if (d.originalFace == 2 && d.currentFace == 2) {
    	                    xAdjustment = -1;
    	                } else if (d.originalFace == 3 && d.currentFace == 3) {
    	                    xAdjustment = -1;
    	                }
    	            }
    	        }
    	}
    	Location otherLoc = null;
    	otherLoc = Location.location(d.originalX + xAdjustment, d.originalY + yAdjustment, d.doorZ);
    	return otherLoc;
    }
     
    private static int getNextFace(Doors d) {
        int f = d.originalFace;
        if (d.type == 0) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 3;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            }
        } else if (d.type == 9) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace){
                    f = d.originalFace;
                }
            }
        }
        d.currentFace = f;
        return f;
    }
     
    public void load() {
        long start = System.currentTimeMillis();
        try {
            singleton.processLineByLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.debug("Loaded "+ doors.size() +" doors in "+ (System.currentTimeMillis() - start) +"ms.");
    }
     
    private final void processLineByLine() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(doorFile));
            try {
                while(scanner.hasNextLine()) {
                    processLine(scanner.nextLine());
                }
         } finally {
                scanner.close();
            }
    }
     
    protected void processLine(String line){
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(" ");
        try {
            while(scanner.hasNextLine()) {
                int id = Integer.parseInt(scanner.next());
                int x = Integer.parseInt(scanner.next());
                    int y = Integer.parseInt(scanner.next());
                    int f = Integer.parseInt(scanner.next());
                    int z = Integer.parseInt(scanner.next());
                    int t = Integer.parseInt(scanner.next());
                    doors.add(new Doors(id,x,y,z,f,t,alreadyOpen(id)?1:0));
            }
        } finally {
            scanner.close();
        }
    }
     
    private boolean alreadyOpen(int id) {
        for (int i = 0; i < openDoors.length; i++) {
            if (openDoors[i] == id) {
                return true;
            }
        }
        return false;
    }
 
    private int doorId;
    private int originalId;
    private int doorX;
    private int doorY;
    private int originalX;
    private int originalY;
    private int doorZ;
    private int originalFace;
    private int currentFace;
    private int type;
    private int open;
    
    public static boolean isDoor(int objectId, int objectX, int objectY) {
    	for (Doors door : doors) {
    		if (objectId == door.doorId) {
    			Doors.getSingleton();
    			if(Doors.handleDoor(objectId, objectX, objectY, player.getLocation().getZ())) {
    			}
    			return true;
    		}
    	}
    	return false;
    }
     
    private static int[] openDoors = {1504, 1514, 1517, 1520, 1531, 1534, 2033, 2035, 2037, 2998, 3271, 4468, 4697, 6101, 6103, 6105, 6107, 6109, 6111, 6113, 6115, 6976, 6978, 8696, 8819, 10261, 10263, 10265, 11708, 11710, 11712, 11715, 11994, 12445, 13002,};
    
}