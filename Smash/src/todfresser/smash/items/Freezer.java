package todfresser.smash.items;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Freezer implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§9F§br§9e§be§9z§be§9r";
	}

	@Override
	public Material getType() {
		return Material.PACKED_ICE;
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
		return 10;
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
		
		Item freezer = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.PACKED_ICE));	
		
		freezer.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 13;
			@Override
			public void run() {
				if (i == 13) freezer.remove();
				if(i >=0 ) {
				i--;
				freezer.getWorld().spigot().playEffect(freezer.getLocation(), Effect.SNOW_SHOVEL, 0, 0, 2, 2, 2, 0, 300, 100);
				for (Entity e: freezer.getNearbyEntities(4, 4, 4)) {
					if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
						Player target = (Player) e;
						PlayerFunctions.playOutDamage(game, target,player, VectorFunctions.getStandardVector(Math.random()*180-90, 0.5).multiply(0.1), 1);
						if (target.hasPotionEffect(PotionEffectType.HUNGER)) target.removePotionEffect(PotionEffectType.HUNGER);
						if (target.hasPotionEffect(PotionEffectType.SLOW)) target.removePotionEffect(PotionEffectType.SLOW);
						target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 90, 0, false, false));
						target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 170, 3, false, false));
					}
				}
			}else {
				playerdata.cancelItemRunnable(this);
			}
				
			}
		}, 40, 10);
		playerdata.canUseItem = true;
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {	
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {	
	}

	@Override
	public void onHookPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}
	
}
