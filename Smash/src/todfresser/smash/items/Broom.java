package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.map.events.FlyToggleEvent;

public class Broom extends SmashItem{

	@Override
	public String getDisplayName() {
		return ChatColor.GOLD + "Broom";
	}

	@Override
	public Material getType() {
		return Material.TRIPWIRE_HOOK;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 10;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item broom = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.TRIPWIRE_HOOK));
		broom.setVelocity(player.getLocation().getDirection().multiply(2D));
		broom.setPassenger(player);
		if (!FlyToggleEvent.cantSmash.contains(player.getUniqueId())) FlyToggleEvent.cantSmash.add(player.getUniqueId());
		playerdata.registerItemRunnable(new BukkitRunnable() {
			Location l = broom.getLocation();
			@Override
			public void run() {
				if (!player.getEyeLocation().add(0, 0.5, 0).getBlock().getType().equals(Material.AIR) || !player.getEyeLocation().getBlock().getType().equals(Material.AIR) || !player.getLocation().getBlock().getType().equals(Material.AIR) || !broom.getLocation().getBlock().getType().equals(Material.AIR) || broom.getLocation().getBlockY() <= 0 || broom.getPassenger() == null){
					broom.remove();
					//Location l = broom.getLocation();
					l.setYaw(player.getLocation().getYaw());
					l.setPitch(player.getLocation().getPitch());
					player.teleport(l);
					playerdata.cancelItemRunnable(this);
					return;
				}
				l = broom.getLocation();
				broom.setVelocity(broom.getVelocity().setX(Math.cos(((player.getLocation().getYaw()+90) * Math.PI) / 180)).setZ(Math.cos(((player.getLocation().getYaw()) * Math.PI) / 180)));
				
			}
		}, 0, 1);
		playerdata.canUseItem = true;
		return true;
	}

}
