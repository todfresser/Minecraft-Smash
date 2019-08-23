package todfresser.smash.map.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
							if (e.getClickedBlock().getType().equals(Material.SPRUCE_DOOR) ||e.getClickedBlock().getType().equals(Material.ACACIA_DOOR) || e.getClickedBlock().getType().equals(Material.BIRCH_DOOR) || e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR) || e.getClickedBlock().getType().equals(Material.JUNGLE_DOOR) || e.getClickedBlock().getType().equals(Material.LEGACY_WOOD_DOOR) || e.getClickedBlock().getType().equals(Material.ACACIA_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.BIRCH_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.DARK_OAK_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.LEGACY_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.JUNGLE_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.SPRUCE_FENCE_GATE)){
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
										Material t = e.getClickedBlock().getType();
										if (t.equals(Material.LEGACY_WORKBENCH) || t.equals(Material.ANVIL) || t.equals(Material.CHEST) || t.equals(Material.TRAPPED_CHEST) || t.equals(Material.ENDER_CHEST) || t.equals(Material.FURNACE)) e.setCancelled(true);
										return;
									}
									e.setCancelled(false);
									return;
								}
							}
							if (m.equals(Material.BOW)){
								if (g.getPlayerData(e.getPlayer()).canUseItem == true){
									if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
										Material t = e.getClickedBlock().getType();
										if (t.equals(Material.LEGACY_WORKBENCH) || t.equals(Material.ANVIL) || t.equals(Material.CHEST) || t.equals(Material.TRAPPED_CHEST) || t.equals(Material.ENDER_CHEST) || t.equals(Material.FURNACE)) e.setCancelled(true);
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
