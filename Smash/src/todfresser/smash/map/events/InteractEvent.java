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
		if (e.getItem() != null && e.getItem().getType() != Material.AIR){
			for (Game g : Game.getrunningGames()){
				if (g.getIngamePlayers().contains(e.getPlayer().getUniqueId())){
					if (g.getGameState().equals(GameState.Running)){
						if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
							g.getPlayerData(e.getPlayer()).OnInteract(e.getAction(), e.getPlayer(), g);
							if (e.getItem().getType().equals(Material.FISHING_ROD)){
								if (g.getPlayerData(e.getPlayer()).canUseItem == true){
									e.setCancelled(false);
									return;
								}
							}
							if (e.getItem().getType().equals(Material.BOW)){
								if (g.getPlayerData(e.getPlayer()).canUseItem == true){
									e.getPlayer().getInventory().setItem(10, new ItemStack(Material.ARROW));
									e.setCancelled(false);
									return;
								}
								//((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutse(0, 9,new net.minecraft.server.v1_10_R1.ItemStack(Block.a)));
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

}
