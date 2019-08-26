package todfresser.smash.basic.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.basic.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Cloud extends SmashItem{

	@Override
	public String getDisplayName() {
		return "Wolke der Rettung";
	}

	@Override
	public Material getType() {
		return Material.LEGACY_STAINED_GLASS;
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
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Location l = player.getLocation();
		int y = l.getBlockY() - 1;
		List<Block> blocks = new ArrayList<>();
		for (int x = -1; x <= 1; x++){
			for (int z = -1; z <= 1; z++){
				Block b = l.getWorld().getBlockAt(l.getBlockX() + x, y, l.getBlockZ() + z);
				if (b.getType().equals(Material.AIR)){
					b.setType(Material.LEGACY_STAINED_GLASS);
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
		if (blocks.size() == 0) return false;
		playerdata.registerItemRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Block b : blocks){
					if (b.getType().equals(Material.LEGACY_STAINED_GLASS)){
						b.setType(Material.AIR);
					}
				}
				blocks.clear();
				playerdata.cancelItemRunnable(this);
			}
		}, 80, 0);
		playerdata.canUseItem = true;
		return true;
		
	}
}
