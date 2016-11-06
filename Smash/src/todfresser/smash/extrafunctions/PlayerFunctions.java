package todfresser.smash.extrafunctions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PlayerConnection;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import todfresser.smash.main.Smash;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class PlayerFunctions {
	public static void deleteBlocksNearPlayer(Player p, SmashPlayerData data){
		Vector v = data.direction;
		int playerX = p.getLocation().getBlockX();
		int playerZ = p.getLocation().getBlockZ();
		Block b;
		World w = p.getWorld();
		int x, x2 = 0, x3 = 0, z, z2 = 0, z3 = 0, y;
		if (v.getX() < -0.1){
			x = -1; 
		}else if (v.getX() > 0.1){
			x = 1;
		}else x = 0;
		y = p.getLocation().getBlockY();
		if (v.getZ() < -0.1){
			z = -1;
			if (x == 0){
				z2 = z;
				z3 = z;
				x2 = -1;
				x3 = 1;
			}
			if (x != 0){
				z2 = -1;
				z3 = 0;
				x2 = 0;
				x3 = x;
			}
		}else if (v.getZ() > 0.1){
			z = 1;
			if (x == 0){
				z2 = z;
				z3 = z;
				x2 = -1;
				x3 = 1;
			}
			if (x != 0){
				z2 = 1;
				z3 = 0;
				x2 = 0;
				x3 = x;
			}
		}else{
			z = 0;
			if (x == 0) return;
			if (x != 0){
				z2 = 1;
				z3 = -1;
				x2 = x;
				x3 = x;
			}
		}
		for (int i = 0; i < 3; i++){
			b = w.getBlockAt(x + playerX, y + i, z + playerZ);
			if (!b.getType().equals(Material.AIR)) b.setType(Material.AIR);
			b = w.getBlockAt(x2 + playerX, y + i, z2 + playerZ);
			if (!b.getType().equals(Material.AIR)) b.setType(Material.AIR);
			b = w.getBlockAt(x3 + playerX, y + i, z3 + playerZ);
			if (!b.getType().equals(Material.AIR)) b.setType(Material.AIR);
		}
		
	}
	public static void playOutDamage(Game g, Player p, Vector velocity, int damage){
		playDamageAnimation(p, g);
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		p.setVelocity(velocity.multiply(((double)g.getPlayerData(p).getDamage()/200) + 0.9));
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				g.getPlayerData(p).direction = velocity;
				
			}
		}.runTaskLater(Smash.getInstance(), 4);
	}
	public static void playOutDamage(Game g, Player p, Player damager, int damage){
		playDamageAnimation(p, g);
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		g.getPlayerData(damager).addDamageDone((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	public static void playOutDamage(Game g, Player p, int damage){
		playDamageAnimation(p, g);
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
	}
	public static void playOutDamage(Game g, Player p, Player damager, Vector velocity, int damage){
		playDamageAnimation(p, g);
		if (damage > 0) g.getPlayerData(p).addDamage((int) damage);
		p.setVelocity(velocity.multiply(((double)g.getPlayerData(p).getDamage()/200) + 0.9));
		if (damage > 0) g.getPlayerData(damager).addDamageDone((int) damage);
		if (damage > 0) PlayerFunctions.updateDamageManually(p.getUniqueId(), g);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				g.getPlayerData(p).direction = velocity;
				
			}
		}.runTaskLater(Smash.getInstance(), 4);
	}
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
	  {
	    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
	    
	    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
	    connection.sendPacket(packetPlayOutTimes);
	    if (subtitle != null)
	    {
	      subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
	      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
	      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
	      connection.sendPacket(packetPlayOutSubTitle);
	    }
	    if (title != null)
	    {
	      title = title.replaceAll("%player%", player.getDisplayName());
	      title = ChatColor.translateAlternateColorCodes('&', title);
	      IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
	      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
	      connection.sendPacket(packetPlayOutTitle);
	    }
	  }
	
	public static void playDamageAnimation(Player target, Game g){
		EntityPlayer player = ((CraftPlayer) target).getHandle();
		PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(player, (byte) 2);
		for (UUID id : g.getAllPlayers()){
			EntityPlayer p = ((CraftPlayer) Bukkit.getPlayer(id)).getHandle();
			p.playerConnection.sendPacket(status);
		}
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
