package todfresser.smash.map.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;

public class PlayerShootBow implements Listener{
	
	@EventHandler
	public void onPlayerBowShoot(EntityShootBowEvent e){
		if (e.getEntity().getType().equals(EntityType.PLAYER)){
			Player p = (Player) e.getEntity();
			for (Game g : Game.getrunningGames()){
				if (g.getIngamePlayers().contains(p.getUniqueId())){
					if (g.getGameState().equals(GameState.Running)){
						g.getPlayerData(p).OnBowShootEvent(p, e.getForce(), g);
					}
					e.setCancelled(true);
					return;
				}
			}
		}
	}

}
