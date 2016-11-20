package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Cloud implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "Wolke der Rettung";
	}

	@Override
	public Material getType() {
		return Material.STAINED_GLASS;
	}

	@Override
	public byte getSubID() {
		return 0;
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 11;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}

	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		return false;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}

	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player player, Game game) {
		Location l = player.getLocation();
		int y = l.getBlockY() - 1;
		List<Block> blocks = new ArrayList<>();
		for (int x = -1; x <= 1; x++){
			for (int z = -1; z <= 1; z++){
				Block b = l.getWorld().getBlockAt(l.getBlockX() + x, y, l.getBlockZ() + z);
				if (b.getType().equals(Material.AIR)){
					b.setType(Material.STAINED_GLASS);
					if (x == 0 && z == 0){
						Location loc = b.getLocation().clone().add(0.5, 1, 0.5);
						loc.setYaw(player.getLocation().getYaw());
						loc.setPitch(player.getLocation().getPitch());
						player.teleport(loc);
					}
					blocks.add(b);
				}
			}
		}
		playerdata.registerItemRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Block b : blocks){
					if (b.getType().equals(Material.STAINED_GLASS)){
						b.setType(Material.AIR);
					}
				}
				blocks.clear();
				playerdata.cancelItemRunnable(this);
			}
		}, 80, 0);
		playerdata.canUseItem = true;
		
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
	}
	
}
