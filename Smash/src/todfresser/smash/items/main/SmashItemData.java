package todfresser.smash.items.main;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public interface SmashItemData {
	
	String getDisplayName(); //Name of the Item
	Material getType();    //normal ID (237)
	byte getSubID();		//more specified ID (237:2)
	boolean isEnchanted();
	List<String> getLore();  //Text behind the Item�s name
	int getmaxItemUses();
	int getSpawnChance();  //Chance from 0 (very rare) to 50 (very common)

    void onItemAdded(SmashPlayerData playerdata);
    boolean canChangeItem(SmashPlayerData playerdata);
	boolean hasOnRightClickEvent();
	boolean hasOnPlayerHitPlayerEvent();
	boolean hasOnPlayerShootBowEvent();
	boolean hasOnHookEvent();

	/**
	 *
	 * @param playerdata Die Smash-Spielerdaten des jeweiligen Spielers.
	 * @param whoclicked
	 * @param game
	 */
	boolean onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game);
	
	boolean onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
	boolean onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game);
	
	boolean onHookEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
}
