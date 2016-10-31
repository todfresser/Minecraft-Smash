package todfresser.smash.items;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Smasher implements SmashItemData {
	public String getDisplayName() {
		return "§6Smasher";
	}

	public List<String> getLore() {
		return null;
	}

	public Material getType() {
		return Material.DIAMOND_AXE;
	}

	public int getmaxItemUses() {
		return 1;
	}

	public boolean hasOnPlayerHitPlayerEvent() {
		return true;
	}

	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}

	public boolean hasOnRightClickEvent() {
		return false;
	}

	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		PlayerFunctions.playOutDamage(game, target, player, VectorFunctions.getStandardVector(player.getLocation().getYaw(), 6.5D).multiply(8), 100);
		PlayerFunctions.sendTitle(target, 1, 25, 10, "§6Get Smashed", "");
		PlayerFunctions.playOutDamage(game, player, new Vector(0, 1, 0), 0);
		PlayerFunctions.sendTitle(player, 1, 25, 10, "§6Get Smashed", "");
		playerdata.canUseItem = true;
	}

	public void onPlayerShootBowEvent(SmashPlayerData arg0, Player arg1, float arg2, Game arg3) {
	}

	public void onRightClickEvent(SmashPlayerData arg0, Action arg1, Player arg2, Game arg3) {
	}

	@Override
	public int getSpawnChance() {
		return 0;
	}
}
