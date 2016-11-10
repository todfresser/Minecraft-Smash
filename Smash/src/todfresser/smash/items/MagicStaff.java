package todfresser.smash.items;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class MagicStaff implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§5M§3a§5g§3i§5c§3S§5t§3a§5f§3f";
	}

	@Override
	public Material getType() {
		return Material.STICK;
	}

	@Override
	public List<String> getLore() {
		return null;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
		playerdata.canUseItem = true;
		playerdata.registerItemRunnable(new BukkitRunnable() {
			Location loc = whoclicked.getLocation().add(0, 1, 0);
			Vector direction = loc.getDirection().normalize();
			double t = 0.25;
			@Override
			public void run() {
				if (t > 30) playerdata.cancelItemRunnable(this);
				t = t + 0.25;
				double x = direction.getX() * t;
				double y = direction.getY() * t;
				double z = direction.getZ() * t;
				loc.add(x, y, z);
				loc.getWorld().spigot().playEffect(loc, Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0, 1, 40);
				for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
					if (e.getType().equals(EntityType.PLAYER)){
						if (!((Player)e).getUniqueId().equals(whoclicked.getUniqueId())){
							if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, whoclicked, VectorFunctions.getStandardVector(loc.getYaw(), 0.5), 5);
								((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 2));
								playerdata.cancelItemRunnable(this);
								return;
							}
						}
					}
				}
				t = t + 0.25;
				x = direction.getX() * t;
				y = direction.getY() * t;
				z = direction.getZ() * t;
				loc.add(x, y, z);
				loc.getWorld().spigot().playEffect(loc, Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0, 1, 40);
				for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
					if (e.getType().equals(EntityType.PLAYER)){
						if (!((Player)e).getUniqueId().equals(whoclicked.getUniqueId())){
							if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, whoclicked, VectorFunctions.getStandardVector(loc.getYaw(), 1), 5);
								((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 160, 2));
								playerdata.cancelItemRunnable(this);
								return;
							}
						}
					}
				}
			}
		}, 0, 1);
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		
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
