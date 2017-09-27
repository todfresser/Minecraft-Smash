package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class GoldenApple extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§5GoldenApple";
	}

	@Override
	public Material getType() {
		return Material.GOLDEN_APPLE;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 1;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}

	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		playerdata.removeDamage(50);
		PlayerFunctions.updateDamageManually(player.getUniqueId(), game);
		player.setFoodLevel(20);
		PlayerFunctions.setAllowFlight(player);
		playerdata.canUseItem = true;
		return true;
	}
}
