package todfresser.smash.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import todfresser.smash.items.main.SmashItem;

public class PoisonousPotato extends SmashItem{

	private String displayName = ChatColor.DARK_GREEN + "poisonous " + ChatColor.GOLD + "Potato";
	
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return displayName;
	}

	@Override
	public Material getType() {
		// TODO Auto-generated method stub
		return Material.POISONOUS_POTATO;
	}

	@Override
	public int getmaxItemUses() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpawnChance() {
		// TODO Auto-generated method stub
		return 0;
	}

}
