package todfresser.smash.items;



import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class Protector extends SmashItem{

	private String displayName = ChatColor.DARK_AQUA + "Protector";
	
	@Override
	public String getDisplayName() {
		
		return displayName;
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return Material.SHIELD;
	}

	@Override
	public int getmaxItemUses() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getSpawnChance() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		playerdata.preventDamage();
		playerdata.registerItemRunnable(new BukkitRunnable() {
			@Override
			public void run() {	
				playerdata.allowDamage();
				playerdata.cancelItemRunnable(this);
			}
			
		}, 200, 0);
	
		
		return true;
	}
	
	
}
