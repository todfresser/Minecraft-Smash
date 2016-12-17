package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.map.Game;
import todfresser.smash.mobs.main.SmashEntity;
import todfresser.smash.mobs.main.SmashEntityType;

public class SpiderRain extends SmashEvent{

	@Override
	public String getDisplayName() {
		return ChatColor.DARK_PURPLE + "Spinnen-Regen";
	}

	@Override
	public Material getType() {
		return Material.SPIDER_EYE;
	}
	
	@Override
	public int getChance() {
		return 10;
	}
	
	@Override
	public int getTime() {
		return 8;
	}
	
	@Override
	public int getProtectionTime() {
		return 40;
	}

	@Override
	public boolean perform(Game g) {
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxZ = Integer.MIN_VALUE;
		int minZ = Integer.MAX_VALUE;
		
		for (Location l : g.getMap().getPlayerSpawns(g.getWorld())){
			if (l.getBlockX() > maxX) maxX = l.getBlockX();
			if (l.getBlockX() < minX) minX = l.getBlockX();
			if (l.getBlockZ() > maxZ) maxZ = l.getBlockZ();
			if (l.getBlockZ() < minZ) minZ = l.getBlockZ();
		}
		final int maxX2 = maxX + 10;
		final int minX2 = minX - 10;
		final int maxZ2 = maxZ + 10;
		final int minZ2 = minZ - 10;
		
		g.registerEventRunnable(new BukkitRunnable() {
			int t = 35;
			@Override
			public void run() {
				if (t <= 0) g.cancelEventRunnable(this);
				Location l = new Location(g.getWorld(), minX2 + Math.random() *(maxX2 - minX2), 240, minZ2 + Math.random() *(maxZ2 - minZ2));
				new SmashEntity(g, l, SmashEntityType.SPIDER, 5);
		        t--;
				
			}
		}, 0, 2);
		
		return true;
	}

}
