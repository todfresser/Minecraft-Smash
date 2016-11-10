package todfresser.smash.map.events;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class HookEvent implements Listener{
	
	@EventHandler
	public void onHookPlayer(PlayerFishEvent e){
		for (Game g : Game.getrunningGames()){
			if (e.getPlayer().getWorld().getName().equals(g.getWorld().getName())){
	            Player player = e.getPlayer();
	            if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
				if (e.getState().equals(State.FAILED_ATTEMPT) || e.getState().equals(State.IN_GROUND) || e.getState().equals(State.CAUGHT_ENTITY)){
					if (g.getIngamePlayers().contains(player.getUniqueId())){
						e.setCancelled(true);
						g.getPlayerData(player).OnHookPlayerEvent(player, e.getHook().getLocation(), g);
						e.getHook().remove();
						System.out.println("hook");
						return;
					}
				}
				/*if (e.getCaught() != null){
					if (e.getCaught().getType().equals(EntityType.PLAYER)) {
						Player target = (Player) e.getCaught();
			            if (e.getState().equals(State.CAUGHT_ENTITY)){
			    			if (g.containsPlayer(player)){
			    				e.setCancelled(true);
			    	           	e.getHook().remove();
			    				if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
			    				if (g.containsPlayer(target)){
			    					g.getPlayerData(player).OnHookPlayerEvent(player, target, g);
			    				}
			    				return;
			    			}
			            }
					}
				}*/
				return;
			}
		}
		/*for (Game g : Game.getrunningGames()){
			if (e.getPlayer().getWorld().getName().equals(g.getWorld().getName())){
				if (e.getState().equals(State.FAILED_ATTEMPT) || e.getState().equals(State.IN_GROUND)){
					e.getHook().remove();
					return;
				}
				if (e.getState().equals(State.CAUGHT_ENTITY)){
					if (e.getCaught().getType().equals(EntityType.PLAYER)){
						e.setCancelled(true);
					}
				}
				if (e.getCaught() != null){
					if (e.getCaught().getType().equals(EntityType.PLAYER)) {
						Player target = (Player) e.getCaught();
			            Player player = e.getPlayer();
			            if (e.getState().equals(State.CAUGHT_ENTITY)){
			    			if (g.containsPlayer(player)){
			    				e.setCancelled(true);
			    	           	e.getHook().remove();
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
				return;
			}
		}*/
		/*System.out.println("hook:" + e.getCaught().toString());
		if (e.getCaught() == null) return;
		if (e.getCaught().getType().equals(EntityType.PLAYER)) {
			Player target = (Player) e.getCaught();
            Player player = e.getPlayer();
            if (e.getState().equals(State.CAUGHT_ENTITY)){
                for (Game g : Game.getrunningGames()){
    				if (g.containsPlayer(player)){
    					e.setCancelled(true);
    	            	e.getHook().remove();
    					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
    					if (g.containsPlayer(target)){
    						g.getPlayerData(target).setLastDamager(player.getUniqueId());
    						g.getPlayerData(player).OnHookPlayerEvent(player, target, g);
    					}
    					return;
    				}
                }
            }
		}*/
	}
}
