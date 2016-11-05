package todfresser.smash.items;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class MolotovCocktail implements SmashItemData{
	
	@Override
	public String getDisplayName() {

		return "§cM§6o§el§co§6t§eo§cv§6C§eo§cc§6k§et§ca§6i§el";
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	@Override
	public int getSpawnChance() {

		return 10;
	}

	@Override
	public Material getType() {

		return Material.EXP_BOTTLE;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
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
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData arg0, Player arg1, Player arg2, Game arg3) {
		
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData arg0, Player arg1, float arg2, Game arg3) {
		
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action click, Player player, Game game) {
		
		Item grenade = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.EXP_BOTTLE));	
		
		grenade.setVelocity(player.getLocation().getDirection().multiply(1.5D));


		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 25;

			@Override
			public void run() {
				if (i == 25) grenade.remove();
				if(i >=0 ) {
				i--;
				grenade.getWorld().spigot().playEffect(grenade.getLocation(), Effect.FLAME, 0, 0, 2, 2, 2, 0, 300, 100);
				for (Entity e: grenade.getNearbyEntities(4, 4, 4)) {
					if (e.getType().equals(org.bukkit.entity.EntityType.PLAYER)) {
					PlayerFunctions.playOutDamage(game, player,player, VectorFunctions.getStandardVector(Math.random()*180-90, 0.5).multiply(0.4), 1);
					player.setFireTicks(20);
					}
				
			}
			}else {
				playerdata.cancelItemRunnable(this);
			}
				
			}
		}, 40, 4);
		
	
	
playerdata.canUseItem = true;
	
	
	
	
	
	}

	@Override
	public void onHookPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		// TODO Auto-generated method stub
		
	}
}
