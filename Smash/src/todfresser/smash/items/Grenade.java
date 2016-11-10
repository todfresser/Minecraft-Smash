package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Grenade implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§7G§fr§7e§fn§7a§fd§7e";
	}

	@Override
	public Material getType() {
		return Material.FIREWORK_CHARGE;
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
		return 19;
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
		float Yaw = player.getLocation().getYaw();
		Item grenade = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.FIREWORK_CHARGE));	
		
		grenade.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int blocks = 0;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				grenade.remove();
				grenade.getWorld().spigot().playEffect(grenade.getLocation(), Effect.EXPLOSION, 0, 0, 2f, 2f, 2f, 0, 300, 100);
				for (Entity e: grenade.getNearbyEntities(4, 4, 4)) {
					if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
						PlayerFunctions.playOutDamage(game, (Player) e,player, VectorFunctions.getStandardVector(Yaw, 0.9).multiply(2), 25);
					}
				}
				Location loc = grenade.getLocation();
				List<Entity> entitys = new ArrayList<>();
				while (blocks < 10){
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
					e.setVelocity(VectorFunctions.getStandardVector(Yaw + (Math.random()*4 -2), 0.9).multiply(2));
				}
				playerdata.registerItemRunnable(new BukkitRunnable() {
					
					@Override
					public void run() {
						for (Entity e : entitys){
							e.remove();
						}
						entitys.clear();
						playerdata.cancelItemRunnable(this);
						return;
					}
				}, 30, 0);
				playerdata.cancelItemRunnable(this);
			}
		}, 40, 0);
		playerdata.canUseItem = true;
		
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
