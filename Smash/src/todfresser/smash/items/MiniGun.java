package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class MiniGun extends SmashItem{
	
	private String displayName = ChatColor.DARK_GRAY + "Minigun";
	private int damage = 2;

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Material getType() {
		return Material.ARMOR_STAND;
	}

	@Override
	public int getmaxItemUses() {
		return 10;
	}

	@Override
	public int getSpawnChance() {
		return 8;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		playerdata.canUseItem = true;
		playerdata.registerItemRunnable(new BukkitRunnable() {
			Location loc = player.getLocation().add(0, 1, 0);
			Vector direction = loc.getDirection().normalize();
			double t = 0.25;
			@Override
			public void run() {
				if (t > 20) playerdata.cancelItemRunnable(this);
				t = t + 0.40;
				double x = direction.getX() * 1.6;
				double y = direction.getY() * 1.6;
				double z = direction.getZ() * 1.6;
				loc.add(x, y, z);
				if (t > 1){
					ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 40);
				}else ParticleEffect.FLAME.display(0.2f, 0.1f, 0.2f, 0.0f, 3, loc, 40);
				for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
					if (e.getType().equals(EntityType.PLAYER)){
						if (!((Player)e).getUniqueId().equals(player.getUniqueId())){
							if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(loc.getYaw(), 0.5).multiply(0.2f), damage, false);
								playerdata.cancelItemRunnable(this);
								return;
							}
						}
					}
				}
			}
		}, 0, 1);
		return true;
	}
	
}
