package todfresser.smash.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.map.Game;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;

public class SmashCommands implements CommandExecutor, TabCompleter {
	
	//private static String st = ChatColor.GOLD + "-------" + ChatColor.BLUE + ChatColor.BOLD + " [" + ChatColor.AQUA + ChatColor.BOLD + "Smash" + ChatColor.BLUE + ChatColor.BOLD + "] " + ChatColor.GOLD + "-------";

	private static void tryTabComplete(List<String> suggestions, String input, String suggestion) {
		if (suggestion.startsWith(input)) {
			suggestions.add(suggestion);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> suggestions = new ArrayList<>();
		if (sender instanceof Player){
			Player p = (Player) sender;
			//if (cmd.getName().equalsIgnoreCase("smash") || cmd.getName().equalsIgnoreCase("sm")){
			boolean admin = p.hasPermission("SMASH.admin");
			if (args.length == 1){
				tryTabComplete(suggestions, args[0], "stats");
				for (Game g : Game.getrunningGames()) {
					if (g.containsPlayer(p)) {
						if (admin) {
							tryTabComplete(suggestions, args[0], "start");
						}
						if (args[0].startsWith("leave")) {
							tryTabComplete(suggestions, args[0], "leave");
						}
					}
				}
				if (admin) {
					tryTabComplete(suggestions, args[0], "admin");
				}
			} else if (admin && args[0].equals("admin")) {
				if (args.length == 2) {
					tryTabComplete(suggestions, args[1], "edit");
					tryTabComplete(suggestions, args[1], "goto");
					tryTabComplete(suggestions, args[1], "maps");
					tryTabComplete(suggestions, args[1], "games");
					for (Game g : Game.getrunningGames()) {
						if (g.containsPlayer(p)) {
							tryTabComplete(suggestions, args[1], "addPercents");
							tryTabComplete(suggestions, args[1], "removePercents");
							tryTabComplete(suggestions, args[1], "setPercents");
						}
					}
				} else if (args.length == 3) {
					if (args[1].equals("edit") || args[1].equals("goto")) {
						for (String name : Map.getallMapnames()) {
							tryTabComplete(suggestions, args[2], name);
						}
					}
				} else if (args.length == 4) {
					if (args[1].equals("goto")) {
						suggestions.add("lobby");
						suggestions.add("leave");
					}
				}
			}
		}
		return suggestions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		
		
		
		//if (cmd.getName().equalsIgnoreCase("smash") || cmd.getName().equalsIgnoreCase("sm")){
		if (args.length == 0){
			p.sendMessage(Smash.pr + SM.Command_Help_1);
			p.sendMessage(Smash.pr + SM.Command_Help_2);
			if (p.hasPermission("SMASH.admin")){
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_1);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_2);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_3);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_4);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_5);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_6);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_7);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_8);
			}
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("admin") && p.hasPermission("SMASH.admin")) {
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_1);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_2);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_3);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_4);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_5);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_6);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_7);
				p.sendMessage(Smash.pr + SM.Command_Help_Admin_8);
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("start")){
			if (p.hasPermission("SMASH.admin")){
				for (Game g : Game.getrunningGames()){
					if (g.containsPlayer(p)){
						g.start(10);
						p.sendMessage(Smash.pr + SM.Command_Start.toString());
						return true;
					}
				}
				 p.sendMessage(Smash.pr + SM.Command_Error_InNoGame);
				return true;
			}else{
				p.sendMessage(Smash.pr + SM.Command_Error_NotEnoughRights);
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("stats")){
			if (args.length == 1) {
				int kills = Statistics.getKills(p.getUniqueId());
				int deaths = Statistics.getDeaths(p.getUniqueId());
				p.sendMessage(SM.Command_Stats_1.toString());
				p.sendMessage(SM.Command_Stats_2 + Integer.toString(Statistics.getPlayedGames(p.getUniqueId())));
				p.sendMessage(SM.Command_Stats_3 + Integer.toString(Statistics.getTotalWins(p.getUniqueId())));
				p.sendMessage(SM.Command_Stats_4 + Integer.toString(kills));
				p.sendMessage(SM.Command_Stats_5 + Integer.toString(deaths));
				double kd = BigDecimal.valueOf((double)kills / (double)deaths).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
				p.sendMessage(SM.Command_Stats_6 + Double.toString(kd));
				p.sendMessage(SM.Command_Stats_7 + Integer.toString(Statistics.getDamageDone(p.getUniqueId())));
				p.sendMessage(SM.Command_Stats_8 + Integer.toString(Statistics.getTotalDamage(p.getUniqueId())));
				p.sendMessage(SM.Command_Stats_9.toString());
				return true;
			}else if (args[1].equalsIgnoreCase("reset")) {
				Statistics.delete(p.getUniqueId());
				p.sendMessage(Smash.pr + SM.Command_Stats_Reset);
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("leave")){
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					/*if (g.players.get(p.getUniqueId()).equals(PlayerType.Spectator)){
						p.sendMessage(Smash.pr + "Du hast das Spiel verlassen.");
					}
					g.removePlayer(p.getUniqueId(), false);*/
					g.removePlayer(p, true);
					p.sendMessage(Smash.pr + SM.Command_leave);
					return true;
				}
			}
			p.sendMessage(Smash.pr + SM.Command_Error_InNoGame);
			return true;
		}
		if (args[0].equalsIgnoreCase("admin") && p.hasPermission("SMASH.admin")){
			if(args.length > 1) {
				if (args[1].equalsIgnoreCase("edit")){
					if (args.length == 3){
						MapEditor.open(p, args[2]);
					}else{
						p.sendMessage(Smash.pr + "Usage: /smash admin edit <mapname>");
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("goto")){
					if (args.length >= 3){
						for (Map m : Map.getloadedMaps()) {
							if (m.getName().equals(args[2])) {
								World world = m.loadWorld();
								if (args.length == 4) {
									if (args[3].equalsIgnoreCase("lobby")) {
										p.teleport(m.getLobbySpawnPoint(world));
										p.setFlying(true);
										return true;
									} else if (args[3].equalsIgnoreCase("leave")) {
										p.teleport(m.getLeavePoint());
										p.setFlying(true);
										return true;
									}
								}
								p.teleport(m.getSpectatorSpawnPoint(world));
								p.setFlying(true);
								return true;
							}
						}
						p.sendMessage(Smash.pr + "The Map " + args[2] + " doesn't exist.");
					}else{
						p.sendMessage(Smash.pr + "Usage: /smash admin goto <mapname> <loppy|leave>");
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("maps")){
					p.sendMessage(Smash.pr + SM.Command_Maps_1);
					if (Map.getloadedMaps().isEmpty() || Map.getallMapnames() == null){
						p.sendMessage(Smash.pr + SM.Command_Error_NoMaps);
						return true;
					}
					for (Map m : Map.getloadedMaps()){
						p.sendMessage(Smash.pr + SM.Command_Maps_list.toString().replaceAll("%MAPNAME%", m.getName()));
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("games")){
					p.sendMessage(Smash.pr + SM.Command_Games_1);
					if (Game.getrunningGames().size() == 0){
						p.sendMessage(Smash.pr + SM.Command_Error_NoGames);
						return true;
					}
					for (Game g1 : Game.getrunningGames()){
						p.sendMessage(Smash.pr + SM.Command_Games_list.toString().replaceAll("%MAPNAME%", g1.getMap().getName()).replaceAll("%GAMESTATE%", g1.getGameState().getName()));
					}
					return true;
				}
				for (Game g : Game.getrunningGames()){
					if (g.containsPlayer(p)){
						if (args[1].equalsIgnoreCase("addPercents")) {
							if(args.length == 3) {
								try {
									g.getPlayerData(p).addDamage(Integer.parseInt(args[2]));
									PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
									return true;
								} catch(NumberFormatException ex) {


								}
								p.sendMessage(Smash.pr + SM.Command_Error_wrongParameters);
							} else {
								g.getPlayerData(p).addDamage(100);
								PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
							}
							return true;
						}
						if (args[1].equalsIgnoreCase("removePercents")) {
							if(args.length == 3) {
								try {
									g.getPlayerData(p).removeDamage(Integer.parseInt(args[2]));
									PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
									return true;
								} catch(NumberFormatException ex) {


								}
								p.sendMessage(Smash.pr + SM.Command_Error_wrongParameters);
							} else {
								g.getPlayerData(p).removeDamage(100);
								PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
							}
							return true;
						}
						if (args[1].equalsIgnoreCase("setPercents")) {
							if(args.length == 3) {
								try {
									g.getPlayerData(p).setDamage(Integer.parseInt(args[2]));
									PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
									return true;
								} catch(NumberFormatException ex) {


								}
								p.sendMessage(Smash.pr + SM.Command_Error_wrongParameters);
							} else {
								p.sendMessage(Smash.pr + SM.Command_Error_noParameters);
							}
							return true;
						}
					}
				}
			}
			p.sendMessage(Smash.pr + SM.Command_Error_Unavailable);
			return true;
			
		}
		return false;
	}

}
