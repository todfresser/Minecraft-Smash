package todfresser.smash.map.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import todfresser.smash.map.Game;

public class WeatherChange implements Listener{
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e){
		for (Game g : Game.getrunningGames()){
			if (e.getWorld().getName().equals(g.getWorld().getName())){
				if (e.toWeatherState()){
					e.setCancelled(true);
					e.getWorld().setStorm(false);
					e.getWorld().setThundering(false);
					e.getWorld().setWeatherDuration(0);
				}
			}
		}
	}
}
