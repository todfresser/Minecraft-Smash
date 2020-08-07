package todfresser.smash.map.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import todfresser.smash.extrafunctions.MaterialUtils;
import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class InteractEvent implements Listener{
	
	@EventHandler
	public void onItemClick(PlayerInteractEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.getIngamePlayers().contains(e.getPlayer().getUniqueId())){
				if (g.getGameState().equals(GameState.Running)){
					if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
						if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
							if (MaterialUtils.equalsClickableBlock(e.getClickedBlock().getType())){
								e.setCancelled(false);
								return;
							}
						}
						if (e.getItem() != null && e.getItem().getType() != Material.AIR){					
							g.getPlayerData(e.getPlayer()).OnInteract(e.getPlayer(), g);
							Material m = e.getItem().getType();
							if (m.equals(Material.FISHING_ROD)){
								if (g.getPlayerData(e.getPlayer()).canUseItem){
									if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
										if (MaterialUtils.equalsNonClickableBlock(e.getClickedBlock().getType())) e.setCancelled(true);
										return;
									}
									e.setCancelled(false);
									return;
								}
							}
							if (m.equals(Material.BOW)){
								if (g.getPlayerData(e.getPlayer()).canUseItem){
									if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
										if (MaterialUtils.equalsNonClickableBlock(e.getClickedBlock().getType())) e.setCancelled(true);
										return;
									}
									e.getPlayer().getInventory().setItem(10, new ItemStack(Material.ARROW));
									e.setCancelled(false);
									return;
								}
							}
						}
						e.setCancelled(true);
						return;
					}
				}
				e.setCancelled(true);
				return;
			}
		}
	}

}
