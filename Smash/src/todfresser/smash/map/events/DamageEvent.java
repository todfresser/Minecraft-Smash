package todfresser.smash.map.events;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

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
					e.setCancelled(true);
					return;
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
					PlayerFunctions.playOutDamage(g, p, damager, arrow.getVelocity().setY(0.3).multiply(1.3f), 5, false);
					arrow.remove();
					return;
				}
			}
		}
		if (e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.SNOWBALL)){
			Player p = (Player) e.getEntity();
			Snowball ball = (Snowball) e.getDamager();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						ball.remove();
						return;
					}
					Player damager = (Player) ball.getShooter();
					if (p.getName().equals(damager.getName())){
						ball.remove();
						return;
					}
					PlayerFunctions.playOutDamage(g, p, damager, ball.getVelocity().normalize().setY(0.3), 3, false);
					ball.remove();
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
					if (g.containsPlayer(damager) && g.getPlayerData(damager).canHit){
						g.getPlayerData(p).setLastDamager(damager.getUniqueId());
						if (!g.getPlayerData(damager).OnPlayerHitPlayer(damager, p, g)){
							PlayerFunctions.playOutDamage(g, p, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), 0.45), 1, true);
						}
						g.getPlayerData(damager).canHit = false;
						g.getPlayerData(damager).registerItemRunnable(new BukkitRunnable() {
							
							@Override
							public void run() {
								g.getPlayerData(damager).canHit = true;
								g.getPlayerData(damager.getUniqueId()).cancelItemRunnable(this);
							}
						}, 8, 0);
						return;
					}
				}
			}
		}
	}
}
