package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class HealingField extends SmashItem{
	
	private String displayName = ChatColor.GREEN + "Field of Healing";

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Material getType() {
		return Material.APPLE;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 7;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item healingField = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.APPLE));	
		
		healingField.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 13;
			@Override
			public void run() {
				if (i == 13) healingField.remove();
				if(i >=0 ) {
					i--;
					ParticleEffect.HEART.display(1.1f, 0.10f, 1.1f, 0, 120, healingField.getLocation(), 80);
					for (Entity e: healingField.getNearbyEntities(2.3, 1.5, 2.3)) {
						if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
							Player target = (Player) e;
							if (game.containsPlayer(target)){
								game.getPlayerData(target).removeDamage(2);
								PlayerFunctions.updateDamageManually(target.getUniqueId(), game);
							}
						}
					}
				}else {
					playerdata.cancelItemRunnable(this);
				}
			}
		}, 60, 10);
		playerdata.canUseItem = true;
		return true;
	}
	
}
