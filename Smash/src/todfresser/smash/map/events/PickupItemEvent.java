package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import todfresser.smash.items.ItemManager;
import todfresser.smash.map.Game;

public class PickupItemEvent implements Listener{
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				if (ItemManager.getItemDataID(e.getItem().getItemStack().getType(), e.getItem().getItemStack().getItemMeta().getDisplayName()) == 0){
					//e.getItem().remove();
					e.setCancelled(true);
					return;
				}
				if (!g.getPlayerData(e.getPlayer()).hasItem()){
					g.getPlayerData(e.getPlayer()).changeItem(ItemManager.getItemDataID(e.getItem().getItemStack().getType(), e.getItem().getItemStack().getItemMeta().getDisplayName()));
					ItemStack item = e.getItem().getItemStack();
					item.setAmount(1);
					e.getPlayer().getInventory().setItem(0, item);
					e.setCancelled(true);
					e.getItem().remove();
					e.getPlayer().updateInventory();
					return;
				}
				e.setCancelled(true);
				return;
			}
		}
	}

}
