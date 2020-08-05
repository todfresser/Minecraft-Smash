package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class SmokeGrenade extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§8Smoke§7G§fr§7e§fn§7a§fd§7e";
	}

	@Override
	public Material getType() {
		return Material.SKULL_ITEM;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 25;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item grenade = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.SKULL_ITEM, 1, (byte)1));
		
		grenade.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 25;

			@Override
			public void run() {
				if (i == 25) grenade.remove();
				if(i >=0 ) {
				i--;
				//if (i > 2) grenade.getWorld().spigot().playEffect(grenade.getLocation(), Effect.LARGE_SMOKE, 0, 0, 2, 2, 2, 0.1f, 300, 100);
				if (i > 2) ParticleEffect.SMOKE_LARGE.display(2f, 2f, 2f, 0.1f, 300, grenade.getLocation(), 100);
				for (Entity e: grenade.getNearbyEntities(5, 5, 5)) {
					if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
						Player p = (Player) e;
						if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) p.removePotionEffect(PotionEffectType.BLINDNESS);
						if (p.hasPotionEffect(PotionEffectType.SLOW)) p.removePotionEffect(PotionEffectType.SLOW);
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, false, false));
					}
				}
			}else {
				playerdata.cancelItemRunnable(this);
			}
				
			}
		}, 40, 4);
		playerdata.canUseItem = true;
		return true;
		
	}
	
	@Override
	public byte getSubID() {
		// TODO Auto-generated method stub
		return 1;
	}
}
