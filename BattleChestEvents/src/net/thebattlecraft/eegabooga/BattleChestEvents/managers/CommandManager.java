package net.thebattlecraft.eegabooga.BattleChestEvents.managers;

import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ChestEvent;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * 
 * @author alkarin
 *
 */
public class CommandManager  {

	BCEChestManager chestManager;
	BCETimeManager timeManager;
	public CommandManager(BCEChestManager chestManager, BCETimeManager timeManager){
		this.chestManager = chestManager;
		this.timeManager = timeManager;
	}

	public boolean handleCommand(Player player, Command cmd,
			String commandLabel, String[] args) {
		String commandStr = cmd.getName().toLowerCase();
//		final int length = args.length;

		if (commandStr.equalsIgnoreCase("newchestevent")){
			ChestEvent ce = BattleChestEvents.getNextChestEvent();
			if (ce == null){
				player.sendMessage("All events are currently running");
			} else {
				ce.setEventTimeNow();
				timeManager.scheduleEvent(ce);
			}
			
		}
		else if(commandStr.equalsIgnoreCase("cancelchestevent")){
			BCETimeManager.cancelEvents();
			player.sendMessage("Event has been canceled.");
			return true;
		}
		return false;
	}

	
}
