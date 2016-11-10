package todfresser.smash.items;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class GoldenApple implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§5GoldenApple";
	}

	@Override
	public Material getType() {
		return Material.GOLDEN_APPLE;
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
		return 1;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
		playerdata.removeDamage(100);
		PlayerFunctions.updateDamageManually(whoclicked.getUniqueId(), game);
		playerdata.canUseItem = true;
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
		// TODO Auto-generated method stub
		
	}

}
