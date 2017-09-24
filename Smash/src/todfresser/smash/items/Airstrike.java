package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class Airstrike extends SmashItem{

	private String displayName = ChatColor.STRIKETHROUGH + "�8Airstrike";
	private int itemClicked = 0;
	
	@Override
	public String getDisplayName() {
 		return displayName;
	}

	@Override
	public Material getType() {
		return Material.FIREWORK;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 4;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
	return true;	
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		itemClicked++;
		Location particleSpawn = player.getLocation().add(0, 0.5, 0);
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			
			float zeit = 0;
			
			@Override
			public void run() {
				ParticleEffect.CLOUD.display(0.5f, 1.3f, 0.5f, 0.3f, 20, particleSpawn, 40);
				if(zeit == 10 || itemClicked != 1) {
					

					for(int i = 0;i < 20; i++) {
						playerdata.registerItemRunnable(new BukkitRunnable() {
							Location airstrikeLocation = player.getLocation().add( Math.random()*10-5, Math.random()*5 + 12.5,  Math.random()*10-5);

							@Override
							public void run() {
								ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, airstrikeLocation.add(0, -0.5, 0), 40);
								for (Entity e : airstrikeLocation.getWorld().getNearbyEntities(airstrikeLocation, 1.5, 1.5, 1.5)){
									if (e.getType().equals(EntityType.PLAYER)){
										if (!((Player)e).getUniqueId().equals(player.getUniqueId())){
											PlayerFunctions.playOutDamage(game, player, 12, false);
										}
									}
								}
								if(airstrikeLocation.getY() <= 0 || !airstrikeLocation.getBlock().getType().equals(Material.AIR)) {
									playerdata.cancelItemRunnable(this);
									return;
								}
							}
						}, 0, 1);	
					}	
					playerdata.cancelItemRunnable(this);
							
				}
				
				zeit += 0.5;
			}
		}, 0, 10);
		if (itemClicked > 0){
		    playerdata.canUseItem = true;
		    return true;
        }
		return false;
	}
}
