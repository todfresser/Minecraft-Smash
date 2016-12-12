package todfresser.smash.events.main;

import java.util.List;

import todfresser.smash.map.Game;

public abstract class SmashEvent implements SmashEventData{
	
	@Override
	public byte getSubID() {
		return 0;
	}
	
	@Override
	public boolean isEnchanted() {
		return false;
	}
	
	@Override
	public List<String> getLore() {
		return null;
	}
	
	@Override
	public int getTime() {
		return 30;
	}
	
	@Override
	public int getProtectionTime() {
		return 30;
	}
	
	@Override
	public void cancel(Game g) {
		g.cancelAllEventRunnables();
	}
}
