package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class DiamondSword extends SmashItem{
	
	@Override
	public String getDisplayName() {

		return "§bDiamondSword";
	}
	
	@Override
	public int getSpawnChance() {
		return 13;
	}

	@Override
	public Material getType() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public int getmaxItemUses() {
		return 5;
	}

	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		return true;
	}
	
	@Override
	public boolean onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player damager, Player target, Game game) {
		PlayerFunctions.playOutDamage(game, target, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), 0.45D), 8, true);
		playerdata.canUseItem = true;
		return true;
		
	}
}
