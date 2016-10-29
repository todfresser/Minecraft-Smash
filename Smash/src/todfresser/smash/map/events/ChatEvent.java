package todfresser.smash.map.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import todfresser.smash.map.Game;
import todfresser.smash.map.PlayerType;

import org.bukkit.event.Listener;

public class ChatEvent implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e){
		for (Game g : Game.getrunningGames()){
			if (g.containsPlayer(e.getPlayer())){
				if (g.getPlayerData(e.getPlayer()).getType().equals(PlayerType.Ingame)){
					e.setCancelled(true);
					g.sendlocalMessage(ChatColor.GOLD + ">>" + ChatColor.BLUE + e.getPlayer().getName() + ChatColor.GOLD + ": " + ChatColor.GRAY + e.getMessage());
				}else{
					e.setCancelled(true);
				}
				return;
			}
		}
		List<UUID> players = new ArrayList<>();
		for (Game g : Game.getrunningGames()){
			for (UUID id : g.getAllPlayers()){
					players.add(id);
			}
		}
		Iterator<Player> i = e.getRecipients().iterator();
		Player current = null;
		while(i.hasNext()){
			current = i.next();
			if (players.contains(current.getUniqueId())){
				i.remove();
			}
		}
		e.setCancelled(false);
		players.clear();
		return;
	}
}
