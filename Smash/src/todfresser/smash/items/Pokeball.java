package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.mobs.main.SmashEntity;
import todfresser.smash.mobs.main.SmashEntityType;

public class Pokeball extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§cP§fo§ck§fe§cb§fa§cl§fl";
	}

	@Override
	public Material getType() {
		return Material.MONSTER_EGG;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 20;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item pokeball = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.MONSTER_EGG));	
		
		pokeball.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {
				pokeball.remove();
				new SmashEntity(game, pokeball.getLocation(), SmashEntityType.BLAZE);
				playerdata.cancelItemRunnable(this);
			}
		}, 30, 0);
		playerdata.canUseItem = true;
		return true;
	}

}
