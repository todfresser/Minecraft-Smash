package todfresser.smash.items;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class UltraBow implements SmashItemData{

	@Override
	public String getDisplayName() {
		return ChatColor.RED + "UltraBow";
	}

	@Override
	public Material getType() {
		return Material.BOW;
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getmaxItemUses() {
		return 3;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		// TODO Auto-generated method stub
		
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


	@Override
	public byte getSubID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
		// TODO Auto-generated method stub
		
	}
	
}
