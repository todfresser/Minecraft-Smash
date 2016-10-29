package todfresser.smash.map;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import todfresser.smash.map.MapEditorItems.EditorInventoryType;

public class MapEditorData {
	public static String cs = ChatColor.GOLD + "-------" + ChatColor.BLUE + ChatColor.BOLD + " [" + ChatColor.AQUA + ChatColor.BOLD + "MapCreator" + ChatColor.BLUE + ChatColor.BOLD + "] " + ChatColor.GOLD + "-------";
	public UUID SpielerID;
	public Inventory Inv;
	
	public String name;
	public boolean exists;
	public int time;
	public String worldtoCopy = null;
	public Location spectatorspawn;
	public Location leavepoint;
	public Location lobbyspawnpoint;
	public ArrayList<Location> playerspawns;
	public ArrayList<Location> itemspawns;
	public Location leavesign;
	public Location itemsign;
	public Location livesign;
	
	private Inventory setupInventory(){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY + "Map: " + ChatColor.GOLD + name);
		return inv;
	}
	/*public void updateScoreboard(){
		
	}*/
	
	public MapEditorData(UUID id, String name){
		this.SpielerID = id;
		this.name = name;
		this.exists = false;
		this.worldtoCopy = Bukkit.getPlayer(id).getWorld().getName();
		this.Inv = setupInventory();
		this.playerspawns = new ArrayList<>();
		this.itemspawns = new ArrayList<>();
		MapEditorItems.setInventory(this, EditorInventoryType.SAVEDELETE);
	}
	
	public MapEditorData(UUID id, Map m){
		this.SpielerID = id;
		this.name = m.getName();
		this.exists = true;
		this.Inv = setupInventory();
		World w = Bukkit.getWorld(m.getWorldtoCopy().getName());
		this.spectatorspawn = m.getSpectatorSpawnPoint(w);
		this.leavepoint = m.getLeavePoint();
		this.lobbyspawnpoint = m.getLobbySpawnPoint(w);
		this.playerspawns = m.getPlayerSpawns(w);
		this.itemspawns = m.getItemSpawns(w);
		this.worldtoCopy = m.getWorldtoCopy().getName();
		if (m.getleaveSign(w) != null) this.leavesign = m.getleaveSign(w).getLocation();
		if (m.getItemSign(w) != null) this.itemsign = m.getItemSign(w).getLocation();
		if (m.getLiveSign(w) != null) this.livesign = m.getLiveSign(w).getLocation();
		MapEditorItems.setInventory(this, EditorInventoryType.SAVEDELETE);
	}
	
	public void create(){
		Map m;
		if (Map.getallMapnames().contains(name)){
			m = Map.getMapfromString(name);
			for (Game g : Game.getrunningGames()){
				if (g.getMap().getName().equals(name)){
					g.delete();
				}
			}
		}else m = new Map(name);
		m.create(worldtoCopy, time, spectatorspawn, lobbyspawnpoint, leavepoint, playerspawns, itemspawns, leavesign.toVector(), itemsign.toVector(), livesign.toVector());
	}
	
}
