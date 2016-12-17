package todfresser.smash.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import todfresser.smash.events.main.SmashEvent;
import todfresser.smash.map.Game;

public class MaximizedKnockback extends SmashEvent{

	@Override
	public String getDisplayName() {
		return ChatColor.DARK_RED + "Maximierter Rückstoß";
	}

	@Override
	public Material getType() {
		return Material.ANVIL;
	}
	
	@Override
	public int getProtectionTime() {
		return 5;
	}

	@Override
	public int getChance() {
		return 40;
	}

	@Override
	public boolean perform(Game g) {
		g.setVelocityMultiplier(2);
		return true;
	}
	
	@Override
	public void cancel(Game g) {
		g.setVelocityMultiplier(1);
	}

}
