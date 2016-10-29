package todfresser.smash.extrafunctions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import todfresser.smash.map.Game;

public class PlayerFunctions {
	/*public static void deleteBlocksNearPlayer(Player p, int chance){
		Location l = p.getLocation().add(0, 0.1, 0);
		Block b;
		World w = p.getWorld();
		Random r = new Random();
		for (int x = -1; x <= 1; x++){
			for (int z = -1; z <= 1; z++){
				b = w.getBlockAt(l.getBlockX() + x, l.getBlockY(), l.getBlockZ() + z);
				if (!b.getType().equals(Material.AIR)){
					if (r.nextInt(chance + 1) == 1){
						b.setType(Material.AIR);
						w.spigot().playEffect(b.getLocation(), Effect.TILE_BREAK, 2, 2, 0.1f, 0.1f, 0.1f, 1, 5, 20);
					}
				}
				b = w.getBlockAt(l.getBlockX() + x, l.getBlockY() + 1, l.getBlockZ() + z);
				if (!b.getType().equals(Material.AIR)){
					if (r.nextInt(chance + 1) == 1){
						b.setType(Material.AIR);
						w.spigot().playEffect(b.getLocation(), Effect.TILE_BREAK, 2, 2, 0.1f, 0.1f, 0.1f, 1, 5, 20);
					}
				}
			}
		}
	}*/
	public static void playOutDamage(Game g, Player p, Vector velocity, int damage){
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		p.setVelocity(velocity);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	public static void playOutDamage(Game g, Player p, Player damager, int damage){
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		g.getPlayerData(damager).addDamageDone((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	public static void playOutDamage(Game g, Player p, int damage){
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	public static void playOutDamage(Game g, Player p, Player damager, Vector velocity, int damage){
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		p.setVelocity(velocity);
		if (damage > 0) g.getPlayerData(damager).addDamageDone((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	
	public static void sendActionBar(Player p, String message){
		if(message == null) message = "";
		message = ChatColor.translateAlternateColorCodes('&', message);
		message = message.replaceAll("%Player%", p.getDisplayName());
		
		PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;
		
		IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
		
		con.sendPacket(packet);
	}
	
	/*public static void sendJsonMessage(Player p, String firstmessage, String function1, ChatColor function1C, String command1, String hover1, String function2, ChatColor function2C, String command2, String hover2, String function3, ChatColor function3C, String command3, String hover3){
		ComponentBuilder b = new ComponentBuilder(firstmessage).color(ChatColor.GRAY);
		if (function1 != null){
			b.append(function1).color(function1C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command1)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover1).color(ChatColor.GRAY).create()));
		}
		if (function2 != null){
			b.append(function2).color(function2C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command2)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover2).color(ChatColor.GRAY).create()));
		}
		if (function3 != null){
			b.append(function3).color(function3C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command3)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover3).color(ChatColor.GRAY).create()));
		}
		p.spigot().sendMessage(b.create());
	}
	public static void sendJsonMessage(Player p, String firstmessage, String function1, ChatColor function1C, String command1, String hover1, String function2, ChatColor function2C, String command2, String hover2){
		ComponentBuilder b = new ComponentBuilder(firstmessage).color(ChatColor.GRAY);
		if (function1 != null){
			b.append(function1).color(function1C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command1)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover1).color(ChatColor.GRAY).create()));
		}
		if (function2 != null){
			b.append(function2).color(function2C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command2)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover2).color(ChatColor.GRAY).create()));
		}
		p.spigot().sendMessage(b.create());
	}
	public static void sendJsonMessage(Player p, String firstmessage, String function1, ChatColor function1C, String command1, String hover1){
		ComponentBuilder b = new ComponentBuilder(firstmessage).color(ChatColor.GRAY);
		if (function1 != null){
			b.append(function1).color(function1C).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command1)).event(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder(hover1).color(ChatColor.GRAY).create()));
		}
		p.spigot().sendMessage(b.create());
	}*/
	
	
	public static void updateDamageManually(UUID PlayerID, Game g){
		if (g.board == null){
			g.board = Bukkit.getScoreboardManager().getNewScoreboard();
			//Damage:
			Objective o = g.board.registerNewObjective("damage", "level");
			o.setDisplayName("%");
			o.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		Bukkit.getPlayer(PlayerID).setLevel(g.getPlayerData(PlayerID).getDamage());
	}
	
	
	public static void sendDamageScoreboard(Game g){
		if (g.board == null){
			g.board = Bukkit.getScoreboardManager().getNewScoreboard();
			//Damage:
			Objective o = g.board.registerNewObjective("damage", "level");
			o.setDisplayName("%");
			o.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		Objective obj;
		if (g.board.getObjective(DisplaySlot.SIDEBAR) == null){
			obj = g.board.registerNewObjective("aaa", "bbb");
		}else{
			obj = g.board.getObjective(DisplaySlot.SIDEBAR);
			obj.unregister();
			obj = g.board.registerNewObjective("aaa", "bbb");
		}
		obj.setDisplayName("Spieler");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
		for(UUID id : g.getAllPlayers()){
			Score s = obj.getScore(Bukkit.getPlayer(id).getName());
			s.setScore(g.getPlayerData(id).getLives());
		}
		for (UUID id : g.getAllPlayers()){
			Bukkit.getPlayer(id).setScoreboard(g.board);
		}
	}
	
	
	public static void removeScoreboard(Player p){
    	try{
        	p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    	}catch(NullPointerException e){
    	}
    }
}
