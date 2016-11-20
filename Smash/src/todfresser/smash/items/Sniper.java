package todfresser.smash.items;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Sniper implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§5Sniper";
	}

	@Override
	public Material getType() {
		return Material.BOW;
	}

	@Override
	public byte getSubID() {
		return 0;
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	@Override
	public int getmaxItemUses() {
		return 2;
	}

	@Override
	public int getSpawnChance() {
		return 9;
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
		return true;
	}

	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		player.launchProjectile(Arrow.class, player.getLocation().getDirection().multiply(force + 5.0f)).setGravity(false);
		playerdata.canUseItem = true;
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
	}
	
	
}
