package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class RocketLauncher extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§cRocket§8Launcher";
	}

	@Override
	public Material getType() {
		return Material.DIAMOND_PICKAXE;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
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
        player.setVelocity(VectorFunctions.getStandardVector(player.getLocation().getYaw() - 180, 1));
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int blocks = 0;
			Location loc = player.getLocation().add(0, 1, 0);
			Vector direction = loc.getDirection().normalize();
			double t = 0.25;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (int i = 0; i < 3; i++){
					if (t > 10) playerdata.cancelItemRunnable(this);
					t = t + 0.2;
					double px = direction.getX() * 1;
					double py = direction.getY() * 1;
					double pz = direction.getZ() * 1;
					loc.add(px, py, pz);
					ParticleEffect.EXPLOSION_NORMAL.display(0f, 0f, 0f, 0.001f, 1, loc, 50);
					if (!loc.getBlock().getType().equals(Material.AIR)){
						List<Entity> entitys = new ArrayList<>();
						for (Entity e : loc.getWorld().getNearbyEntities(loc, 3, 3, 3)){
							if (e instanceof Player){
								if (!entitys.contains(e)) entitys.add(e);
							}
						}
						while (blocks < 16){
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
								//falling.setGravity(false);
								falling.setHurtEntities(false);
								falling.setInvulnerable(true);
								falling.setDropItem(false);
								b.setType(Material.AIR);
								entitys.add(falling);
							}
						}
						ParticleEffect.EXPLOSION_LARGE.display(1.5f, 1.5f, 1.5f, 0, 30, loc, 60);
						for (Entity e : entitys){
							if (e instanceof Player){
								if (game.getIngamePlayers().contains(e.getUniqueId())){
									PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(loc.getYaw(), 2).multiply(2), 40, false);
								}
							}else if (!e.isDead()){
								e.setVelocity(VectorFunctions.getStandardVector(loc.getYaw() + Math.random()*10 - 5 , 2).multiply(2));
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
						}, 30, 0);
						playerdata.cancelItemRunnable(this);
						return;
					}
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
		return true;
		
	}
}
