package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import todfresser.smash.map.Game;

public class PlayerQuit implements Listener{
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				g.removePlayer(e.getPlayer());
				return;
			}
		}
	}
}
