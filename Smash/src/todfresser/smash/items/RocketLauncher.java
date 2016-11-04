package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class RocketLauncher implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§cRocket§8Launcher";
	}

	@Override
	public Material getType() {
		return Material.DIAMOND_PICKAXE;
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
		return 6;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player player, Game game) {
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int blocks = 0;
			Location loc = player.getLocation().add(0, 1, 0);
			Vector direction = loc.getDirection().normalize();
			double t = 0.25;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
					if (t > 40) playerdata.cancelItemRunnable(this);
					t = t + 0.25;
					double px = direction.getX() * t;
					double py = direction.getY() * t;
					double pz = direction.getZ() * t;
					loc.add(px, py, pz);
					loc.getWorld().spigot().playEffect(loc, Effect.FLAME, 0, 0, 0.1f, 0.1f, 0.1f, 0, 8, 40);
					if (!loc.getBlock().getType().equals(Material.AIR)){
						List<Entity> entitys = new ArrayList<>();
						for (Entity e : loc.getWorld().getNearbyEntities(loc, 3, 3, 3)){
							if (e instanceof Player){
								if (!entitys.contains(e)) entitys.add(e);
							}
						}
						while (blocks < 20){
							double distance = 4;
							Block b = null;
							for (int x = -4; x <=4; x++){
								for (int y = -4; y <=4; y++){
									for (int z = -4; z <=4; z++){
										Block block = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
										if (!block.getType().equals(Material.AIR)){
											if (block.getLocation().distance(loc) < distance){
												b = block;
												distance = block.getLocation().distance(loc);
											}
										}
									}
								}
							}
							blocks++;
							if (b != null){
								FallingBlock falling = loc.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
								falling.setGravity(false);
								falling.setHurtEntities(false);
								falling.setInvulnerable(true);
								falling.setDropItem(false);
								b.setType(Material.AIR);
								entitys.add(falling);
							}
						}
						for (Entity e : entitys){
							if (e instanceof Player){
								if (game.getIngamePlayers().contains(e.getUniqueId())){
									PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(loc.getYaw(), 2).multiply(2), 40);
								}
							}else if (!e.isDead()){
								e.setVelocity(VectorFunctions.getStandardVector(loc.getYaw() + Math.random()*10 - 5 , 5));
							}
						}
						playerdata.registerItemRunnable(new BukkitRunnable() {
							
							@Override
							public void run() {
								for (Entity e : entitys){
									if (e.getType() != EntityType.PLAYER){
										e.remove();
									}
								}
								entitys.clear();
								playerdata.cancelItemRunnable(this);
								return;
							}
						}, 50, 0);
						playerdata.cancelItemRunnable(this);
						return;
					}
				}
				/*if (i <= 0){
					item.remove();
					List<Entity> entitys = new ArrayList<>();
					for (Entity e : item.getLocation().getWorld().getNearbyEntities(item.getLocation(), 3, 3, 3)){
						if (e instanceof Player){
							if (!entitys.contains(e)) entitys.add(e);
						}
					}
					while (blocks < 20){
						double distance = 4;
						Block b = null;
						for (int x = -5; x <=5; x++){
							for (int y = -5; y <=5; y++){
								for (int z = -5; z <=5; z++){
									Block block = item.getLocation().getWorld().getBlockAt(item.getLocation().getBlockX() + x, item.getLocation().getBlockY() + y, item.getLocation().getBlockZ() + z);
									if (!block.getType().equals(Material.AIR)){
										if (block.getLocation().distance(item.getLocation()) < distance){
											b = block;
											distance = block.getLocation().distance(item.getLocation());
										}
									}
								}
							}
						}
						blocks++;
						if (b != null){
							FallingBlock falling = item.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
							falling.setGravity(false);
							falling.setHurtEntities(false);
							falling.setInvulnerable(true);
							falling.setDropItem(false);
							b.setType(Material.AIR);
							entitys.add(falling);
						}
					}
					for (Entity e : entitys){
						if (e instanceof Player){
							if (game.getIngamePlayers().contains(e.getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(Math.random()*180 - 90 , 2).multiply(2), 5);
							}
						}else if (!e.isDead()){
							e.setVelocity(VectorFunctions.getStandardVector(Math.random()*180 - 90 , 5));
						}
					}
					playerdata.registerItemRunnable(new BukkitRunnable() {
						
						@Override
						public void run() {
							for (Entity e : entitys){
								if (e.getType() != EntityType.PLAYER){
									e.remove();
								}
							}
							entitys.clear();
							playerdata.cancelItemRunnable(this);
							return;
						}
					}, 50, 0);
					playerdata.cancelItemRunnable(this);
					return;
				}
				i--;
			}*/
		}, 1, 1);
		playerdata.canUseItem = true;
		
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
	}
	
}
