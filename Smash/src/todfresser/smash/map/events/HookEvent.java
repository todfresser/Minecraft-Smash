package todfresser.smash.map.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class HookEvent implements Listener{
	
	@EventHandler
	public void onHookPlayer(PlayerFishEvent e){
		if (e.getCaught() == null) return;
		if (e.getCaught().getType().equals(EntityType.PLAYER)) {
			Player target = (Player) e.getCaught();
            Player player = e.getPlayer();
            for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(player)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
					if (g.containsPlayer(target)){
						g.getPlayerData(target).setLastDamager(player.getUniqueId());
						g.getPlayerData(player).OnHookPlayerEvent(player, target, g);
					}
					return;
				}
            }
		}
	}
}
