package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.ItemManager;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class ItemGrabber extends SmashItem{

	@Override
	public String getDisplayName() {
		return "�fItemGrabber";
	}

	@Override
	public Material getType() {
		return Material.FISHING_ROD;
	}
	
	@Override
	public boolean isEnchanted() {
		return true;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 18;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return true;
	}
	
	@Override
	public boolean onHookEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		playerdata.registerItemRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				if (game.containsPlayer(target) && game.getPlayerData(target).hasItem()){
					int itemID = game.getPlayerData(target).getItemID();
					/*playerdata.removeItems(player);
					playerdata.changeItem(itemID);
					player.getInventory().setItem(0, ItemManager.getStandardItem(itemID));
					player.updateInventory();*/
                    PlayerFunctions.changeItem(player, game, itemID);
					game.getPlayerData(target).removeItems(target);
				}
				playerdata.cancelItemRunnable(this);
				return;
			}
		}, 2, 0);
		playerdata.canUseItem = true;
		return true;
	}

}
