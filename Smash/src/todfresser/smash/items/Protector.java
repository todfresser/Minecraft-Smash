package todfresser.smash.items;



import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class Protector extends SmashItem{

	private String displayName = ChatColor.DARK_AQUA + "Protector";
	
	@Override
	public String getDisplayName() {
		
		return displayName;
	}

	@Override
	public Material getType() {
		return Material.END_CRYSTAL;
	}

	@Override
	public int getmaxItemUses() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getSpawnChance() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		if (!playerdata.canGetDamage()) return false;
		PlayerFunctions.playOutDamage(game, player, (int) (Math.random()*15), true);
		playerdata.preventDamage();
		BukkitRunnable particles = playerdata.registerItemRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				Location l = player.getLocation().add(0, 0.5, 0);
				ParticleEffect.PORTAL.display(0.5f, 0.5f, 0.5f, 0.5f, 70, l, 40);
				
			}
		}, 0, 1);
		playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {	
				playerdata.allowDamage();
				playerdata.cancelItemRunnable(particles);
				playerdata.cancelItemRunnable(this);
			}
			
		}, 140, 0);
		playerdata.canUseItem = true;
		return true;
	}
	
	
}
