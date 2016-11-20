package todfresser.smash.items.main;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public interface SmashItemData {
	
	abstract String getDisplayName(); //Name of the Item
	abstract Material getType();    //Type from the Item
	abstract byte getSubID();
	abstract List<String> getLore();
	abstract int getmaxItemUses();
	abstract int getSpawnChance();  //Chance from 0 (very rare) to 50 (very common)
	
	abstract boolean hasOnRightClickEvent();
	abstract boolean hasOnPlayerHitPlayerEvent();
	abstract boolean hasOnPlayerShootBowEvent();
	abstract boolean hasOnHookEvent();
	
	/**
	 * 
	 * @param playerdata Die Smash-Spielerdaten des jeweiligen Spielers.
	 * @param whoclicked
	 * @param game
	 */
	abstract void onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game);
	
	abstract void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
	abstract void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game);
	
	abstract void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game);
	
}
