package com.runescape.packethandler.commands;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;

import com.runescape.model.World;
import com.runescape.model.npc.NPC;
import com.runescape.model.player.Player;

import javax.xml.parsers.*;

/**
 * 
 * NPC Spawner
 * @author Abexlry
 */
public class AddNPC implements Command {
	
	public static int npcId;
	
	public static int npcX;
	public static int npcY;
	public static int npcZ;
	
	public static int npcMinX;
	public static int npcMinY;
	public static int npcMinZ;
	
	public static int npcMaxX;
	public static int npcMaxY;
	public static int npcMaxZ;
	
	public static int npcWalkType;
	
	public static int npcFaceDirection;
	
	public static boolean dirInterfaceOpen;
	
	@Override
	public void execute(Player player, String command) {
		if (player.getRights() >= minimumRightsNeeded() && player.getRights() < 4) {
		String cmd[] = command.split(" ");
		int id = Integer.valueOf(cmd[1]); //id of npc
		int walk = Integer.valueOf(cmd[2]); //walktype: 0 for STATIC(stationary with face), 1 for RANGE (moves in a 3x3 square)
		int x = player.getLocation().getX();
		int y = player.getLocation().getY();
		int z = player.getLocation().getZ();
		NPC npc = new NPC(id);
		npc.setLocation(player.getLocation());
		npc.readResolve();
		World.getInstance().getNpcList().add(npc); //places a temporary npc on map (will be stationary)
		
		npcId = id;
		
		npcX = x;
		npcY = y;
		npcZ = z;
		
		npcMinX = (x - 1);
		npcMinY = (y - 1);
		npcMinZ = z;
		
		npcMaxX = (x + 1);
		npcMaxY = (y + 1);
		npcMaxZ = z;
				
		npcWalkType = walk;
		
		try {
			if (walk == 0) {
				chooseFace(player);
			}
			if (walk == 1) {
				rangedMovement(player, command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	
    public static void rangedMovement(Player player, String command) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("data\\npcs.xml");
        Element root = document.getDocumentElement();

        // Root Element
        Element rootElement = document.getDocumentElement();

        Collection<NPCReturns> svr = new ArrayList<NPCReturns>();
        svr.add(new NPCReturns());

        for (NPCReturns i : svr) {
            // elements
        	// NPC ROOT
            Element npc = document.createElement("npc");
            rootElement.appendChild(npc);
            
            // npc id
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(Integer.toString(i.getId())));
            npc.appendChild(id);
            
            // NPC LOCATION ROOT
            Element location = document.createElement("location");
            npc.appendChild(location);
            
            // npc X
            Element locationX = document.createElement("x");
            locationX.appendChild(document.createTextNode(Integer.toString(i.getLocationX())));
            location.appendChild(locationX);
            
            // npc Y
            Element locationY = document.createElement("y");
            locationY.appendChild(document.createTextNode(Integer.toString(i.getLocationY())));
            location.appendChild(locationY);
            
            // npc Z
            Element locationZ = document.createElement("z");
            locationZ.appendChild(document.createTextNode(Integer.toString(i.getLocationZ())));
            location.appendChild(locationZ);

            // npc walktype
            Element walkType = document.createElement("walkType");
            walkType.appendChild(document.createTextNode(i.getWalkType(player)));
            npc.appendChild(walkType);
            
            // NPC MIN COORDS ROOT
            Element minCoords = document.createElement("minimumCoords");
            npc.appendChild(minCoords);
            
            // npc min coords x
            Element minCoordsX = document.createElement("x");
            minCoordsX.appendChild(document.createTextNode(Integer.toString(i.getMinX())));
            minCoords.appendChild(minCoordsX);
            
            // npc min coords y
            Element minCoordsY = document.createElement("y");
            minCoordsY.appendChild(document.createTextNode(Integer.toString(i.getMinY())));
            minCoords.appendChild(minCoordsY);
            
            // npc min coords z
            Element minCoordsZ = document.createElement("z");
            minCoordsZ.appendChild(document.createTextNode(Integer.toString(i.getMinZ())));
            minCoords.appendChild(minCoordsZ);
            
            // NPC MAX COORDS ROOT
            Element maxCoords = document.createElement("maximumCoords");
            npc.appendChild(maxCoords);
            
            // npc max coords x
            Element maxCoordsX = document.createElement("x");
            maxCoordsX.appendChild(document.createTextNode(Integer.toString(i.getMaxX())));
            maxCoords.appendChild(maxCoordsX);
            
            // npc min coords y
            Element maxCoordsY = document.createElement("y");
            maxCoordsY.appendChild(document.createTextNode(Integer.toString(i.getMaxY())));
            maxCoords.appendChild(maxCoordsY);
            
            // npc min coords z
            Element maxCoordsZ = document.createElement("z");
            maxCoordsZ.appendChild(document.createTextNode(Integer.toString(i.getMaxZ())));
            maxCoords.appendChild(maxCoordsZ);

            root.appendChild(npc);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult("data\\npcs.xml");
        transformer.transform(source, result);
    }
    
    public static void staticMovement(Player player, String command) throws Exception {
    	dirInterfaceOpen = false;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("data\\npcs.xml");
        Element root = document.getDocumentElement();

        // Root Element
        Element rootElement = document.getDocumentElement();

        Collection<NPCReturns> svr = new ArrayList<NPCReturns>();
        svr.add(new NPCReturns());

        for (NPCReturns i : svr) {
            // elements
        	// NPC ROOT
            Element npc = document.createElement("npc");
            rootElement.appendChild(npc);
            
            // npc id
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(Integer.toString(i.getId())));
            npc.appendChild(id);
            
            // NPC LOCATION ROOT
            Element location = document.createElement("location");
            npc.appendChild(location);
            
            // npc X
            Element locationX = document.createElement("x");
            locationX.appendChild(document.createTextNode(Integer.toString(i.getLocationX())));
            location.appendChild(locationX);
            
            // npc Y
            Element locationY = document.createElement("y");
            locationY.appendChild(document.createTextNode(Integer.toString(i.getLocationY())));
            location.appendChild(locationY);
            
            // npc Z
            Element locationZ = document.createElement("z");
            locationZ.appendChild(document.createTextNode(Integer.toString(i.getLocationZ())));
            location.appendChild(locationZ);

            // npc walktype
            Element walkType = document.createElement("walkType");
            walkType.appendChild(document.createTextNode(i.getWalkType(player)));
            npc.appendChild(walkType);
            
            // npc faceDir
            Element faceDir = document.createElement("faceDirection");
            faceDir.appendChild(document.createTextNode(Integer.toString(i.getFaceDir())));
            npc.appendChild(faceDir);

            root.appendChild(npc);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult("data\\npcs.xml");
        transformer.transform(source, result);
    }

    public static class NPCReturns {
    	
    	public Integer getId() {
    		return npcId;
    	}
    	
        public String getWalkType(Player player) { 
        	if (npcWalkType == 1) {
        		return "RANGE";
        	} else if (npcWalkType == 0) {
        		chooseFace(player);
        		return "STATIC"; 
        	} else {
        		return "RANGE";
        	}
        }
        
        public Integer getFaceDir() {
        	return npcFaceDirection;
        }
        
        public Integer getLocationX() { 
        	return npcX; 
        }
        
        public Integer getLocationY() { 
        	return npcY; 
        }
        
        public Integer getLocationZ() { 
        	return npcZ; 
        }
        
        public Integer getMinX() {
        	return npcMinX;
        }
        
        public Integer getMinY() {
        	return npcMinY;
        }
        
        public Integer getMinZ() {
        	return npcMinZ;
        }
        
        public Integer getMaxX() {
        	return npcMaxX;
        }
        
        public Integer getMaxY() {
        	return npcMaxY;
        }
        
        public Integer getMaxZ() {
        	return npcMaxZ;
        }  
        
    	public static void setFaceDir(int face) {
    		npcFaceDirection = face;
    	}
    }
    
    public static void chooseFace(Player player) {
		player.getActionSender().modifyText("North", 232, 2);
		player.getActionSender().modifyText("West", 232, 3);
		player.getActionSender().modifyText("East", 232, 4);
		player.getActionSender().modifyText("South", 232, 5);
		player.getActionSender().sendChatboxInterface2(232);
		dirInterfaceOpen = true;
    }

	@Override
	public int minimumRightsNeeded() {
		return 2;
	}
}
