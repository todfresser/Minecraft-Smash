package todfresser.smash.basic.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import todfresser.smash.basic.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class SpeedItem extends SmashItem{

	@Override
	public String getDisplayName() {
		return ChatColor.BLUE + "Speed";
	}

	@Override
	public Material getType() {
		return Material.SUGAR;
	}
	
	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		if (whoclicked.getPotionEffect(PotionEffectType.SPEED) != null) whoclicked.removePotionEffect(PotionEffectType.SPEED);
		whoclicked.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 8*20, 1));
		playerdata.canUseItem = true;
		return true;
		
	}
	
	@Override
	public int getSpawnChance() {
		return 49;
	}
}
