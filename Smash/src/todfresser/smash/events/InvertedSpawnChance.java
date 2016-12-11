package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.map.Game;

public class InvertedSpawnChance extends SmashEvent{

	@Override
	public String getDisplayName() {
		
		return ChatColor.RED + "Verdrehte Spawnraten!";
	}

	@Override
	public Material getType() {
		return Material.MOB_SPAWNER;
	}

	@Override
	public int getChance() {
		return 20;
	}
	
	@Override
	public int getTime() {
		return 20;
	}

	@Override
	public boolean perform(Game g) {
		for (int id : g.getAllowedItemIDs()){
			g.setCustomItemSpawnChance(id, 50 - g.getCustomItemSpawnChance(id));
		}
		return true;
	}
	
	@Override
	public void cancel(Game g) {
		for (int id : g.getAllowedItemIDs()){
			g.setCustomItemSpawnChance(id, 50 - g.getCustomItemSpawnChance(id));
		}
	}
	
}
