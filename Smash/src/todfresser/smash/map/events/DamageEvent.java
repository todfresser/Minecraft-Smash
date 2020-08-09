package todfresser.smash.map.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;
import todfresser.smash.mobs.SmashEntity;

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
					if (e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION)){
						PlayerFunctions.playOutDamage(g, p, new Vector(Math.random() * 1.0D - 0.5, 0.8, Math.random() * 1.0D - 0.5), (int) Math.floor(e.getDamage()*3), true);
					}
					return;
				}
			}
		}else{
			for (Game g : Game.getrunningGames()){
				if (g.getWorld().getName().equals(e.getEntity().getWorld().getName())){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						e.getEntity().remove();
						return;
					}
					for (SmashEntity entity : g.getEntitys()){
						if (e.getEntity().getUniqueId().equals(entity.getUniqueId())){
							entity.setVelocity(VectorFunctions.getStandardVector(Math.random()*180-90, 0.5).multiply(0.4 * entity.getAttributes().getKnockback()));
							entity.damage(1);
							PlayerFunctions.playDamageAnimation(e.getEntity(), g);
							return;
						}
					}
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
					ProjectileSource source = arrow.getShooter();
					if (source instanceof Player) {
						Player damager = (Player) arrow.getShooter();
						if (!p.getName().equals(damager.getName())){
							PlayerFunctions.playOutDamage(g, p, damager, arrow.getVelocity().setY(0.3).multiply(1.3f), 5, false);
						}
					} else if (source instanceof LivingEntity) {
						for (SmashEntity entity : g.getEntitys()) {
							if (entity.getUniqueId().equals(((LivingEntity) source).getUniqueId())) {
								Player damager = entity.getCreator();
								Vector velocity = arrow.getVelocity().setY(0.3).multiply(entity.getAttributes().getVelocityDamage());
								int damage = entity.getAttributes().getAttackDamage();
								if (damager != null) {
									PlayerFunctions.playOutDamage(g, p, damager, velocity, damage, true);
								} else {
									PlayerFunctions.playOutDamage(g, p, velocity, damage, true);
								}
								break;
							}
						}
					}
					arrow.remove();
					return;
				}
			}
			return;
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
			return;
		}
		if (e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.SMALL_FIREBALL)){
			Player p = (Player) e.getEntity();
			SmallFireball ball = (SmallFireball) e.getDamager();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						ball.remove();
						return;
					}
					if (ball.getShooter() instanceof Player){
						Player damager = (Player) ball.getShooter();
						if (p.getName().equals(damager.getName())){
							ball.remove();
							return;
						}
						PlayerFunctions.playOutDamage(g, p, damager, ball.getVelocity().normalize().setY(0.3).multiply(0.8), 5, true);
						return;
					}else if (ball.getShooter() != null && ball.getShooter() instanceof LivingEntity){
						for (SmashEntity entity : g.getEntitys()){
							if (((LivingEntity)ball.getShooter()).getUniqueId().equals(entity.getUniqueId())){
								Player damager = entity.getCreator();
								Vector velocity = ball.getVelocity().normalize().setY(0.3).multiply(entity.getAttributes().getVelocityDamage());
								int damage = entity.getAttributes().getAttackDamage();
								if (damager != null) {
									PlayerFunctions.playOutDamage(g, p, damager, velocity, damage, true);
								} else {
									PlayerFunctions.playOutDamage(g, p, velocity, damage, true);
								}
								break;
							}
						}
						
					}
					PlayerFunctions.playOutDamage(g, p, ball.getVelocity().normalize().setY(0.3).multiply(0.8), 4, true);
					ball.remove();
					return;
				}
			}
			return;
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
						}, 3, 0);
						return;
					}
				}
			}
			return;
		}
		if (e.getDamager().getType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getDamager();
			Entity creature = e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						creature.remove();
						return;
					}
					for (SmashEntity entity : g.getEntitys()){
						if (creature.getUniqueId().equals(entity.getUniqueId())){
							entity.setVelocity(VectorFunctions.getStandardVector(p.getLocation().getYaw(), 0.45).multiply(entity.getAttributes().getKnockback()));
							entity.damage(1);
							PlayerFunctions.playDamageAnimation(creature, g);
							return;
						}
					}
				}
			}
		}
		if (e.getEntity().getType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			Entity damager = e.getDamager();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					e.setCancelled(true);
					if (g.getGameState().equals(GameState.Lobby) || g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Ending)){
						damager.remove();
						return;
					}
					for (SmashEntity entity : g.getEntitys()){
						if (damager.getUniqueId().equals(entity.getUniqueId())){
							PlayerFunctions.playOutDamage(g, p, damager.getLocation().getDirection().normalize().setY(0.3).multiply(entity.getAttributes().getVelocityDamage()), entity.getAttributes().getAttackDamage(), true);
							return;
						}
					}
					return;
				}
			}
		}
	}

	@EventHandler
	public void onBurnInSunlight(EntityCombustEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			Entity entity = e.getEntity();
			for (Game g : Game.getrunningGames()) {
				if (g.getWorld().getName().equals(entity.getWorld().getName())) {
					for (SmashEntity smashEntity : g.getEntitys()) {
						if (entity.getUniqueId().equals(smashEntity.getUniqueId())) {
							e.setCancelled(true);
							return;
						}
					}
					return;
				}
			}
		}
	}
}
