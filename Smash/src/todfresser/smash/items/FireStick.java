package todfresser.smash.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class FireStick extends SmashItem {
	@Override
	public String getDisplayName() {
		return "§cF§6i§er§ce§6S§et§ci§6c§ek";
	}

	@Override
	public List<String> getLore() {
		List<String> l = new ArrayList<>();
		l.add(ChatColor.RED + "Zündet den Gegner an.");
		return l;
	}

	@Override
	public Material getType() {
		return Material.BLAZE_ROD;
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
		PlayerFunctions.sendTitle(target, 1, 25, 10, "§cF§6i§er§ce§6d§e!", "");
		target.setFireTicks(120);
		PlayerFunctions.playOutDamage(game, target, player, VectorFunctions.getStandardVector(player.getLocation().getYaw(), 0.5), 6, true);
		/*playerdata.registerItemRunnable(new BukkitRunnable() {
			int i = 3;
			@Override
			public void run() {
				if (i == 0){
					playerdata.cancelItemRunnable(this);
					return;
				}
				PlayerFunctions.playOutDamage(game, target, player, 4, false);
				i--;
			}
		}, 19, 20);*/
		playerdata.canUseItem = true;
		return true;
	}
	
	@Override
	public int getSpawnChance() {
		return 27;
	}
}
