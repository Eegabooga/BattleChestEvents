package net.thebattlecraft.eegabooga.BattleChestEvents.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thebattlecraft.eegabooga.BattleChestEvents.objects.EventChest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.inventory.ItemStack;

public class BCEChestManager {
	
	static Map<String, EventChest> eventchests = new HashMap<String,EventChest>();

	public static void createChest(Location point, List<ItemStack> itemStacks) {
		CraftWorld w = (CraftWorld) point.getWorld();
		Block b = w.getBlockAt(point);
		b.setType(Material.CHEST);
        CraftChest ch = new CraftChest(b);
        
        for (ItemStack itemStack : itemStacks){
        	ch.getInventory().addItem(itemStack);
        }
        
        ch.update(true);
        
		EventChest ec = new EventChest(point, true);
		eventchests.put(getStringLoc(point), ec);

//		System.out.println("SHOULDVE CREATD BLOCK  " + " point=" + point.getBlockX() + ":" + point.getBlockY() + ":" + point.getBlockZ());
	}

	
	public static boolean isChestLocked(Location loc) {
		EventChest ec = eventchests.get(getStringLoc(loc));
		if (ec != null){
			return ec.locked;}
		return false; /// Nothing here?
	}

	public static void lockChest(Location loc) {
		EventChest ec = eventchests.get(getStringLoc(loc));
		if (ec != null){
			ec.locked = true;}
	}

	public static void unLockChest(Location loc) {
		EventChest ec = eventchests.get(getStringLoc(loc));
		if (ec != null){
			ec.locked = false;}
	}

	public static void removeChest(Location loc) {
		World w = loc.getWorld();
		Block b = w.getBlockAt(loc);
		/// Get rid of the inventory, otherwise it will pop out once we change it to air
		CraftChest cc = new CraftChest( b);
		cc.getInventory().clear();
		
		/// Get rid of our chest
		b.setTypeId(Material.AIR.getId());
		/// Remove from our hash
		eventchests.remove(getStringLoc(loc));
	}
	
	public static String getStringLoc(Location loc) {
		return loc.getWorld().getName() + ":" + loc.getX() +":" + loc.getY() + ":" + loc.getZ();
	}

	public static boolean isEventChest(Location location) {
		return eventchests.containsKey(getStringLoc(location));
	}


//	static boolean isProtected;
//	static ItemStack[] inventory1;
//	static ItemStack[] inventory2;
//	static ItemStack[] inventory3;
//	static ItemStack[] inventory4;
//	static ItemStack[] inventory5;
//	public static void findChest(Block block, ItemStack itemStack) {	
//		BlockState blockState;
//		 if ((blockState = block.getState()) != null && (blockState instanceof ContainerBlock)) {
//			 Block chestBlock = findAdjacentBlock(block, Material.CHEST);
//			 ContainerBlock containerBlock = (ContainerBlock) blockState;
//			 for(ItemStack item : theInventory()){
//				 containerBlock.getInventory().addItem(item);
//			 } 
//		 }
//	}
//	
//	private static Block findAdjacentBlock(Block block, Material material, Block... ignore) {
//		BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
//		List<Block> ignoreList = Arrays.asList(ignore);
//		
//		for(BlockFace face : faces) {
//			Block adjacentBlock = block.getRelative(face);
//			
//			if(adjacentBlock.getType() == material && !ignoreList.contains(adjacentBlock)) {
//				return adjacentBlock;
//			}
//		}
//		return null;
//		
//	}
//	
//	public void chestInventory() {
//		
//		/*
//		 * To add items to the item array create a new item stack:
//		 * 	>>	ItemStack <yourItem>Stack = new ItemStack(<itemID>, itemAmount);
//		 * 
//		 * Next, add it to one of the ItemStack inventory arrays.
//		 *  >>  ItemStack[] inventory = new ItemStack[]{diamondStack, ironStack, <yourItem>Stack};
//		 * 
//		 * If you want to add multiple unstackable items, add them to the array multiple times, 
//		 * but make sure itemAmount == 1.
//		 * 
//		 * If you add another inventory array, be sure to change the variable numOfInventories in
//		 * the randomInventory method.
//		 */
//		
//		ItemStack diamondStack = new ItemStack(264, 2);
//		ItemStack ironIngotStack = new ItemStack(265, 10);
//		ItemStack ironSword = new ItemStack(267, 1);
//		
//		ItemStack[] inventory1 = new ItemStack[] {diamondStack,ironIngotStack,ironSword};
//		ItemStack[] inventory2 = new ItemStack[] {};
//		ItemStack[] inventory3 = new ItemStack[] {};
//		ItemStack[] inventory4 = new ItemStack[] {};
//		ItemStack[] inventory5 = new ItemStack[] {};			
//		}
//			
//	public static int randomInventory() {
//		int numOfInventories = 1;
//		int random = (int) (Math.random() * numOfInventories);
//		return random;
//	}
//	
//	public static ItemStack[] theInventory() {
//		int random = randomInventory();
//		if(random == 1){
//			return inventory1;
//		}
//		else if(random == 2){
//			return inventory2;
//		}
//		else if(random == 3){
//			return inventory3;
//		}
//		else if(random == 4){
//			return inventory4;
//		}
//		else if(random == 5){
//			return inventory5;
//		}
//		return inventory1;
//	}
}
