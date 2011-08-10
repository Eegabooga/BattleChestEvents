package net.thebattlecraft.eegabooga.BattleChestEvents.managers;

import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

public class PermissionManager {

	public static PermissionHandler permissionHandler;
	
	public static boolean isAdmin(Player player, String str) {
		if (permissionHandler != null){
			return permissionHandler.has(player, str) || player.isOp();		
		}
		return player.isOp();
	}	
}
