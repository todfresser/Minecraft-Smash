package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class StoneSword extends SmashItem{
	
	@Override
	public String getDisplayName() {

		return "§8StoneSword";
	}
	
	@Override
	public int getSpawnChance() {
		return 30;
	}

	@Override
	public Material getType() {
		return Material.STONE_SWORD;
	}

	@Override
	public int getmaxItemUses() {
		return 4;
	}

	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		return true;
	}
	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player damager, Player target, Game game) {
		PlayerFunctions.playOutDamage(game, target, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), 0.45D), 4, true);
		playerdata.canUseItem = true;
		
	}
}
