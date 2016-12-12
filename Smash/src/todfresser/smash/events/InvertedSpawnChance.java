package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.items.main.ItemManager;
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
		return 30;
	}
	
	@Override
	public int getProtectionTime() {
		return 10;
	}

	@Override
	public boolean perform(Game g) {
		for (Location l : g.getMap().getItemSpawns(g.getWorld())){
			for (Entity e : g.getWorld().getNearbyEntities(l, 2, 5, 2)){
				if (e.getType().equals(EntityType.DROPPED_ITEM)){
					ItemStack item = ((Item) e).getItemStack();
					if (item.hasItemMeta()){
						if (ItemManager.getItemDataID(item.getType(), item.getItemMeta().getDisplayName()) != 0) e.remove();
					}
				}
			}
		}
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
