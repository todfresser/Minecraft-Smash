package todfresser.smash.map.events;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import todfresser.smash.map.Game;

public class DropPotionEvent implements Listener{
	
	@EventHandler
	public void dropPotion(PotionSplashEvent e){
		if (e.getEntityType().equals(EntityType.PLAYER)){
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(e.getEntity().getUniqueId())){
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
