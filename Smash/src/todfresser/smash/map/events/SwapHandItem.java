package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import todfresser.smash.map.Game;

public class SwapHandItem implements Listener{
	
	@EventHandler
	public void onSwap(PlayerSwapHandItemsEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.getIngamePlayers().contains(e.getPlayer().getUniqueId())){
				e.setCancelled(true);
				if (e.getPlayer().getInventory().getHeldItemSlot() != 0){
					e.getPlayer().getInventory().setHeldItemSlot(0);
				}
				return;
			}
		}
	}
	
	@EventHandler
	public void onItemHeld(PlayerItemHeldEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.getIngamePlayers().contains(e.getPlayer().getUniqueId())){
				if (e.getPreviousSlot() != 0){
					e.getPlayer().getInventory().setHeldItemSlot(0);
				}
				e.setCancelled(true);
				return;
			}
		}
	}

}
