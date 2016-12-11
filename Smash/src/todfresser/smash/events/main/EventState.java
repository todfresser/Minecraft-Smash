package todfresser.smash.events.main;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import todfresser.smash.main.Smash;
import todfresser.smash.map.Game;

public class EventState {
	private BossBar bar;
	private SmashEventData currentEvent;
	private boolean protection;
	private double time;
	
	
	public EventState(){
		currentEvent = null;
		protection = false;
		time = 20+(int)(Math.random()*80);
		bar = Smash.getInstance().getServer().createBossBar(ChatColor.GRAY + "Kein Event", BarColor.YELLOW, BarStyle.SOLID);
		bar.setVisible(false);
		bar.setProgress(1);
		
	}
	
	public void update(Game g){
		if (currentEvent != null){
			if (protection){
				time = time + 1/(double)currentEvent.getTime();
				if (time >= 1){
					protection = false;
					time = 10+(int)(Math.random()*80);
					currentEvent = null;
					bar.setColor(BarColor.YELLOW);
					bar.setProgress(1);
					return;
				}
				bar.setProgress(time);
			}else{
				if (time <= 0){
					time = 1;
				}else{
					time = time - 1/(double)currentEvent.getTime();
					if (time <= 0){
						currentEvent.cancel(g);
						bar.setTitle(ChatColor.GRAY + "Kein Event");
						bar.setColor(BarColor.GREEN);
						protection = true;
						time = 0;
						
					}
				}
				bar.setProgress(time);
			}
			return;
		}
		if (time <= 0){
			currentEvent = EventManager.activateRandomEvent(g);
			bar.setTitle(currentEvent.getDisplayName());
			bar.setColor(BarColor.RED);
		}else time--;
	}
	
	public void addPlayer(Player p){
		for (Player player : bar.getPlayers()){
			if (player.getUniqueId().equals(p.getUniqueId())) return;
		}
		bar.addPlayer(p);
	}
	
	public void show(){
		bar.setVisible(true);
	}
	
	public void removePlayer(Player p){
		for (Player player : bar.getPlayers()){
			if (player.getUniqueId().equals(p.getUniqueId())){
				bar.removePlayer(p);
				return;
			}
		}
	}
}
