package todfresser.smash.basic.events;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.basic.events.main.SmashEvent;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.particles.ParticleEffect;

public class Tornado extends SmashEvent{

	@Override
	public String getDisplayName() {
		return ChatColor.GOLD + "Tornado!";
	}

	@Override
	public Material getType() {
		return Material.HOPPER;
	}

	@Override
	public int getChance() {
		return 10;
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
		final int maxX2 = maxX;
		final int minX2 = minX;
		final int maxZ2 = maxZ;
		final int minZ2 = minZ;
		
		Random r = new Random();
		int max_height = 15;
		double max_radius = 7;
		int lines = 4;
		double height_increasement = 0.8;
		double radius_increasement = max_radius / max_height;
		g.registerEventRunnable(new BukkitRunnable() {
			Location location = g.getMap().getItemSpawns(g.getWorld()).get(r.nextInt(g.getMap().getItemSpawns(g.getWorld()).size()));
			int angle = 0;
			@Override
			public void run() {
				Location current;
				do{ 
					current = location.clone();
					current.add(Math.random()* 2 - 1,0 , Math.random()* 2 - 1);
				}while (current.getBlockX() < minX2 || current.getBlockX() > maxX2 || current.getBlockZ() < minZ2 || current.getBlockZ() > maxZ2);
				location = current;
				for (int l = 0; l < lines; l++) {
					for (double y = 0; y < max_height; y+=height_increasement ) {
						double radius = y * radius_increasement;
						double x = Math.cos(Math.toRadians(360/lines*l + y*25 - angle)) * radius;
				        double z = Math.sin(Math.toRadians(360/lines*l + y*25 - angle)) * radius;
				        ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, location.clone().add(x, y, z), 255);
					}
				}
				for (int y = 0; y < max_height; y++){
					double radius = y * radius_increasement;
					for (Entity e : g.getWorld().getNearbyEntities(location.clone().add(0,y,0), radius, 1, radius)){
						if (e instanceof Player && g.getIngamePlayers().contains(e.getUniqueId())){
							Vector v = new Vector(Math.random()*1 - 0.5, 0.2 * (y+2)/2, Math.random()*1 - 0.5);
							PlayerFunctions.playOutDamage(g, (Player) e, v, 2, true);
							PlayerFunctions.setAllowFlight((Player) e);
						}
					}
				}
				angle++;
			}
		}, 0, 1);
		return true;
	}

}
