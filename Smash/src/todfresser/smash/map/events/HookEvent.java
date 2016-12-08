package todfresser.smash.map.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class HookEvent implements Listener{
	
	@EventHandler
	public void onHookPlayer(PlayerFishEvent e){
		for (Game g : Game.getrunningGames()){
			if (e.getPlayer().getWorld().getName().equals(g.getWorld().getName())){
	            Player player = e.getPlayer();
	            if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
				if (g.getIngamePlayers().contains(player.getUniqueId())){
					if (!e.getState().equals(State.FISHING)){
						e.setCancelled(true);
						e.getHook().remove();
						if (e.getState().equals(State.CAUGHT_ENTITY) && e.getCaught() instanceof Player){
							Player target = (Player) e.getCaught();
							if (g.containsPlayer(target)){
		    					g.getPlayerData(player).OnHookPlayerEvent(player, target, g);
		    				}
						}
					}else{
						g.getPlayerData(player).registerItemRunnable(new BukkitRunnable() {
							Projectile hook = e.getHook();
							@Override
							public void run() {
								if (hook.isDead()){
									g.getPlayerData(player).cancelItemRunnable(this);
									return;
								}
								if (player.getLocation().distance(hook.getLocation()) > 15){
									hook.remove();
									g.getPlayerData(player).cancelItemRunnable(this);
									return;
								}
							}
						}, 5, 5);
					}
					return;
				}
			}
		}
	}
	
}
