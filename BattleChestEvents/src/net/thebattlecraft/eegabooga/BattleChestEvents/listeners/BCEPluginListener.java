package net.thebattlecraft.eegabooga.BattleChestEvents.listeners;

import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.PermissionManager;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * 
 * @author alkarin
 *
 */
public class BCEPluginListener extends ServerListener {

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        ///Permissions
        if (PermissionManager.permissionHandler == null) {
            Plugin permissions = BattleChestEvents.getBukkitServer().getPluginManager().getPlugin("Permissions");

            if (permissions != null) {
                PermissionManager.permissionHandler = ((Permissions) permissions).getHandler();
                PluginDescriptionFile pDesc = permissions.getDescription();
                System.out.println(BattleChestEvents.getPluginNameVersion() + " Loaded " + pDesc.getName() +
                		" version " + pDesc.getVersion() + " loaded.");
            }
        }


    }
}
