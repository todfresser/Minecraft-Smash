package todfresser.smash.signs;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

import todfresser.smash.map.Game;

public class SignJ {
	
	private Sign s;
	private Game g;
	public UUID Creator;
	
	public boolean exists(){
		if (s.getWorld().getBlockAt(s.getLocation()).getType().equals(Material.SIGN_POST) || s.getWorld().getBlockAt(s.getLocation()).getType().equals(Material.WALL_SIGN)){
			return true;
		}
		removeSign();
		return false;
	}
	
	public void updateBlock(Location l){
		String l1 = s.getLine(0);
		String l2 = s.getLine(1);
		String l3 = s.getLine(2);
		String l4 = s.getLine(3);
		this.s = (Sign) l.getBlock().getState();
		write(l1, l2, l3, l4);
	}
	
	public SignJ(Location l){
		this.s = (Sign) l.getBlock().getState();
		SignManager.signs.add(this);
	}
	
	public void addSigntoGame(Game g){
		if (exists()) {
			this.g = g;
		}
	}
	public void removeGame(){
		this.g = null;
		write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
								ChatColor.BLUE + "Neues", 
								ChatColor.BLUE + "Spiel",
								ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
	}
	public boolean hasGame(){
		return g != null;
	}
	
	public Location getLocation(){
		return s.getLocation();
	}
	
	public Game getGame(){
		return g;
	}
	
	public void write(String l1, String l2, String l3, String l4){
		if (exists()) {
			s.setLine(0, l1);
			s.setLine(1, l2);
			s.setLine(2, l3);
			s.setLine(3, l4);
			s.update();
		}
	}
	
	public String getLine(int index){
		return s.getLine(index);
	}
	
	private void removeSign(){
		SignManager.signs.remove(this);
	}
}
