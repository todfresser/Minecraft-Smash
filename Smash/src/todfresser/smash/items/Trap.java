package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Trap extends SmashItem{

	@Override
	public String getDisplayName() {
		return ChatColor.YELLOW + "Trap";
	}

	@Override
	public Material getType() {
		return Material.IRON_TRAPDOOR;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 16;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item trap = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.IRON_TRAPDOOR));	
		trap.setVelocity(player.getLocation().getDirection().multiply(0.8f));
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 30;
			@Override
			public void run() {
				if (i <= 0 || trap.isDead()){
					trap.remove();
					playerdata.cancelItemRunnable(this);
					return;
				}
				i--;
				Location l = trap.getLocation().subtract(0, 1, 0);
				if (!l.getBlock().getType().equals(Material.AIR) && trap.getLocation().getBlock().getType().equals(Material.AIR)){
					trap.remove();
					Block b = trap.getLocation().getBlock();
					b.setType(Material.IRON_TRAPDOOR);
					playerdata.registerItemRunnable(new BukkitRunnable() {
						int i = 1000;
						@Override
						public void run() {
							if (i == 1000) PlayerFunctions.sendTitle(player, 10, 5, 10, "", "Trap aktiviert!");
							if (i <= 0 || !b.getType().equals(Material.IRON_TRAPDOOR)){
								b.setType(Material.AIR);
								playerdata.cancelItemRunnable(this);
								return;
							}
							i--;
							for (Entity e : b.getLocation().getWorld().getNearbyEntities(b.getLocation(), 0.8, 1, 0.8)){
								if (e instanceof Player){
									PlayerFunctions.playOutDamage(game, (Player) e, player, new Vector(Math.random() * 2.0D - 1, 1.5, Math.random() * 2.0D - 1), 50, true);
									b.setType(Material.AIR);
									playerdata.cancelItemRunnable(this);
									return;
								}
							}
						}
					}, 18, 2);
					playerdata.cancelItemRunnable(this);
					return;
				}
			}
		}, 0, 2);
		playerdata.canUseItem = true;
		return true;
	}

}
