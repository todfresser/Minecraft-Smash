package todfresser.smash.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import todfresser.smash.map.Game;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;

public class SmashCommands implements CommandExecutor{
	
	//private static String st = ChatColor.GOLD + "-------" + ChatColor.BLUE + ChatColor.BOLD + " [" + ChatColor.AQUA + ChatColor.BOLD + "Smash" + ChatColor.BLUE + ChatColor.BOLD + "] " + ChatColor.GOLD + "-------";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player) == false){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		
		
		
		if (cmd.getName().equalsIgnoreCase("smash") || cmd.getName().equalsIgnoreCase("sm")){
			if (args.length == 0){
				p.sendMessage(Smash.pr + SM.Command_Help_1);
				p.sendMessage(Smash.pr + SM.Command_Help_2);
				p.sendMessage(Smash.pr + SM.Command_Help_3);
				p.sendMessage(Smash.pr + SM.Command_Help_4);
				if (p.hasPermission("SMASH.admin")){
					p.sendMessage(Smash.pr + SM.Command_Help_5);
					p.sendMessage(Smash.pr + SM.Command_Help_6);
				}
				return true;
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
					p.sendMessage(SM.Command_Stats_1.toString());
					p.sendMessage(SM.Command_Stats_2 + Integer.toString(Statistics.getPlayedGames(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_3 + Integer.toString(Statistics.getTotalWins(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_4 + Integer.toString(Statistics.getKills(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_5 + Integer.toString(Statistics.getDeaths(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_6 + Integer.toString(Statistics.getDamageDone(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_7 + Integer.toString(Statistics.getTotalDamage(p.getUniqueId())));
					p.sendMessage(SM.Command_Stats_8.toString());
					return true;
				}else if (args[1].equalsIgnoreCase("reset")) {
					Statistics.delete(p.getUniqueId());
					p.sendMessage(Smash.pr + SM.Command_Stats_Reset);
					return true;
				}
			}
			
			
			
			
			if (args[0].equalsIgnoreCase("edit")){
				if (p.hasPermission("SMASH.admin") == false){
					p.sendMessage(Smash.pr + SM.Command_Error_NotEnoughRights);
					return true;
				}
				if (args.length == 2){
					MapEditor.open(p, args[1]);
				}else{
					p.sendMessage(Smash.pr + "Usage: /smash edit <mapname>");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("maps")){
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
			if (args[0].equalsIgnoreCase("games")){
				p.sendMessage(Smash.pr + SM.Command_Games_1);
				if (Game.getrunningGames().size() == 0){
					p.sendMessage(Smash.pr + SM.Command_Error_NoGames);
					return true;
				}
				for (Game g : Game.getrunningGames()){
					p.sendMessage(Smash.pr + SM.Command_Games_list.toString().replaceAll("%MAPNAME%", g.getMap().getName()).replaceAll("%GAMESTATE%", g.getGameState().getName()));
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
						p.sendMessage(Smash.pr + SM.Command_leave);
						return true;
					}
				}
				p.sendMessage(Smash.pr + SM.Command_Error_InNoGame);
				return true;
			}
			p.sendMessage(Smash.pr + SM.Command_Error_Unavailable);
			return true;
			
		}
		return false;
	}

}
