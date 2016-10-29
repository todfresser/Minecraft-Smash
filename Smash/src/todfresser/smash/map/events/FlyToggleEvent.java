package todfresser.smash.map.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class FlyToggleEvent implements Listener{
	
	public static List<UUID> cantSmash = new ArrayList<>();
	
	@EventHandler
	public void onFlyToggle(PlayerToggleFlightEvent e){
		if (e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(e.getPlayer())){
					if (g.getGameState().equals(GameState.Running) || g.getGameState().equals(GameState.Ending)){
						if (e.getPlayer().getFoodLevel() >= 5){
							e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() - 5);
							e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_SAND_BREAK, 10, 10);
							e.getPlayer().getLocation().getWorld().spigot().playEffect(e.getPlayer().getLocation().subtract(0, 0.5, 0), Effect.MOBSPAWNER_FLAMES, 1, 1, 0.4f, 0.0f, 0.4f, 0.1f, 4, 20);
							e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.6D).setY(0.6D));
							e.setCancelled(true);
							e.getPlayer().setFlying(false);
							e.getPlayer().setAllowFlight(false);
							return;
						}
					}
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e){
		if (e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(e.getPlayer())){
					if (g.getGameState().equals(GameState.Running) || g.getGameState().equals(GameState.Ending)){
						if (e.getPlayer().isOnGround() == false){
							if (!cantSmash.contains(e.getPlayer().getUniqueId())){
								if (e.getPlayer().getFoodLevel() >= 7){
									e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() - 7);
									e.getPlayer().setVelocity(new Vector(0, -2, 0));
									cantSmash.add(e.getPlayer().getUniqueId());
									for (Entity entity : e.getPlayer().getNearbyEntities(4, 10, 4)){
										if (entity instanceof Player){
											Player near = (Player) entity;
											PlayerFunctions.playOutDamage(g, near, e.getPlayer(), new Vector(Math.random() * 2.0D - 1, 1.3 + g.getPlayerData(near).getDamage()/350, Math.random() * 2.0D - 1), 5);
											//g.getPlayerData(near).addDamage(5);
											//PlayerFunctions.sendDamage(near.getUniqueId(), g);
											//g.getPlayerData(e.getPlayer()).addDamageDone(5);
											//near.setVelocity(new Vector(Math.random() * 2.0D - 1, 1.2 + g.getPlayerData(near).getDamage()/300, Math.random() * 2.0D - 1));
										}
									}
								}
							}
							return;
						}
					}
				}
			}
		}
	}
}
