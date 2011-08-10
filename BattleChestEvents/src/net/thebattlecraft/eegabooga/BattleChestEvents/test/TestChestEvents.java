package net.thebattlecraft.eegabooga.BattleChestEvents.test;

import java.util.List;

import junit.framework.TestCase;
import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.ConfigController;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TestChestEvents extends TestCase{

	public void testAll(){
		configTest();
	}

	public void configTest(){
		ConfigController.setConfig(
				BattleChestEvents.loadFile(getClass().getResourceAsStream(BattleChestEvents.DEFAULT_CONFIGURATION_FILE), 
						"./config.yml"));
	
		parseConfigFile();
	}
	
	

	/**
	 * Really a duplicate of whats inside the config controller
	 * but!!!!
	 * things like server, material, itemstack are taken out
	 */
	public void parseConfigFile(){
		
		
		int timeToSpawn = ConfigController.getInt("timeToChestSpawn");
		System.out.println("timeToSpawn = " + timeToSpawn);
		int timeToDespawn = ConfigController.getInt("timeToChestDespawn");
		System.out.println("timeToDespawn = " + timeToDespawn);
		
		/// There is a better way, I just dont know it, and dont want to learn right now
		// Timer Announcements
		String[] splits = ConfigController.getString("timerAnnouncements").
		replaceAll("[}{]", "").split(",");
		for (String s : splits){
			//		System.out.println(s);
			String ss[] = s.split("=");
			String intstr = ss[0].replace(" ", "");
			int time = Integer.valueOf(intstr);
			//				commandController.addUniqueRank(ss[0], ss[1]);
			System.out.println( time + ":" + ss[1]);
		}


//		Server server = BattleChestEvents.getBukkitServer();
		List<String> list = ConfigController.getStringList("spawnLocations");
		for (String s : list){
			System.out.println(s);
//			String[] sInfo = s.replaceAll("[{}]", "").split(",");
			String[] sInfo = s.substring(1, s.length() -1).split(",");
			String name = sInfo[0].split("=")[1];
			String world = sInfo[1].split("=")[1];
			int x = Integer.valueOf(sInfo[2].split("=")[1].replace(" ", ""));
			int y = Integer.valueOf(sInfo[3].split("=")[1].replace(" ", ""));
			int z = Integer.valueOf(sInfo[4].split("=")[1].replace(" ", ""));
			System.out.println("spawnLoc  '" + name + "' at " + world + " " + x + ":" + y +
					":" + z );
//			SpawnLocation sl = new SpawnLocation(name, new Location(
//					server.getWorld(world),x,y,z));

//			System.out.println(sInfo[5]);
//			System.out.println(sInfo[6]);
			int index = sInfo[5].indexOf("=");
			String itemStr = sInfo[5].substring(index, sInfo[5].length()).
				replaceAll("[{}]", "");
//			System.out.println(itemStr);
			index = itemStr.indexOf("[");
			itemStr = itemStr.substring(index+1,itemStr.length());
			System.out.println(itemStr);
			ItemStack is = null;
			String item[] = itemStr.split("=");
			item[1] = item[1].replace("]", "");
//			System.out.println("item0=" + item[0]);
			Material mat = Material.getMaterial(item[0]);
//			System.out.println(mat);
			if (mat != null)
				is = new ItemStack(mat, Integer.valueOf(item[1].replace(" ", "")) );
			System.out.println(is);
			// sl.addItem(is);
			for (int i=6; i<sInfo.length;i++){
				itemStr = sInfo[i].replaceAll("[{ }]", "").replace("]", "");
				System.out.println();
				item= itemStr.split("=");
				mat = Material.getMaterial(item[0]);
				if (mat != null)
					is = new ItemStack(mat, Integer.valueOf(item[1].replace(" ", "")) );
				// sl.addItem(is);
			}

		}
	
	}

}
