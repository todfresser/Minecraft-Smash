package todfresser.smash.main;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.game.Game;
import todfresser.smash.game.GameManager;
import todfresser.smash.game.Map;
import todfresser.smash.game.MapEditor;

public class SmashCommands implements CommandExecutor{
	
	//private static String st = ChatColor.GOLD + "-------" + ChatColor.BLUE + ChatColor.BOLD + " [" + ChatColor.AQUA + ChatColor.BOLD + "Smash" + ChatColor.BLUE + ChatColor.BOLD + "] " + ChatColor.GOLD + "-------";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		
		
		
		if (cmd.getName().equalsIgnoreCase("smash") || cmd.getName().equalsIgnoreCase("sm")){
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
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("start")){
				if (p.hasPermission("SMASH.admin")){
					for (Game g : GameManager.getrunningGames()){
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
				for (Game g : GameManager.getrunningGames()){
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
			if (p.hasPermission("SMASH.admin")){
                if (args[0].equalsIgnoreCase("edit")){
                    if (args.length == 3){
                        MapEditor.open(p, args[1], args[2]);
                    }else{
                        p.sendMessage(Smash.pr + "Usage: /smash edit <mapname> <maptype>");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("maps")){
                    p.sendMessage(Smash.pr + SM.Command_Maps_1);
                    if (GameManager.getloadedMaps().isEmpty()){
                        p.sendMessage(Smash.pr + SM.Command_Error_NoMaps);
                        return true;
                    }
                    for (Map m : GameManager.getloadedMaps()){
                        p.sendMessage(Smash.pr + SM.Command_Maps_list.toString().replaceAll("%MAPNAME%", m.getName()));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("games")){
                    p.sendMessage(Smash.pr + SM.Command_Games_1);
                    if (GameManager.getrunningGames().size() == 0){
                        p.sendMessage(Smash.pr + SM.Command_Error_NoGames);
                        return true;
                    }
                    for (Game g1 : GameManager.getrunningGames()){
                        p.sendMessage(Smash.pr + SM.Command_Games_list.toString().replaceAll("%MAPNAME%", g1.getMap().getName()).replaceAll("%GAMESTATE%", g1.getGameState().getName()));
                    }
                    return true;
                }
                for (Game g : GameManager.getrunningGames()){
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
