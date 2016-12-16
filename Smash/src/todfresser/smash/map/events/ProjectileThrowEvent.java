package todfresser.smash.map.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import todfresser.smash.map.Game;

public class ProjectileThrowEvent implements Listener{
	
	@EventHandler
	public void onThrow(ProjectileLaunchEvent e){
		for (Game g : Game.getrunningGames()){
			if (e.getEntity().getWorld().getName().equals(g.getWorld().getName())){
				if (!e.getEntityType().equals(EntityType.SMALL_FIREBALL) && !e.getEntityType().equals(EntityType.FISHING_HOOK) && !e.getEntityType().equals(EntityType.ARROW) && !e.getEntityType().equals(EntityType.SPECTRAL_ARROW) && !e.getEntityType().equals(EntityType.TIPPED_ARROW) && !e.getEntityType().equals(EntityType.SNOWBALL)) e.setCancelled(true);
				return;
			}
		}
	}
}
