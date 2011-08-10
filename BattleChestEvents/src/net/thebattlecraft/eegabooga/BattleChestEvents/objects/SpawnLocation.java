package net.thebattlecraft.eegabooga.BattleChestEvents.objects;

import org.bukkit.Location;

public class SpawnLocation {
	public String name;
	public Location location;
	public SpawnLocation(String name, Location location){
		this.name = name;
		this.location = location;
	}
}
