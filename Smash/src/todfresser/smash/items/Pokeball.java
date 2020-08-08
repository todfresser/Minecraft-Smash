package todfresser.smash.items;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.mobs.SmashEntity;
import todfresser.smash.mobs.SmashEntityAttributes;

public class Pokeball extends SmashItem{

	private static final List<SmashEntityAttributes> ENTITY_ATTRIBUTES = Arrays.asList(
			new SmashEntityAttributes(
					Blaze.class, 2, 2, 0.8, 1, 0.25, 30),
			new SmashEntityAttributes(
					Zombie.class, 4, 6, 0.2, 0.4, 0.4, 30),
			new SmashEntityAttributes(
					CaveSpider.class, 2, 3, 0.3, 0.8, 0.5, 30),
			new SmashEntityAttributes(
					Vex.class, 1, 4, 0.15, 0.3, 0.4, 30),
			new SmashEntityAttributes(
					Endermite.class, 1, 4, 0.3, 0.3, 0.45, 30),
			new SmashEntityAttributes(
					Phantom.class, 2, 10, 0.1, 0.3, 0.5, 30),
			new SmashEntityAttributes(
					Pillager.class, 2, 2, 0.8, 0.5, 0.2, 10),
			new SmashEntityAttributes(
					Ravager.class, 4, 4, 1.0, 0.3, 0.3, 20),
			new SmashEntityAttributes(
					Stray.class, 2, 10, 0.5, 0.7, 0.5, 30)
	);

	private final Random random = new Random();

	@Override
	public String getDisplayName() {
		return "§cP§fo§ck§fe§cb§fa§cl§fl";
	}

	@Override
	public Material getType() {
		return Material.VEX_SPAWN_EGG;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 30;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item pokeball = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.VEX_SPAWN_EGG));
		
		pokeball.setVelocity(player.getLocation().getDirection().multiply(1.5D));
		
		playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {
				pokeball.remove();
				int attributeIndex = random.nextInt(ENTITY_ATTRIBUTES.size());
				SmashEntity.spawn(game, player, pokeball.getLocation(), ENTITY_ATTRIBUTES.get(attributeIndex));
				playerdata.cancelItemRunnable(this);
			}
		}, 30, 0);
		playerdata.canUseItem = true;
		return true;
	}

}
