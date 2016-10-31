package todfresser.smash.items;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class JetPack implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§fJ§7e§ft§7P§fa§7c§fk";
	}

	@Override
	public Material getType() {
		return Material.IRON_HOE;
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	@Override
	public int getmaxItemUses() {
		return 3;
	}

	@Override
	public int getSpawnChance() {
		return 20;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
		whoclicked.setVelocity(VectorFunctions.getStandardVector(whoclicked.getLocation().getYaw(), 4).multiply(0.2f));
		whoclicked.getLocation().getWorld().spigot().playEffect(whoclicked.getLocation().subtract(0, 0.5, 0), Effect.MOBSPAWNER_FLAMES, 1, 1, 0.4f, 0.0f, 0.4f, 0.1f, 4, 20);
		playerdata.canUseItem = true;
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		
	}
	

}
