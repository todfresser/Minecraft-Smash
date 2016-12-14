package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.map.Game;

public class DoubleDamage extends SmashEvent{
	
	@Override
	public String getDisplayName() {
		return ChatColor.DARK_RED + "Doppelter Schaden!";
	}

	@Override
	public Material getType() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public int getChance() {
		return 30;
	}
	
	@Override
	public int getTime() {
		return 20;
	}
	
	@Override
	public int getProtectionTime() {
		return 6;
	}

	@Override
	public boolean perform(Game g) {
		g.setDamageMultiplier(2);
		return true;
	}
	
	@Override
	public void cancel(Game g) {
		g.setDamageMultiplier(1);
	}
}
