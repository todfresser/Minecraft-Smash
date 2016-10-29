package todfresser.smash.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public interface SmashItemData {
	
	public String getDisplayName();
	public Material getType();
	public List<String> getLore();
	public int getmaxItemUses();
	public int getSpawnChance();
	
	public boolean hasOnRightClickEvent();
	public boolean hasOnPlayerHitPlayerEvent();
	public boolean hasOnPlayerShootBowEvent();
	
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game);
	
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game);
	
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game);
	
}
