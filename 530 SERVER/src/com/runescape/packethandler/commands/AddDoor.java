package com.runescape.packethandler.commands;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.runescape.model.player.Player;

//TODO THIS IS A WORK IN PROGRESS...

public class AddDoor implements Command {

public static String closedDoorFace;
public static String openDoorFace;

public static int doorId;

public static int defaultStatus;

public static int openDoorX;
public static int openDoorY;
public static int openDoorZ;

public static int closedDoorX;
public static int closedDoorY;
public static int closedDoorZ;
	
	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		if (cmd[1] == null || cmd[2] == null || cmd[3] == null) {
			player.getActionSender().sendMessage("Incorrect usage. Use as ::addDoor [id] [closeFace] [openFace]");
			return;
		}
		int id = Integer.valueOf(cmd[1]); //id of door
		String closedLocation = String.valueOf(cmd[2]); //1 north, 2 east, 3 south, 4 west
		String openLocation = String.valueOf(cmd[3]); //1 north, 2 east, 3 south, 4 west
		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();
		int playerZ = player.getLocation().getZ();
		if (Integer.valueOf(closedLocation) == 1) {
			closedDoorX = playerX; //this is ok
			closedDoorY = playerY;
			if (Integer.valueOf(openLocation) == 4 || Integer.valueOf(openLocation) == 2) {
				openDoorX = playerX;
				openDoorY = playerY + 1;
			}
		} else if (Integer.valueOf(closedLocation) == 2) {
			closedDoorX = playerX; //this is ok
			closedDoorY = playerY;
			if (Integer.valueOf(openLocation) == 3 || Integer.valueOf(openLocation) == 1) {
				openDoorX = playerX + 1;
				openDoorY = playerY;
			}
		} else if (Integer.valueOf(closedLocation) == 3) {
			closedDoorX = playerX; //this is ok
			closedDoorY = playerY + 1;
			if (Integer.valueOf(openLocation) == 4 || Integer.valueOf(openLocation) == 2) {
				openDoorX = playerX;
				openDoorY = playerY;
			}
		} else if (Integer.valueOf(closedLocation) == 4) {
			closedDoorX = playerX + 1; //this is ok
			closedDoorY = playerY;
			if (Integer.valueOf(openLocation) == 3 || Integer.valueOf(openLocation) == 1) {
				openDoorX = playerX;
				openDoorY = playerY;
			}
		}
		closedDoorZ = playerZ;
		openDoorZ = playerZ;
		doorId = id;
		closedDoorFace = closedLocation;
		openDoorFace = openLocation;
		
		try {
			AddDoor.writeDoor(player, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	
    public static void writeDoor(Player player, String command) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("data\\doors.xml");
        Element root = document.getDocumentElement();

        // Root Element
        Element rootElement = document.getDocumentElement();

        Collection<DoorReturns> svr = new ArrayList<DoorReturns>();
        svr.add(new DoorReturns());

        for (DoorReturns i : svr) {
            // elements
        	// DOOR ROOT
            Element door = document.createElement("door");
            rootElement.appendChild(door);
            
            // default status
            Element defStatus = document.createElement("defaultStatus");
            defStatus.appendChild(document.createTextNode(DoorReturns.getDefStatus()));
            door.appendChild(defStatus);
            
            // open door id
            Element openDoorId = document.createElement("openDoorId");
            openDoorId.appendChild(document.createTextNode(Integer.toString(i.getDoorId())));
            door.appendChild(openDoorId);
            
            // closed door id
            Element closedDoorId = document.createElement("closedDoorId");
            closedDoorId.appendChild(document.createTextNode(Integer.toString(i.getDoorId())));
            door.appendChild(closedDoorId);
            
            // closed door location
            Element closedDoorLocation = document.createElement("closedDoorLocation");
            door.appendChild(closedDoorLocation);
            
            // npc x
            Element closedX = document.createElement("x");
            closedX.appendChild(document.createTextNode(Integer.toString(i.getClosedX())));
            closedDoorLocation.appendChild(closedX);
            
            // npc y
            Element closedY = document.createElement("y");
            closedX.appendChild(document.createTextNode(Integer.toString(i.getClosedY())));
            closedDoorLocation.appendChild(closedX);
            
            // npc z
            Element closedZ = document.createElement("z");
            closedX.appendChild(document.createTextNode(Integer.toString(i.getClosedZ())));
            closedDoorLocation.appendChild(closedX);

            root.appendChild(door);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult("data\\doors.xml");
        transformer.transform(source, result);
    }

    public static class DoorReturns {
        
        public Integer getDoorId() {
        	return doorId;
        }
        
        public Integer getClosedX() { 
        	return openDoorX; 
        }
        
        public Integer getClosedY() { 
        	return openDoorY; 
        }
        
        public Integer getClosedZ() { 
        	return openDoorZ; 
        }
        
        public Integer getOpenX() { 
        	return closedDoorX; 
        }
        
        public Integer getOpenY() { 
        	return closedDoorY; 
        }
        
        public Integer getOpenZ() { 
        	return closedDoorZ; 
        }
        
        public static String getDefStatus() {
        	if (defaultStatus == 0) {
        		return "CLOSED";
        	} else if (defaultStatus == 1) {
        		return "OPEN";
        	} else {
            	return "CLOSED";
        	}
        }
    }
    
    public static void chooseStatus(Player player) {
		player.getActionSender().modifyText("", 232, 2);
		player.getActionSender().modifyText("Closed", 232, 3);
		player.getActionSender().modifyText("Open", 232, 4);
		player.getActionSender().modifyText("", 232, 5);
		player.getActionSender().sendChatboxInterface2(232);
		//dirInterfaceOpen = true;
    }
    
    public static void setDoorId(Player player) {
    	
    }

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}

}
