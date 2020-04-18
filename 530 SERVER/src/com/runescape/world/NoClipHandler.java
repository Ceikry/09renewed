package com.runescape.world;

import java.io.*;

import com.runescape.model.Location;
import com.runescape.model.World;
import com.runescape.util.log.Logger;

public class NoClipHandler {

	public int maxObjects = 5000000;
	public int objectLoadedId = 0;
	public int[] objectX = new int[maxObjects];
	public int[] objectY = new int[maxObjects];
	public int[] oHeight = new int[maxObjects];
	
	private Logger logger = Logger.getInstance();
	
	public boolean checkPos(int absX , int absY,int height){
		for(int i = 0;i < maxObjects; i++){
			if(objectX[i] <= 0 || objectY[i] <= 0 || absX <= 0 || absY <= 0){
				continue;
			}
			if(objectX[i] == absX && objectY[i] == absY && oHeight[i] == height){
				return false;
			}
		}
		return true;
	}
	
	public void addItemOnPos(){
		for (int i = 0; i < maxObjects; i++) {
			GroundItem item = new GroundItem(995, 1, Location.location(objectX[i], objectY[i], oHeight[i]), null);
			World.getInstance().getGroundItems().newWorldItem(item);
		}
	}
	
	public NoClipHandler(){
		String line = "", token = "", token2 = "", token2_2 = "", token3[] = new String[500];
        BufferedReader mapFile = null;
        try
        {
            mapFile = new BufferedReader(new FileReader("./data/clip.cfg"));
            line = mapFile.readLine().trim();
        }
        catch(Exception e)
        {
            return;
        }
        while (line != null)
        {
            int spot = line.indexOf("=");
            if (spot > -1)
            {
                token = line.substring(0, spot).trim();
                token2 = line.substring(spot + 1).trim();
                token2_2 = token2.replaceAll("\t\t", "\t");
                token2_2 = token2_2.replaceAll("\t\t", "\t");
                token3 = token2_2.split("\t");
                if (token.equals("object"))
                {
                    objectX[objectLoadedId] = Integer.parseInt(token3[0]);
                    objectY[objectLoadedId] = Integer.parseInt(token3[1]);
					oHeight[objectLoadedId] = Integer.parseInt(token3[2]);
					objectLoadedId++;
                }
            }
            else
            {
                if (line.equals("[ENDOFOBJECTLIST]"))
                {
					maxObjects = objectLoadedId;
					logger.debug("Clipping coordinates loaded.");
                    try
                    {
                        mapFile.close();
                    }
                    catch (IOException ioexception)
                    {}
                    mapFile = null;
                    return;
                }
            }
            try
            {
                line = mapFile.readLine().trim();
            }
            catch (IOException ioexception1)
            {
                try
                {
                   mapFile.close();
                }
                catch (IOException ioexception)
                {}
                mapFile = null;
                return;
            }
        }
        return;
	}
}