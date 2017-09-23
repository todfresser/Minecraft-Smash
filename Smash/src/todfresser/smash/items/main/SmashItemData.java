package todfresser.smash.items.main;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public interface SmashItemData {
	
	abstract String getDisplayName(); //Name of the Item
	abstract Material getType();    //normal ID (237)
	abstract byte getSubID();		//more specified ID (237:2)
	abstract boolean isEnchanted();
	abstract List<String> getLore();  //Text behind the Item´s name
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
	abstract boolean onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game);
	
	abstract boolean onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
	abstract boolean onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game);
	
	abstract boolean onHookEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
}
