package todfresser.smash.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import todfresser.smash.map.SmashPlayerData;

public class Statistics {
	
	public static void update(UUID PlayerID, SmashPlayerData data){
		FileConfiguration cfg = getFileConfiguration(PlayerID);
		int pg = cfg.getInt("PlayedGames");
		int dd = cfg.getInt("DamageDone");
		int td = cfg.getInt("TotalDamage");
		int d = cfg.getInt("Deaths");
		int k = cfg.getInt("Kills");
		cfg.set("PlayedGames", Integer.valueOf(pg + 1));
		cfg.set("DamageDone", Integer.valueOf(dd + data.getDamageDone()));
		cfg.set("TotalDamage", Integer.valueOf(td + data.getTotalDamge()));
		cfg.set("Deaths", Integer.valueOf(d + data.getDeaths()));
		cfg.set("Kills", Integer.valueOf(k + data.getKills()));
		save(PlayerID, cfg);
	}
	private static FileConfiguration getFileConfiguration(UUID PlayerID){
		File file = new File("plugins/Smash/Statistics", PlayerID.toString() + ".yml");
		if (file.exists()){
			return YamlConfiguration.loadConfiguration(file);
		}else{
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			cfg.set("PlayedGames", 0);
			cfg.set("TotalWins", 0);
			cfg.set("DamageDone", 0);
			cfg.set("TotalDamage", 0);
			cfg.set("Deaths", 0);
			cfg.set("Kills", 0);
			save(PlayerID, cfg);
			return cfg;
		}
	}
	private static void save(UUID PlayerID, FileConfiguration cfg){
		File file = new File("plugins/Smash/Statistics", PlayerID.toString() + ".yml");
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(UUID PlayerID){
		File file = new File("plugins/Smash/Statistics", PlayerID.toString() + ".yml");
		if (file.exists()) file.delete();
	}
	
	public static int getPlayedGames(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("PlayedGames");
	}
	
	public static void addWin(UUID PlayerID){
		FileConfiguration cfg = getFileConfiguration(PlayerID);
		int tw = cfg.getInt("TotalWins");
		cfg.set("TotalWins", tw +1 );
		save(PlayerID, cfg);
	}
	
	public static int getTotalWins(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("TotalWins");
	}
	
	public static int getDamageDone(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("DamageDone");
	}
	
	public static int getTotalDamage(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("TotalDamage");
	}
	
	public static int getDeaths(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("Deaths");
	}
	
	public static int getKills(UUID PlayerID){
		return getFileConfiguration(PlayerID).getInt("Kills");
	}
}
