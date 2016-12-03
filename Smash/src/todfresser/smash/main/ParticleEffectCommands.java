package todfresser.smash.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import todfresser.smash.particles.ParticleEffect;

public class ParticleEffectCommands implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) sender;
		System.out.println(cmd);
		
		//if (cmd.equals("particle")){
			System.out.println("4");
			if (args.length == 6){
				System.out.println("2");
				for (ParticleEffect e : ParticleEffect.values()){
					if (e.name().equals(args[0])){
						float offsetX = Float.parseFloat(args[1]);
						float offsetY = Float.parseFloat(args[2]);
						float offsetZ = Float.parseFloat(args[3]);
						float speed = Float.parseFloat(args[4]);
						int amount = Integer.parseInt(args[5]);
						e.display(offsetX, offsetY, offsetZ, speed, amount, p.getLocation(), 80);
						System.out.println("4");
						return true;
					}
				}
			}
			else if (args.length == 1){
				p.performCommand("particle " + args[0] + " 0.5 0.5 0.5 0.5 4");
				System.out.println("1");
				return true;
			}
		//}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)){
			System.out.println("[Smash] Du musst ein Spieler sein!");
			return null;
		}
		
		if (cmd.getName().equalsIgnoreCase("particle")){
			if (args.length == 1){
				ArrayList<String> particleNames = new ArrayList<>();
				for (ParticleEffect e : ParticleEffect.values()){
					if (e.name().toLowerCase().startsWith(args[0].toLowerCase()) && !e.name().equals(args[0])){
						if (e.isSupported() && !e.requiresData() ) particleNames.add(e.name());
					}
				}
				return particleNames;
			}
		}
		return null;
	}

}
