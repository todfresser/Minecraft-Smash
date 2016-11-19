package todfresser.smash.items;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class SpeedItem implements SmashItemData{

	@Override
	public String getDisplayName() {
		return ChatColor.BLUE + "Speed";
	}

	@Override
	public Material getType() {
		return Material.SUGAR;
	}

	@Override
	public List<String> getLore() {
		return null;
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
	public boolean hasOnPlayerHitPlayerEvent() {
		return false;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}
	
	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
		//if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (whoclicked.getPotionEffect(PotionEffectType.SPEED) != null) whoclicked.removePotionEffect(PotionEffectType.SPEED);
		whoclicked.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 8*20, 1));
		/*playerdata.registerItemRunnable(new BukkitRunnable() {
			
			@Override
			public void run() {
				whoclicked.removePotionEffect(PotionEffectType.SPEED);
				playerdata.canUseItem = true;
				playerdata.cancelItemRunnable(this);
			}
		}, 6*20, 0);*/
		playerdata.canUseItem = true;
		
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
		// TODO Auto-generated method stub
		
	}
	
	//Chance from 0 (very rare) to 50 (very common)
	@Override
	public int getSpawnChance() {
		return 49;
	}

	@Override
	public byte getSubID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
		// TODO Auto-generated method stub
		
	}

}
