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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Airstrike extends SmashItem{

	private String displayName = ChatColor.STRIKETHROUGH + "§8Airstrike";
	
	@Override
	public String getDisplayName() {
 		return displayName;
	}

	@Override
	public Material getType() {
		return Material.FIREWORK_STAR;
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
    public boolean canChangeItem(SmashPlayerData playerdata) {
        return !playerdata.hasData(444);
    }

    @Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
	    if (playerdata.hasData(444)){
	        if (!((boolean) playerdata.getData(444))){
	            playerdata.setData(444, true);
	            playerdata.canUseItem = true;
	            return true;
            }else return false;
        }else playerdata.setData(444, false);
		Location particleSpawn = player.getLocation().add(0, 0.5, 0);
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
            int itemID = playerdata.getUniqueItemID();
			float zeit = 0;
			
			@Override
			public void run() {
				ParticleEffect.CLOUD.display(0.5f, 1.3f, 0.5f, 0.3f, 20, particleSpawn, 40);
				if(zeit == 10 || (boolean) playerdata.getData(444)) {
					for(int i = 0;i < 20; i++) {
						playerdata.registerItemRunnable(new BukkitRunnable() {
							Location airstrikeLocation = player.getLocation().add( Math.random()*10-5, Math.random()*5 + 12.5,  Math.random()*10-5);
							boolean hit = false;
							@Override
							public void run() {
								ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, airstrikeLocation.add(0, -0.5, 0), 40);
								for (Entity e : airstrikeLocation.getWorld().getNearbyEntities(airstrikeLocation, 1.5, 1.5, 1.5)){
									if (e.getType().equals(EntityType.PLAYER)){
                                        PlayerFunctions.playOutDamage(game, (Player)e, 4, false);
                                        hit = true;
									}
								}
								if(airstrikeLocation.getY() <= 0 || !airstrikeLocation.getBlock().getType().equals(Material.AIR) || hit) {
                                    playerdata.cancelItemRunnable(this);
								}
							}
						}, 0, 1);	
					}
                    if (playerdata.hasData(444)){
                        playerdata.removeData(444);
                        player.teleport(particleSpawn);
                        if (playerdata.getUniqueItemID() == itemID) PlayerFunctions.changeItem(player, game, 0);
                    }
					playerdata.cancelItemRunnable(this);
				}
				zeit += 0.5;
			}
		}, 0, 10);
		return false;
	}
}
