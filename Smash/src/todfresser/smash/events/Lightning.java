package todfresser.smash.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.map.Game;

public class Lightning extends SmashEvent{

	@Override
	public String getDisplayName() {
		return ChatColor.YELLOW + "Tödliche Blitze!";
	}

	@Override
	public Material getType() {
		return Material.GOLD_NUGGET;
	}

	@Override
	public int getChance() {
		return 15;
	}
	
	@Override
	public int getTime() {
		return 6;
	}

	@Override
	public boolean perform(Game g) {
		g.registerEventRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				for (UUID id : g.getIngamePlayers()){
					Player p = Bukkit.getPlayer(id);
					boolean canbehit = true;
					for (int y = p.getEyeLocation().getBlockY(); y < 256; y++){
						if (!g.getWorld().getBlockAt(p.getLocation().getBlockX(), y, p.getLocation().getBlockZ()).getType().equals(Material.AIR)){
							canbehit = false;
							break;
						}
					}
					if (canbehit){
						g.getWorld().strikeLightning(p.getLocation());
						PlayerFunctions.playOutDamage(g, p, 4, false);
					}
				}
				
			}
		}, 0, 6);
		return true;
	}

}
