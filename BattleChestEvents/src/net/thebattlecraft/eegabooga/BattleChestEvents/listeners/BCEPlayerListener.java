package net.thebattlecraft.eegabooga.BattleChestEvents.listeners;

import java.util.logging.Logger;

import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.BCEChestManager;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.PermissionManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class BCEPlayerListener extends PlayerListener {
	static final boolean DEBUG = true;
	
	Logger log = Logger.getLogger("Minecraft");
	public static BattleChestEvents plugin;
	public BCEPlayerListener(BattleChestEvents instance) {
		plugin = instance;
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
        final Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return; /// This can happen, minecraft is a strange beast
        final Material clickedMat = clickedBlock.getType();

        /// If this is an uninteresting block get out of here as quickly as we can
        if (!(clickedMat.equals(Material.CHEST) )) {
            return;
        } 
        
        /// Its not an event chest, we dont care what happens
        if (!BCEChestManager.isEventChest(clickedBlock.getLocation())){
        	return ;
        }
        /// we have an interaction with an event chest
        
        final Player player = event.getPlayer();
        boolean isAdmin = PermissionManager.isAdmin(player, "battlechestevents.admin");
        if (DEBUG) System.out.println("isAdmin=" + isAdmin);
        /// Admins can do whatever the hell they want
        if (isAdmin){
        	return;
        }
        /// No hitting our event chest!!!
        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            if (DEBUG) System.out.println("leftClicking our chest is futile!");
        	event.setCancelled(true);
        	return;
        }

        if (DEBUG) System.out.println("rightClickEvent");

        boolean chestLocked = BCEChestManager.isChestLocked(clickedBlock.getLocation());
        if (chestLocked && event.getAction() == Action.RIGHT_CLICK_BLOCK){
        	player.sendMessage("Hey! Wait until the event starts!");
			log.info("Hey! Wait until the event starts!");
			event.setCancelled(true);
			return;
        }
        if (DEBUG) System.out.println("Should be able to open");

        return;

        /// They have passed the tests, they can now do right click
        
        
//        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && )
//		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getMaterial()==Material.CHEST
//				&& (!(event.getAction() == Action.LEFT_CLICK_AIR)) && (!(event.getAction() == Action.RIGHT_CLICK_AIR))
//				&& (!(event.getAction() == Action.LEFT_CLICK_BLOCK))) {
//			//if((clickedBlock.getX() == -140) && (clickedBlock.getY() == 73) && (clickedBlock.getZ() == -74)){
//				log.info("This is the 'event.getAction() = Action.RIGHT_CLICK_BLOCK");
//				boolean isEventChest = BCEChestManager.isEventChest(clickedBlock.getLocation());
//				
//				if(!){
//					event.setCancelled(true);
//					Player play = event.getPlayer();
//					
//					return;
//				}
//				
//				else {
//					Player play = event.getPlayer();
//					play.sendMessage("[INFO] This is the event chest! be careful what you do with it!");
//					return;
//				}
//			//}
//		}
////		
//		else if((!(event.getAction() == Action.LEFT_CLICK_AIR)) && (!(event.getAction() == Action.RIGHT_CLICK_AIR))
//				&& (!(event.getAction() == Action.LEFT_CLICK_BLOCK))){
//			log.info("This isn't the event chest block!");
//			return;
//		}
//		else if(event.getAction() == Action.LEFT_CLICK_AIR) {
//			return;
//		}
//		else {
//			log.info("Combination not on the list!");
//			return;
//		}
        
        
	}
}
