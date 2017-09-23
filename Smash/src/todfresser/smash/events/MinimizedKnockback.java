package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.map.Game;

public class MinimizedKnockback extends SmashEvent{

	@Override
	public String getDisplayName() {
		return ChatColor.DARK_GREEN + "Minimierter Rückstoß";
	}

	@Override
	public Material getType() {
		return Material.SHIELD;
	}

	@Override
	public int getChance() {
		return 40;
	}
	
	@Override
	public int getProtectionTime() {
		return 5;
	}

	@Override
	public boolean perform(Game g) {
		g.setVelocityMultiplier(0.3);
		return true;
	}
	
	@Override
	public void cancel(Game g) {
		g.setVelocityMultiplier(1);
	}

}
