package todfresser.smash.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum SM {
	Prefix("Prefix", "§6»§9Smash§6 | §7"),
	Command_Help_1("Commands.help.1", "/smash stats Zeige deine Stats an."),
	Command_Help_2("Commands.help.2", "/smash leave Verlassen eines Spiels."),
	Command_Help_3("Commands.help.3", "/smash maps  Anzeigen aller Maps."),
	Command_Help_4("Commands.help.4", "/smash games Anzeigen aller laufenden Spiele."),
	Command_Help_Admin_1("Commands.help.admin.1", "/smash edit 	Erstellen/Verändern einer Map."),
	Command_Help_Admin_2("Commands.help.admin.2", "/smash start	Sofortiges Starten einen Spiels."),
	Command_Help_Admin_3("Commands.help.admin.3", "/smash admin addPercent [Parameter int]"),
	Command_Help_Admin_4("Commands.help.admin.4", "/smash admin removePercent [Parameter int]"),
	Command_Help_Admin_5("Commands.help.admin.5", "/smash admin setPercent *[Parameter int]"),
	Command_Start("Commands.start", "Der Start des Spiels admin wurde beshleunigt"),
	Command_Stats_1("Commands.stats.show.1", "§6<<§9------------------------§6>>"),
	Command_Stats_2("Commands.stats.show.2", "§6>§9Gespielte Spiele: §6"),
	Command_Stats_3("Commands.stats.show.3", "§6>§9Gewonnene Spiele: §6"),
	Command_Stats_4("Commands.stats.show.4", "§6>§9Getötete Spieler: §6"),
	Command_Stats_5("Commands.stats.show.5", "§6>§9Tode: §6"),
	Command_Stats_6("Commands.stats.show.6", "§6>§9Deine K/D: §6"),
	Command_Stats_7("Commands.stats.show.7", "§6>§9Schaden ausgeteilt: §6"),
	Command_Stats_8("Commands.stats.show.8", "§6>§9Erhaltener Schaden: §6"),
	Command_Stats_9("Commands.stats.show.9", "§6<<§9------------------------§6>>"),
	Command_Stats_Reset("Commands.stats.reset", "Deine Stats wurden zurückgesetzt"),
	Command_Maps_1("Commands.maps.1", "Die erstellten Maps:"),
	Command_Maps_list("Commands.maps.list", "->%MAPNAME%"),
	Command_Error_NoMaps("Commands.error.maps", "§c + keine Maps vorhanden + "),
	Command_Games_1("Commands.games.1", "Die laufenden Spiele:"),
	Command_Games_list("Commands.games.list", "->%MAPNAME% [%GAMESTATE%]"),
	Command_leave("Commands.leave", "Du hast das Spiel verlassen."),
	Command_Error_Unavailable("Commands.unavailable", "Diesen Befehl gibt es nicht."),
	Command_Error_NoGames("Commands.error.games", "§c + keine Spiele am laufen + "),
	Command_Error_InNoGame("Commands.error.innogame", "Du befindest dich in keinem Spiel!"),
	Command_Error_NotEnoughRights("Commands.error.notenoughrights", "Dir fehlen die Rechte für diesen Befehl."),
	Command_Error_wrongParameters("Commands.error.wrongparameters","Du hast falsche Parameter angegeben. Bitte benutze nur Zahlen."),
	Command_Error_noParameters("Commands.error.noparameters","Bitte füge einen Parameter ein.");
	
	private String path;
	private String defaultmessage;
	
	private SM(String path, String defaultmessage){
		this.path = path;
		this.defaultmessage = defaultmessage;
	}
	
	private String getPath(){
		return path;
	}
	private String getDefaultMessage(){
		return defaultmessage;
	}
	
	@Override
	public String toString(){
		return getFileConfiguration().getString(path);
	}
	private static FileConfiguration cfg;
	
	private static FileConfiguration getFileConfiguration(){
		if (cfg == null){
			File file = new File("plugins/Smash", "Messages.yml");
			if (file.exists()){
				cfg = YamlConfiguration.loadConfiguration(file);
			}else{
				cfg = YamlConfiguration.loadConfiguration(file);
				for (SM message : SM.values()){
					cfg.set(message.getPath(), message.getDefaultMessage());
				}
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cfg;
	}
}
