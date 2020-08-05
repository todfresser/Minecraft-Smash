package todfresser.smash.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class MagicStaff extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§5M§3a§5g§3i§5c§3S§5t§3a§5f§3f";
	}

	@Override
	public Material getType() {
		return Material.STICK;
	}
	
	@Override
	public int getmaxItemUses() {
		return 4;
	}

	@Override
	public int getSpawnChance() {
		return 20;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		playerdata.canUseItem = true;
		playerdata.registerItemRunnable(new BukkitRunnable() {
			Location loc = whoclicked.getLocation().add(0, 1, 0);
			Vector direction = loc.getDirection().normalize();
			double t = 0.25;
			@Override
			public void run() {
				if (t > 20) playerdata.cancelItemRunnable(this);
				t = t + 0.25;
				double x = direction.getX() * 1.5;
				double y = direction.getY() * 1.5;
				double z = direction.getZ() * 1.5;
				loc.add(x, y, z);
				ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 40);
				for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
					if (e.getType().equals(EntityType.PLAYER)){
						if (!((Player)e).getUniqueId().equals(whoclicked.getUniqueId())){
							if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, whoclicked, VectorFunctions.getStandardVector(loc.getYaw(), 0.5), 5, false);
								((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 2));
								playerdata.cancelItemRunnable(this);
								return;
							}
						}
					}
				}
				t = t + 0.25;
				x = direction.getX() * 1.5;
				y = direction.getY() * 1.5;
				z = direction.getZ() * 1.5;
				loc.add(x, y, z);
				ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 40);
				for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
					if (e.getType().equals(EntityType.PLAYER)){
						if (!((Player)e).getUniqueId().equals(whoclicked.getUniqueId())){
							if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, whoclicked, VectorFunctions.getStandardVector(loc.getYaw(), 1), 5, false);
								((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 2));
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
