package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class MolotovCocktail extends SmashItem{
	
	@Override
	public String getDisplayName() {

		return "§cM§6o§el§co§6t§eo§cv§6C§eo§cc§6k§et§ca§6i§el";
	}
	
	@Override
	public int getSpawnChance() {

		return 15;
	}

	@Override
	public Material getType() {

		return Material.SPLASH_POTION;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		
		Item grenade = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.SPLASH_POTION));	
		
		grenade.setVelocity(player.getLocation().getDirection().multiply(1.5D));


		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 25;

			@Override
			public void run() {
				if (i == 25) grenade.remove();
				if(i >=0 ) {
				i--;
				//grenade.getWorld().spigot().playEffect(grenade.getLocation(), Effect.FLAME, 0, 0, 2, 2, 2, 0, 300, 100);
				ParticleEffect.FLAME.display(2.0f, 2.0f, 2.0f, 0.0f, 300, grenade.getLocation(), 100);
				for (Entity e: grenade.getNearbyEntities(4, 4, 4)) {
					if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
						PlayerFunctions.playOutDamage(game, (Player) e,player, VectorFunctions.getStandardVector(Math.random()*180-90, 0.5).multiply(0.4), 1, false);
						e.setFireTicks(20);
					}
				
			}
			}else {
				playerdata.cancelItemRunnable(this);
			}
				
			}
		}, 35, 4);
		playerdata.canUseItem = true;
		return true;
	}
}
