package todfresser.smash.items;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class StoneSword implements SmashItemData{
	
	@Override
	public String getDisplayName() {

		return "§8StoneSword";
	}

	@Override
	public List<String> getLore() {
		return null;
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
	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return false;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player damager, Player target, Game game) {
		PlayerFunctions.playOutDamage(game, target, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), 0.45D), 4);
		playerdata.canUseItem = true;
		
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData arg0, Player arg1, float arg2, Game arg3) {
		
	}

	@Override
	public void onRightClickEvent(SmashPlayerData arg0, Action arg1, Player arg2, Game arg3) {		
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
