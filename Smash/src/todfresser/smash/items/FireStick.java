package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.SmashItemData;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class FireStick implements SmashItemData {
	public String getDisplayName() {
		return "§cF§6i§er§ce§6S§et§ci§6c§ek";
	}

	public List<String> getLore() {
		List<String> l = new ArrayList<>();
		l.add(ChatColor.RED + "Zündet den Gegner an.");
		return l;
	}

	public Material getType() {
		return Material.BLAZE_ROD;
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

	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		PlayerFunctions.sendTitle(target, 1, 25, 10, "§cF§6i§er§ce§6d§e!", "");
		target.setFireTicks(80);
		PlayerFunctions.playOutDamage(game, target, player, VectorFunctions.getStandardVector(player.getLocation().getYaw(), 0.5), 2);
		playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 3;
			@Override
			public void run() {
				if (i == 0){
					playerdata.cancelItemRunnable(this);
					return;
				}
				PlayerFunctions.playOutDamage(game, target, player, 2);
				i--;
			}
		}, 19, 20);
		playerdata.canUseItem = true;
	}

	public void onPlayerShootBowEvent(SmashPlayerData arg0, Player arg1, float arg2, Game arg3) {
	}

	public boolean hasOnRightClickEvent() {
		return false;
	}

	public void onRightClickEvent(SmashPlayerData arg0, Action arg1, Player arg2, Game arg3) {
	}

	@Override
	public int getSpawnChance() {
		return 27;
	}
}
