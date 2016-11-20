package todfresser.smash.items.main;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public abstract class SmashItem implements SmashItemData {
	
	@Override
	public byte getSubID() {
		return 0;
	}

	@Override
	public List<String> getLore() {
		return null;
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
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
		// TODO Auto-generated method stub

	}

}
