package net.thebattlecraft.eegabooga.BattleChestEvents.objects;

import org.bukkit.Location;

public class EventChest{
	public Location loc;
	public boolean locked;
	public EventChest(Location loc, boolean locked){
		this.loc = loc; this.locked = locked;
	}
}