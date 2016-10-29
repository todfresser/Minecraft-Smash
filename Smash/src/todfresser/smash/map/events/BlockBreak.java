package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import todfresser.smash.map.Game;

public class BlockBreak implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				e.setCancelled(true);
				return;
			}
		}
	}

}
