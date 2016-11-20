package todfresser.smash.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Hook extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§fHook";
	}

	@Override
	public Material getType() {
		return Material.FISHING_ROD;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 11;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return true;
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
		player.setVelocity(VectorFunctions.getVectorbetweenLocations(player.getLocation(), target).multiply(0.5f));
		playerdata.canUseItem = true;
		
	}
}
