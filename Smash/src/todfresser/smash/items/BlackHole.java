package todfresser.smash.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class BlackHole extends SmashItem{

	@Override
	public String getDisplayName() {
		
		return "§8BlackHole";
	}

	@Override
	public Material getType() {
		
		return Material.COAL;
	}

	@Override
	public int getmaxItemUses() {
		
		return 1;
	}

	@Override
	public int getSpawnChance() {
		
		return 12;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item blackhole = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.COAL));
	    blackhole.setVelocity(player.getLocation().getDirection().multiply(1.2D));
	    playerdata.registerItemRunnable(new BukkitRunnable() {
		    List<Entity> entitys = new ArrayList<>();
		    Iterator<Entity> it;
	    	//Location l = null;
			int i = 50;
			//int blocks = 0;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				//if (l == null) l = blackhole.getLocation().clone();
				if (i == 50) blackhole.remove();
				if (i <= 0){
					for (Entity e : entitys){
						if (e instanceof Player){
							if (game.getIngamePlayers().contains(e.getUniqueId())){
								PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(Math.random()*180 - 90 , 2).multiply(3), 5, false);
							}
						}else if (!e.isDead()){
							e.setVelocity(VectorFunctions.getStandardVector(Math.random()*180 - 90 , 5));
							e.remove();
						}
					}
					entitys.clear();
					playerdata.cancelItemRunnable(this);
					return;
				}
				for (Entity e : blackhole.getLocation().getWorld().getNearbyEntities(blackhole.getLocation(), 4, 4, 4)){
					if (e instanceof Player){
						if (!entitys.contains(e)) entitys.add(e);
					}
				}
				//if (blocks <= 20){
					double distance = 5;
					Block b = null;
						for (int x = -5; x <=5; x++){
							for (int y = -5; y <=5; y++){
								for (int z = -5; z <=5; z++){
									Block block = blackhole.getLocation().getWorld().getBlockAt(blackhole.getLocation().getBlockX() + x, blackhole.getLocation().getBlockY() + y, blackhole.getLocation().getBlockZ() + z);
									if (!block.getType().equals(Material.AIR)){
										if (block.getLocation().distance(blackhole.getLocation()) < distance){
											b = block;
											distance = block.getLocation().distance(blackhole.getLocation());
										}
									}
								}
							}
						}
						if (b != null){
							FallingBlock falling = blackhole.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
							falling.setGravity(false);
							falling.setHurtEntities(false);
							falling.setInvulnerable(true);
							falling.setDropItem(false);
							b.setType(Material.AIR);
							entitys.add(falling);
							b = null;
						}
						//blocks++;
				//}
				it = entitys.iterator();
				while (it.hasNext()){
					Entity e = it.next();
					if (e instanceof Player){
						if (game.getIngamePlayers().contains(e.getUniqueId())){
							if (e.getLocation().distance(blackhole.getLocation()) < 10 || e.getLocation().getY() > 1){
								PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getVectorbetweenLocations(e.getLocation(), new Location(blackhole.getLocation().getWorld(), blackhole.getLocation().getX() + Math.random()*2 -1, blackhole.getLocation().getY() + Math.random(), blackhole.getLocation().getZ() + Math.random()*2 -1)).normalize().multiply(0.7), 1, false);
							}
						}
					}else e.setVelocity(VectorFunctions.getVectorbetweenLocations(e.getLocation(), new Location(blackhole.getLocation().getWorld(), blackhole.getLocation().getX() + Math.random()*2 -1, blackhole.getLocation().getY() + Math.random(), blackhole.getLocation().getZ() + Math.random()*2 -1)).normalize());
				}
				/*for (Entity e : entitys){
					if (e instanceof Player){
						if (e.getLocation().distance(blackhole.getLocation()) < 10 || e.getLocation().getY() > 1){
							PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getVectorbetweenLocations(e.getLocation(), new Location(blackhole.getLocation().getWorld(), blackhole.getLocation().getX() + Math.random()*2 -1, blackhole.getLocation().getY() + 0.5, blackhole.getLocation().getZ() + Math.random()*2 -1)).normalize(), 1);
						}else entitys.remove(e);
					}else e.setVelocity(VectorFunctions.getVectorbetweenLocations(e.getLocation(), new Location(blackhole.getLocation().getWorld(), blackhole.getLocation().getX() + Math.random()*2 -1, blackhole.getLocation().getY() + 0.5, blackhole.getLocation().getZ() + Math.random()*2 -1)).normalize());
				}*/
				
				i--;
			}
		}, 50, 3);
	    playerdata.canUseItem = true;
	    return true;
		
	}
	
}
