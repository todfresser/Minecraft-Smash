package todfresser.smash.basic.events.main;

import java.util.List;

import org.bukkit.Material;

import todfresser.smash.map.Game;

public interface SmashEventData {
	abstract String getDisplayName();
	abstract Material getType();
	abstract byte getSubID();
	abstract boolean isEnchanted();
	abstract List<String> getLore();
	abstract int getChance();
	
	abstract int getTime();
	abstract int getProtectionTime();
	abstract boolean perform(Game g);
	abstract void cancel(Game g);
}
