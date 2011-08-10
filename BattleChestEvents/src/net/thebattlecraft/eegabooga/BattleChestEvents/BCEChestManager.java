package net.thebattlecraft.eegabooga.BattleChestEvents;

import java.util.Arrays;
import java.util.List;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.ContainerBlock;
import org.bukkit.inventory.ItemStack;

public class BCEChestManager {
	
	static ItemStack[] inventory1;
	static ItemStack[] inventory2;
	static ItemStack[] inventory3;
	static ItemStack[] inventory4;
	static ItemStack[] inventory5;
	
	static boolean isProtected;
	
	public static void findChest(Block block, ItemStack itemStack) {
		BlockState blockState;
		 if ((blockState = block.getState()) != null && (blockState instanceof ContainerBlock)) {
			 Block chestBlock = findAdjacentBlock(block, Material.CHEST);
			 ContainerBlock containerBlock = (ContainerBlock) blockState;
			 for(ItemStack item : theInventory()){
				 containerBlock.getInventory().addItem(item);
			 } 
		 }
	}
	
	public static void createChest(Location point, ItemStack itemStack) {
		World world = point.getWorld();
			
		int x_loc = point.getBlockX();
		int y_loc = point.getBlockY();
		int z_loc = point.getBlockZ();
		
		Block block = null;
		BlockState blockState;
		 if ((blockState = block.getState()) != null && (blockState instanceof ContainerBlock)) {
			 Block doubleChestBlock = findAdjacentBlock(block, Material.CHEST);
			 ContainerBlock containerBlock = (ContainerBlock) blockState;
			 
			
		 }
	}
	
	private static Block findAdjacentBlock(Block block, Material material, Block... ignore) {
		BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
		List<Block> ignoreList = Arrays.asList(ignore);
		
		for(BlockFace face : faces) {
			Block adjacentBlock = block.getRelative(face);
			
			if(adjacentBlock.getType() == material && !ignoreList.contains(adjacentBlock)) {
				return adjacentBlock;
			}
		}
		return null;
		
	}
	
	public void chestInventory() {
		
		/*
		 * To add items to the item array create a new item stack:
		 * 	>>	ItemStack <yourItem>Stack = new ItemStack(<itemID>, itemAmount);
		 * 
		 * Next, add it to one of the ItemStack inventory arrays.
		 *  >>  ItemStack[] inventory = new ItemStack[]{diamondStack, ironStack, <yourItem>Stack};
		 * 
		 * If you want to add multiple unstackable items, add them to the array multiple times, 
		 * but make sure itemAmount == 1.
		 * 
		 * If you add another inventory array, be sure to change the variable numOfInventories in
		 * the randomInventory method.
		 */
		
		ItemStack diamondStack = new ItemStack(264, 2);
		ItemStack ironIngotStack = new ItemStack(265, 10);
		ItemStack ironSword = new ItemStack(267, 1);
		
		ItemStack[] inventory1 = new ItemStack[] {diamondStack,ironIngotStack,ironSword};
		ItemStack[] inventory2 = new ItemStack[] {};
		ItemStack[] inventory3 = new ItemStack[] {};
		ItemStack[] inventory4 = new ItemStack[] {};
		ItemStack[] inventory5 = new ItemStack[] {};			
		}
			
	public static int randomInventory() {
		int numOfInventories = 1;
		int random = (int) (Math.random() * numOfInventories);
		return random;
	}
	
	public static ItemStack[] theInventory() {
		int random = randomInventory();
		if(random == 1){
			return inventory1;
		}
		else if(random == 2){
			return inventory2;
		}
		else if(random == 3){
			return inventory3;
		}
		else if(random == 4){
			return inventory4;
		}
		else if(random == 5){
			return inventory5;
		}
		return inventory1;
	}
	
	public void isChestProtected() {
		// TODO: Figure out how to protect and unprotect this chest.
		if(isProtected == true) {
			// TODO: Protect chest here.
		}
		if(isProtected == false) {
			// TODO: Allow chest to be accessed.
		}
	}

}
