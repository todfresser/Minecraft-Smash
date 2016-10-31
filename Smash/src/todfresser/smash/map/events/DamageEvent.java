package todfresser.smash.map.events;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class DamageEvent implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if (e.getEntityType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (e.getCause().equals(DamageCause.VOID)) return;
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
					if (e.getCause().equals(DamageCause.FALL)) return;
					if (e.getCause().equals(DamageCause.FIRE_TICK)){
						e.setDamage(0);
						e.setCancelled(false);
						return;
					}
					//
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onDamagebyEntity(EntityDamageByEntityEvent e){
		if (e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.ARROW)){
			Player p = (Player) e.getEntity();
			Arrow arrow = (Arrow) e.getDamager();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						arrow.remove();
						return;
					}
					Player damager = (Player) arrow.getShooter();
					if (p.getName().equals(damager.getName())){
						arrow.remove();
						return;
					}
					PlayerFunctions.playOutDamage(g, p, damager, arrow.getVelocity().setY(0.3).multiply(2.0f), 5);
					arrow.remove();
					return;
				}
			}
		}
		if (e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)) return;
					if (g.containsPlayer(damager)){
						g.getPlayerData(p).setLastDamager(damager.getUniqueId());
						if (!g.getPlayerData(damager).OnPlayerHitPlayer(damager, p, g)){
							//PlayerFunctions.playOutDamage(g, p, damager, damager.getLocation().getDirection().setY(0.4).multiply(g.getPlayerData(p).getDamage()/200 + 0.9 + e.getDamage()/5), 1);
							PlayerFunctions.playOutDamage(g, p, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), g.getPlayerData(p), 0.45), 1);
						}
						return;
					}
				}
			}
		}
	}
}
