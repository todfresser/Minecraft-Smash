package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Steak extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§6Steak";
	}

	@Override
	public Material getType() {
		return Material.COOKED_BEEF;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		playerdata.removeDamage(10);
		PlayerFunctions.updateDamageManually(whoclicked.getUniqueId(), game);
		playerdata.canUseItem = true;
	}
}
