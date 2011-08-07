package net.thebattlecraft.eegabooga.BattleChestEvents;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;


public class BattleChestEvents extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");
	public final BCEPlayerListener playerListener = new BCEPlayerListener(this);
	public static String prefix = "";
	public static PermissionHandler permissionHandler;
	
	public void onDisable() {
		log.info(prefix()+"Plugin has been disabled.");
	}

	public void onEnable() {
		log.info(prefix()+"Plugin has been enabled.");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
		setupPermissions();
	}
	
	public String prefix() {
		PluginDescriptionFile desc = this.getDescription();
		prefix = "[" + desc.getName() + " v" + desc.getVersion() + "]";
		return prefix;
	}
	
	private void setupPermissions() {
	    if (permissionHandler != null) {
	        return;
	    }
	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    
	    if (permissionsPlugin == null) {
	        log.info(prefix()+"Permission system not detected, defaulting to OP");
	        return;
	    }
	    
	    permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	    log.info(prefix()+"Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
	}
}
