package net.thebattlecraft.eegabooga.BattleChestEvents;

import java.util.logging.Logger;

import net.thebattlecraft.eegabooga.BattleChestEvents.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.ContainerBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class BCEPlayerListener extends PlayerListener {
	Logger log = Logger.getLogger("Minecraft");
	public static BattleChestEvents plugin;
	public BCEPlayerListener(BattleChestEvents instance) {
		plugin = instance;
	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block clickedBlock = event.getClickedBlock();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getMaterial()==Material.CHEST
				&& (!(event.getAction() == Action.LEFT_CLICK_AIR)) && (!(event.getAction() == Action.RIGHT_CLICK_AIR))
				&& (!(event.getAction() == Action.LEFT_CLICK_BLOCK))) {
			//if((clickedBlock.getX() == -140) && (clickedBlock.getY() == 73) && (clickedBlock.getZ() == -74)){
				log.info("This is the 'event.getAction() = Action.RIGHT_CLICK_BLOCK");
				if(!BattleChestEvents.permissionHandler.has(player, "battlechestevents.admin")){
					event.setCancelled(true);
					Player play = event.getPlayer();
					play.sendMessage("Hey! Wait until the event starts!");
					log.info("Hey! Wait until the event starts!");
					return;
				}
				
				else {
					Player play = event.getPlayer();
					play.sendMessage("[INFO] This is the event chest! be careful what you do with it!");
					return;
				}
			//}
		}
		
		else if((!(event.getAction() == Action.LEFT_CLICK_AIR)) && (!(event.getAction() == Action.RIGHT_CLICK_AIR))
				&& (!(event.getAction() == Action.LEFT_CLICK_BLOCK))){
			log.info("This isn't the event chest block!");
			return;
		}
		else if(event.getAction() == Action.LEFT_CLICK_AIR) {
			return;
		}
		else {
			log.info("Combination not on the list!");
			return;
		}
	}
}
