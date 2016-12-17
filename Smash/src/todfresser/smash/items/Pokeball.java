package todfresser.smash.items;

import java.util.Random;

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
				Random r = new Random();
				switch(r.nextInt(5)){
					case 0:
						new SmashEntity(game, pokeball.getLocation(), SmashEntityType.BLAZE, 2);
						break;
					case 1:
						new SmashEntity(game, pokeball.getLocation(), SmashEntityType.ZOMBIE, 4);
						break;
					case 2:
						new SmashEntity(game, pokeball.getLocation(), SmashEntityType.SPIDER, 2);
						break;
					case 3:
						new SmashEntity(game, pokeball.getLocation(), SmashEntityType.VEX, 1);
						break;
					case 4:
						new SmashEntity(game, pokeball.getLocation(), SmashEntityType.ENDERMITE, 1);
						break;
				}
				playerdata.cancelItemRunnable(this);
			}
		}, 30, 0);
		playerdata.canUseItem = true;
		return true;
	}

}
