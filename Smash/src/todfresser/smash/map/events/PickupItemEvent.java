package todfresser.smash.map.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.ItemManager;
import todfresser.smash.map.Game;

public class PickupItemEvent implements Listener{


	
	@EventHandler
	public void onPickup(EntityPickupItemEvent e){
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					if (ItemManager.getItemDataID(e.getItem().getItemStack().getType(), e.getItem().getItemStack().getItemMeta().getDisplayName()) == 0){
						e.setCancelled(true);
						return;
					}
					if (!g.getPlayerData(p).hasItem()){
						PlayerFunctions.sendActionBar(p, "");
						e.setCancelled(true);
						PlayerFunctions.changeItem(p, g, ItemManager.getItemDataID(e.getItem().getItemStack().getType(), e.getItem().getItemStack().getItemMeta().getDisplayName()));
						e.getItem().remove();
						return;
					}else{
						PlayerFunctions.sendActionBar(p, ItemManager.getItemData(ItemManager.getItemDataID(e.getItem().getItemStack().getType(), e.getItem().getItemStack().getItemMeta().getDisplayName())).getDisplayName());
					}
					e.setCancelled(true);
					return;
				}
			}
		}
	}

}
