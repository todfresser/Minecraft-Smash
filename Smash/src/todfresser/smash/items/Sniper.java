package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Sniper extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§5Sniper";
	}

	@Override
	public Material getType() {
		return Material.BOW;
	}
	
	@Override
	public boolean isEnchanted() {
		return true;
	}
	
	@Override
	public int getmaxItemUses() {
		return 2;
	}

	@Override
	public int getSpawnChance() {
		return 8;
	}
	
	@Override
	public boolean hasOnPlayerShootBowEvent() {
		return true;
	}

	@Override
	public boolean onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
        player.setVelocity(VectorFunctions.getStandardVector(player.getLocation().getYaw() - 180, 1).multiply(0.4));
		player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(force + 4f)).setGravity(false);
		playerdata.canUseItem = true;
		return true;
	}
}
