package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class CommandReprocess implements Listener{
	
	@EventHandler
	public void onCommandReprocess(PlayerCommandPreprocessEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				if (e.getMessage().startsWith("/sm") || e.getMessage().startsWith("/smash") || e.getMessage().startsWith("/Smash")){
					e.setCancelled(false);
					return;
				}
				if (e.getMessage().equals("/l") || e.getMessage().equals("/leave")){
					e.setCancelled(true);
					e.getPlayer().performCommand("sm leave");
					return;
				}
				if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting)){
					if (e.getMessage().equals("/s") || e.getMessage().equals("/start")){
						e.setCancelled(true);
						e.getPlayer().performCommand("sm start");
						return;
					}
					if (e.getMessage().equals("/ss") && e.getPlayer().hasPermission("SMASH.admin")){
						e.setCancelled(true);
						g.start(0);
						return;
					}
				}
				e.getPlayer().sendMessage("Befehle sind während der Runde nicht erlaubt!");
				e.getPlayer().sendMessage("Ausnahme: /l oder /leave zum Verlassen");
				e.setCancelled(true);
				return;
			}
		}
	}
}
