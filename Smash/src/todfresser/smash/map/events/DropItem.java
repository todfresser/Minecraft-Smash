package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import todfresser.smash.items.main.ItemManager;
import todfresser.smash.map.Game;

public class DropItem implements Listener{
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				if (g.getPlayerData(e.getPlayer()).hasItem()){
					if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ItemManager.getItemData(g.getPlayerData(e.getPlayer()).getItemID()).getDisplayName())){
						if (g.getPlayerData(e.getPlayer()).canChangeItem()) g.getPlayerData(e.getPlayer()).removeItems(e.getPlayer());
					}
				}
				e.getItemDrop().remove();
				return;
			}
		}
	}
}
