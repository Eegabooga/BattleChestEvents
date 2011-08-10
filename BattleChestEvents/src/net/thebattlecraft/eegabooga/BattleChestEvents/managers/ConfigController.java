package net.thebattlecraft.eegabooga.BattleChestEvents.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ChestEvent;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ScheduledTime;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.SpawnLocation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.config.Configuration;
/**
 * 
 * @author alkarin
 *
 */
public class ConfigController {
    public static Configuration BCEConfig;
	public static final int SECONDS = 1000;
	public static String scheduleAtIntervals;
	
    public static boolean getBoolean(String node) {return BCEConfig.getBoolean(node, false);}
    public static  String getString(String node) {return BCEConfig.getString(node,null);}
    public static int getInt(String node) {return BCEConfig.getInt(node, 0);}
    public static double getDouble(String node) {return BCEConfig.getDouble(node, -1);}
	public static List<String> getStringList(String string) {return BCEConfig.getStringList(string, null);}

    public static void setConfig(File f){
    	BCEConfig = new Configuration(f);
    	BCEConfig.load();
    	parseConfigFile();
    }
    
    
	public static void parseConfigFile(){

		ArrayList<ScheduledTime> stlist = new ArrayList<ScheduledTime>();
		ScheduledTime st;
		/// Add our premade scheduled times events
		int timeToSpawn = ConfigController.getInt("timeToSpawnChestBeforeEvent");
		int lengthOfEvent = ConfigController.getInt("lengthOfEvent");
		scheduleAtIntervals = ConfigController.getString("scheduleAtIntervals");
		
		stlist.add(new ScheduledTime(timeToSpawn*SECONDS, ScheduledTime.SPAWN));
		stlist.add(new ScheduledTime(0L,ScheduledTime.UNLOCK));
		 /// notice this one is negative
		stlist.add(new ScheduledTime(-lengthOfEvent*SECONDS, ScheduledTime.DESPAWN));
		

		/// There is a better way, I just dont know it, and dont want to learn right now
		// Timer Announcements
        String[] splits = ConfigController.getString("timerAnnouncements").
		replaceAll("[}{]", "").split(",");

		for (String s : splits){
			String ss[] = s.split("=");
			st = new ScheduledTime();
			Long timeBeforeEvent = Long.valueOf(ss[0].replace(" ", ""));
			st.setMessage(ss[1]);
			st.setTimeBeforeEvent(timeBeforeEvent*SECONDS);

			addNewScheduledTime(stlist,st);
		}
		Collections.sort(stlist);
		
		

		Server server = BattleChestEvents.getBukkitServer();
		List<String> list = ConfigController.getStringList("spawnLocations");
		for (String s : list){
			ChestEvent ce = new ChestEvent();

			/// Get Spawn location, ugly parsing
			String[] sInfo = s.substring(1, s.length() -1).split(",");
			String name = sInfo[0].split("=")[1];
			String world = sInfo[1].split("=")[1];
			int x = Integer.valueOf(sInfo[2].split("=")[1].replace(" ", ""));
			int y = Integer.valueOf(sInfo[3].split("=")[1].replace(" ", ""));
			int z = Integer.valueOf(sInfo[4].split("=")[1].replace(" ", ""));
			boolean randomizeQuantities = Boolean.valueOf(sInfo[5].split("=")[1].replace(" ", ""));
			ce.setRandomizeQuantities(randomizeQuantities);
			/// Add our items/// ugly parsing
			int index = sInfo[6].indexOf("=");
			String itemStr = sInfo[6].substring(index, sInfo[6].length()).replaceAll("[{}]", "");
			SpawnLocation sl = new SpawnLocation(name, new Location(server.getWorld(world),x,y,z));
			ce.setSpawnLocation(sl);
					
			index = itemStr.indexOf("[");
			itemStr = itemStr.substring(index+1,itemStr.length());

			ItemStack is = null;
			String item[] = itemStr.split("=");
			item[1] = item[1].replace("]", "");

			Material mat = Material.getMaterial(returnID(item[0]));

			is = new ItemStack(mat, Integer.valueOf(item[1].replace(" ", "")) );

			ce.addItem(is);
			for (int i=7; i<sInfo.length;i++){
				itemStr = sInfo[i].replaceAll("[{ }]", "").replace("]", "");

				item= itemStr.split("=");
				mat = Material.getMaterial(returnID(item[0]));
				is = new ItemStack(mat, Integer.valueOf(item[1].replace(" ", "")) );
				ce.addItem(is);
			}
			
			/// add our announcements for this event
			
			ce.addScheduledTime(stlist);
			
			BattleChestEvents.addChestEvent(ce);
		}
	
	}

	public static void addNewScheduledTime(List<ScheduledTime> stes , ScheduledTime ste){
		boolean replaced = false;
		for (ScheduledTime se : stes){
//			System.out.println("<" + se.getTime() + "><" + ste.getTime()+">   " + (se.getTime() == ste.getTime() ));
			if (se.getTime() == ste.getTime()){
				if (se.isNormal() && ste.isSpecial()){
					se.setSpecial(ste.getSpecial());
					replaced = true;
					break;
				} else if (se.isSpecial() && ste.isNormal() ){
					se.setMessage(ste.getMessage());
					replaced = true;
					break;
				} 
			}
		}
		if (!replaced)
			stes.add(ste);
	}	

	// From iConomyChestShop
    //Returns the id of a material
    public static int returnID(String name) {
        Material[] mat = Material.values();
        int temp = 9999;
        Material tmp = null;
        for (Material m : mat) {
            if (m.name().toLowerCase().replaceAll("_", "").startsWith(name.toLowerCase().replaceAll("_", "").replaceAll(" ", ""))) {
                if (m.name().length() < temp) {
                    tmp = m;
                    temp = m.name().length();
                }
            }
        }
        if (tmp != null) {
            return tmp.getId();
        }
        return -1;
    }
}
