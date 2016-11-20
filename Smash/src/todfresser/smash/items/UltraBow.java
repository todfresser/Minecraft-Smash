package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class UltraBow extends SmashItem{

	@Override
	public String getDisplayName() {
		return ChatColor.RED + "UltraBow";
	}

	@Override
	public Material getType() {
		return Material.BOW;
	}

	@Override
	public int getmaxItemUses() {
		return 3;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(force/2 + 1.0f));
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 3;
			@Override
			public void run() {
				if (i==0){
					playerdata.canUseItem = true;
					playerdata.cancelItemRunnable(this);
				}
				player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(force/2 + 1.0f));
				i--;
				
			}
		}, 1, 1);
		
	}

	@Override
	public int getSpawnChance() {
		return 8;
	}
}
