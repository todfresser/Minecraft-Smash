package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Smasher extends SmashItem{
	
	@Override
	public String getDisplayName() {
		return "§6Smasher";
	}
	
	@Override
	public Material getType() {
		return Material.DIAMOND_AXE;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
	}
	
	@Override
	public boolean hasOnPlayerHitPlayerEvent() {
		return true;
	}
	
	@Override
	public boolean onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		PlayerFunctions.playOutDamage(game, target, player, VectorFunctions.getStandardVector(player.getLocation().getYaw(), 3).multiply(2), 80, true);
		PlayerFunctions.sendTitle(target, 1, 25, 10, "§6Get Smashed", "");
		PlayerFunctions.playOutDamage(game, player, new Vector(0, 1, 0), 0, false);
		PlayerFunctions.sendTitle(player, 1, 25, 10, "§6Get Smashed", "");
		playerdata.canUseItem = true;
		return true;
	}
	
	@Override
	public int getSpawnChance() {
		return 0;
	}
}
