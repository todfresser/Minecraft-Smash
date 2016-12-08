package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Snowball extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§bSnowball";
	}

	@Override
	public Material getType() {
		return Material.SNOW_BALL;
	}

	@Override
	public int getmaxItemUses() {
		return 3;
	}

	@Override
	public int getSpawnChance() {
		return 25;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		player.launchProjectile(org.bukkit.entity.Snowball.class);
		playerdata.canUseItem = true;
		return true;
	}

}
