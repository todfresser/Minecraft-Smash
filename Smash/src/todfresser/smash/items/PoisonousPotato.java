package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class PoisonousPotato extends SmashItem{

	private String displayName = ChatColor.DARK_GREEN + "Poisonous " + ChatColor.GOLD + "Potato";
	
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return displayName;
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return Material.POISONOUS_POTATO;
	}

	@Override
	public int getmaxItemUses() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getSpawnChance() {
		// TODO Auto-generated method stub
		return 14;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		
		return true;
	}
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {	
		int damageDoneUntilHere = playerdata.getDamageDone();
		BukkitRunnable particles = playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {	
				
					ParticleEffect.FLAME.display(0.1f, 0.4f, 0.1f, 0.5f, 20, player.getLocation().add(0, 1.5, 0), 40);
			}
		}, 0, 10);
		player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0, false, false));
		playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {	
					int j = playerdata.getDamageDone() - damageDoneUntilHere;
					playerdata.removeDamage(j);
					PlayerFunctions.updateDamageManually(player.getUniqueId(), game);
					playerdata.cancelItemRunnable(particles);
					playerdata.cancelItemRunnable(this);
			}
			
		}, 200, 0);
		playerdata.canUseItem = true;
		return true;
	}
}
