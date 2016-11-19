package todfresser.smash.items;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Hook implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§fHook";
	}

	@Override
	public Material getType() {
		return Material.FISHING_ROD;
	}

	@Override
	public List<String> getLore() {
		return null;
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
	public boolean hasOnRightClickEvent() {
		return false;
	}

	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		return false;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return true;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
	}

	@Override
	public byte getSubID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
		player.setVelocity(VectorFunctions.getVectorbetweenLocations(player.getLocation(), target).multiply(0.5f));
		playerdata.canUseItem = true;
		
	}

}
