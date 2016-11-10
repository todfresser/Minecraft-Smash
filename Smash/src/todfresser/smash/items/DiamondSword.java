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

public class DiamondSword implements SmashItemData{
	
	@Override
	public String getDisplayName() {

		return "§bDiamondSword";
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	@Override
	public int getSpawnChance() {
		return 13;
	}

	@Override
	public Material getType() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public int getmaxItemUses() {
		return 5;
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
		PlayerFunctions.playOutDamage(game, target, damager, VectorFunctions.getStandardVector(damager.getLocation().getYaw(), 0.45D), 8);
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
