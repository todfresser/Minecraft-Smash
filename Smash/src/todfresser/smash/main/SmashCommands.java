package todfresser.smash.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import todfresser.smash.map.Game;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;
import todfresser.smash.map.MapEditorData;

public class SmashCommands implements CommandExecutor{
	
	private static String st = ChatColor.GOLD + "-------" + ChatColor.BLUE + ChatColor.BOLD + " [" + ChatColor.AQUA + ChatColor.BOLD + "Smash" + ChatColor.BLUE + ChatColor.BOLD + "] " + ChatColor.GOLD + "-------";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) == false){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		
		
		
		if (cmd.getName().equalsIgnoreCase("smash") || cmd.getName().equalsIgnoreCase("sm")){
			if (args.length == 0){
				p.sendMessage(Smash.pr + st);
				p.sendMessage(Smash.pr + "/smash leave  Verlassen eines Spiels.");
				p.sendMessage(Smash.pr + "/smash maps Anzeigen aller Maps.");
				p.sendMessage(Smash.pr + "/smash games Anzeigen aller laufenden Spiele.");
				if (p.hasPermission("SMASH.admin")){
					p.sendMessage(Smash.pr + "/smash start  Sofortiges Starten einen Spiels.");
					p.sendMessage(Smash.pr + "/smash edit  Erstellen/Verändern einer Map.");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("start")){
				if (p.hasPermission("SMASH.admin")){
					for (Game g : Game.getrunningGames()){
						if (g.containsPlayer(p)){
							g.start(10);
							return true;
						}
					}
					 p.sendMessage(Smash.pr + "Du befindest dich in keinem Spiel!");
					return true;
				}else{
					p.sendMessage(Smash.pr + st);
					p.sendMessage(Smash.pr + "Dir fehlen die Rechte für diesen Befehl.");
				}
				return true;
			}
			
			if (args[0].equalsIgnoreCase("stats")){
				if (args.length == 1) {
					p.sendMessage(ChatColor.GOLD+"<<"+ ChatColor.BLUE+"------------------------"+ChatColor.GOLD+">>");
					p.sendMessage("§6>§9Gespielte Spiele: §6"+Integer.toString(Statistics.getPlayedGames(p.getUniqueId())));
					p.sendMessage("§6>§9Gewonnene Spiele: §6" + Integer.toString(Statistics.getTotalWins(p.getUniqueId())));
					p.sendMessage("§6>§9Getötete Spieler: §6" + Integer.toString(Statistics.getKills(p.getUniqueId())));
					p.sendMessage("§6>§9Tode: §6" + Integer.toString(Statistics.getDeaths(p.getUniqueId())));
					p.sendMessage("§6>§9Schaden ausgeteilt: §6" + Integer.toString(Statistics.getDamageDone(p.getUniqueId())));
					p.sendMessage("§6>§9Erhaltener Schaden: §6" + Integer.toString(Statistics.getTotalDamage(p.getUniqueId())));
					p.sendMessage(ChatColor.GOLD+"<<"+ ChatColor.BLUE+"------------------------"+ChatColor.GOLD+">>");
					return true;
				}else if (args[1].equalsIgnoreCase("reset")) {
					Statistics.delete(p.getUniqueId());
					return true;
				}
			}
			
			
			
			
			if (args[0].equalsIgnoreCase("edit")){
				if (p.hasPermission("SMASH.admin") == false){
					p.sendMessage(Smash.pr + st);
					p.sendMessage(Smash.pr + "Dir fehlen die Rechte für diesen Befehl.");
					return true;
				}
				if (args.length == 2){
					MapEditor.open(p, args[1]);
				}else{
					p.sendMessage(Smash.pr + st);
					p.sendMessage(Smash.pr + "Usage: /smash edit <mapname>");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("maps")){
				p.sendMessage(Smash.pr + st);
				p.sendMessage(Smash.pr + "Die erstellten Maps:");
				if (Map.getloadedMaps().isEmpty() || Map.getallMapnames() == null){
					p.sendMessage(Smash.pr + ChatColor.RED + " + keine Maps vorhanden +");
					return true;
				}
				for (Map m : Map.getloadedMaps()){
					p.sendMessage(Smash.pr + "  " + m.getName());
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("games")){
				p.sendMessage(Smash.pr + st);
				p.sendMessage(Smash.pr + "Die laufenden Spiele:");
				if (Game.getrunningGames().size() == 0){
					p.sendMessage(Smash.pr + ChatColor.RED + " + keine Spiele am laufen +");
					return true;
				}
				for (Game g : Game.getrunningGames()){
					p.sendMessage(Smash.pr + "  " + g.getMap().getName() + " [" + g.getGameState().getName() + "]");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("leave")){
				for (Game g : Game.getrunningGames()){
					if (g.containsPlayer(p)){
						/*if (g.players.get(p.getUniqueId()).equals(PlayerType.Spectator)){
							p.sendMessage(Smash.pr + "Du hast das Spiel verlassen.");
						}
						g.removePlayer(p.getUniqueId(), false);*/
						g.removePlayer(p, true);
						p.sendMessage(Smash.pr + "Du hast das Spiel verlassen.");
						return true;
					}
				}
				p.sendMessage(Smash.pr + st);
				p.sendMessage(Smash.pr + "Du befindest dich in keinem Spiel.");
				return true;
			}
			if (args[0].equalsIgnoreCase("delete")){
				if (p.hasPermission("SMASH.admin") == false){
					p.sendMessage(Smash.pr + st);
					p.sendMessage(Smash.pr + "Dir fehlen die Rechte für diesen Befehl.");
					return true;
				}
				if (args.length == 2){
					for (MapEditorData d : MapEditor.editors){
						if (d.SpielerID.equals(p.getUniqueId())){
							if (d.exists){
								int id = -111;
								try{
									id = Integer.parseInt(args[1]);
								}catch(NullPointerException e){
									p.sendMessage(Smash.pr + "Die ID muss eine Zahl sein!");
								}
								if (id == (d.playerspawns.size()*3 - d.itemspawns.size())){
									d.exists = false;
								}
								return true;
							}else p.sendMessage(Smash.pr + "Die Map existiert nicht!");
						}
					}
				}else p.sendMessage(Smash.pr + "Usage: /smash delete <ID>");
				return true;
			}
			p.sendMessage(Smash.pr + st);
			p.sendMessage(Smash.pr + "Diesen Befehl gibt es nicht.");
			return true;
			
		}
		return false;
	}

}
