package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import todfresser.smash.map.Game;

public class Explode implements Listener{
	
	@EventHandler
	public void BlockExplode(BlockExplodeEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.getWorld().getName().equals(e.getBlock().getWorld().getName())){
				e.setYield(0);
				return;
			}
		}
	}
	
	@EventHandler
	public void EntityExplode(EntityExplodeEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.getWorld().getName().equals(e.getEntity().getWorld().getName())){
				e.setYield(0);
				return;
			}
		}
	}
}
