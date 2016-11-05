package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import todfresser.smash.map.Game;

public class PhysikEvent implements Listener{
	
	@EventHandler
	public void onPhysik(BlockPhysicsEvent e){
		for (Game g : Game.getrunningGames()){
			if (e.getBlock().getWorld().getName().equals(g.getWorld().getName())){
				e.setCancelled(true);
				return;
			}
		}
	}

}
