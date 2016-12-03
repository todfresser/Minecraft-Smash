package todfresser.smash.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SoundCommands implements CommandExecutor, TabCompleter{
	
	private static HashMap<UUID, ArrayList<String>> tabs = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		
		
		
		if (cmd.getName().equalsIgnoreCase("sound")){
			if (args.length == 3){
				String name = args[0];
				float volume = Float.parseFloat(args[1]);
				float pitch = Float.parseFloat(args[2]);
				for (Sound s : Sound.values()){
					if (s.name().equals(name)){
						p.getLocation().getWorld().playSound(p.getLocation(), s, volume, pitch);
					}
				}
				return true;
			}
			else if (args.length == 1){
				p.performCommand("sound " + args[0] + " 1 1");
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return null;
		}
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("sound")){
			if (args.length < 1){
				ArrayList<String> soundNames = new ArrayList<>();
				for (Sound s : Sound.values()){
					soundNames.add(s.name());
				}
				return soundNames;
			}
			if (args.length == 1){
				ArrayList<String> soundNames = new ArrayList<>();
				for (Sound s : Sound.values()){
					if (s.name().toLowerCase().startsWith(args[0].toLowerCase()) && !s.name().equals(args[0])) soundNames.add(s.name());
				}
				if (tabs.containsKey(p.getUniqueId())){
					if (soundNames.size() == 0){
						return tabs.get(p.getUniqueId());
					}else{
						tabs.remove(p.getUniqueId());
					}
				}
				tabs.put(p.getUniqueId(), soundNames);
				return soundNames;
			}
					
		}
		return new ArrayList<>();
	}

}
