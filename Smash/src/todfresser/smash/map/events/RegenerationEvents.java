package todfresser.smash.map.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import todfresser.smash.map.Game;

public class RegenerationEvents implements Listener{
	
	@EventHandler
	public void onRegain(EntityRegainHealthEvent e){
		if (e.getEntityType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e){
		if (e.isCancelled()) return;
		if (e.getEntityType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
				}
			}
		}
	}

}
