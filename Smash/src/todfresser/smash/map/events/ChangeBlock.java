package todfresser.smash.map.events;

import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import todfresser.smash.map.Game;

public class ChangeBlock implements Listener{
	
	@EventHandler
	public void onEntityBlockChange(EntityChangeBlockEvent e){
		if (e.getEntity() instanceof FallingBlock){
			for (Game g : Game.getrunningGames()){
				if (g.getWorld().getName().equals(e.getEntity().getWorld().getName())){
					e.setCancelled(true);
					return;
				}
			}
			return;
		}
	}

}
