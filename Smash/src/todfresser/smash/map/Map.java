package todfresser.smash.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import todfresser.smash.extrafunctions.DynamicClassFunctions;
import todfresser.smash.main.Smash;

public class Map {
	private final String name;
	private FileConfiguration cfg;
	
	private static ArrayList<Map> maps = new ArrayList<>();
	
	public static void deleteAllWorlds(){
		String wn;
		for (World w : Bukkit.getWorlds()){
			if (w.getName().startsWith("Smash_Map_")){
				wn = w.getName();
				DynamicClassFunctions.bindRegionFiles();
				DynamicClassFunctions.forceUnloadWorld(w);
				DynamicClassFunctions.clearWorldReference(wn);
			}
		}
		File folder = Smash.getInstance().getServer().getWorldContainer();
		if (folder.list().length != 0){
			String[] fileNames = folder.list();
			for(int i = 0; i < fileNames.length; i++){
				if (fileNames[i].startsWith("Smash_Map_")){
					File file = new File(fileNames[i]);
					try {
						FileUtils.deleteDirectory(file);
						System.out.println("[Smash] Die Welt " + fileNames[i].replace(".yml", "") + " wurde gel�scht!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Map(String name){
		this.name = name;
	    File file = new File("plugins/Smash/Maps", name + ".yml");
		if (file.exists()){
			cfg = YamlConfiguration.loadConfiguration(file);
			maps.add(this);
		}else{
			cfg = null;
		}
	}
	
	public int getTypeID(){
		return cfg.getInt("material");
	}
	
	public void delete(){
		maps.remove(this);
		for (Game g : Game.getrunningGames()){
			if (g.getMap().getName().equals(name)){
				g.delete(true);
			}
		}
		File file = new File("plugins/Smash/Maps", name + ".yml");
		file.delete();
	}
	
	public static Map getMapfromString(String name){
		for (Map m : maps){
			if (m.getName().equals(name)){
				return m;
			}
		}
		return null;
	}
	public boolean exists(){
		return (cfg != null);
	}
	
	public static ArrayList<Map> getloadedMaps(){
		return maps;
	}
	
	public static ArrayList<String> getallMapnames(){
		ArrayList<String> l = new ArrayList<>();
		if (new File("plugins/Smash/Maps").exists()){
			File folder = new File("plugins/Smash/Maps");
			if (folder.list().length != 0){
				String[] fileNames = folder.list();
				for(int i = 0; i < fileNames.length; i++){
					l.add(fileNames[i].replace(".yml", ""));
				}
			}
		}
		return l;
		
	}
	
	public static List<String> getloadedMapNames(){
		List<String> s = new ArrayList<>();
		for (Map m : maps){
			s.add(m.getName());
		}
		return s;
	}

	public String getName() {	
		return name;
	}
	
	public Integer getmaxPlayers(){
		return cfg.getInt("maxplayers");
	}
	
	public Location getSpectatorSpawnPoint(World w){
		String[] s = cfg.getString("spectatorspawn").split(",");
		Location l = new Location(w, Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		l.setYaw(Float.parseFloat(s[3]));
		return l;
	}
	
	public Location getLobbySpawnPoint(World w){
		String[] s = cfg.getString("lobbyspawnpoint").split(",");
		Location l = new Location(w, Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
		l.setYaw(Float.parseFloat(s[3]));
		return l;
	}
	
	public Location getLeavePoint(){
		String[] s = cfg.getString("leavepoint").split(",");
		Location l = new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
		l.setYaw(Float.parseFloat(s[4]));
		return l;
	}
	
	public ArrayList<Location> getPlayerSpawns(World w){
		String[] spawns = cfg.getString("Playerspawns").split("#");
		ArrayList<Location> l = new ArrayList<>();
		for (String loc : spawns){
			if ((loc.equals("")) || (loc == null)) continue;
			String[] s = loc.split(",");
			Location L = new Location(w,Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
			L.setYaw(Float.parseFloat(s[3]));
			l.add(L);
		}
		return l;
	}
	
	public ArrayList<Location> getItemSpawns(World w){
		String[] spawns = cfg.getString("Itemspawns").split("#");
		ArrayList<Location> locs = new ArrayList<>();
		for (String loc : spawns){
			if ((loc.equals("")) || (loc == null)) continue;
			String[] s = loc.split(",");
			locs.add(new Location(w, Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
		}
		return locs;
	}

	public ArrayList<Location> getTeleporters(World w){
        ArrayList<Location> locs = new ArrayList<>();
        if (cfg.contains("Teleporters")){
            String[] spawns = cfg.getString("Teleporters").split("#");
            if (spawns.length < 2) return locs;
            for (String loc : spawns){
                if ((loc.equals("")) || (loc == null)) continue;
                String[] s = loc.split(",");
                locs.add(new Location(w, Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
            }
        }
        return locs;
    }

	public void create(String worldtoCopy, int time, int itemID, Location spectatorspawn, Location lobbyspawnpoint, Location leavepoint, ArrayList<Location> spawnpoints, ArrayList<Location> itemspawns, ArrayList<Location> teleporters, Vector leavesign, Vector itemsign, Vector itemchancesign, Vector livesign, Vector eventsign){
		File file = new File("plugins/Smash/Maps", name + ".yml");
		String signS = null;
		
		boolean first = false;
		
		if (cfg != null){
			if (cfg.contains("sign")){
				signS = cfg.getString("sign");
			}
		}else first = true;
		
		if (file.exists()) file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cfg = YamlConfiguration.loadConfiguration(file);
		if (signS != null) cfg.set("sign", signS);
		cfg.set("World_to_Copy", worldtoCopy);
		cfg.set("Time", time);
		cfg.set("maxplayers", spawnpoints.size());
		cfg.set("material", itemID);
		
		cfg.set("spectatorspawn", spectatorspawn.getX() + "," + spectatorspawn.getY() + "," + spectatorspawn.getZ() + "," + spectatorspawn.getYaw());
		cfg.set("lobbyspawnpoint", lobbyspawnpoint.getX() + "," + lobbyspawnpoint.getY() + "," + lobbyspawnpoint.getZ() + "," + lobbyspawnpoint.getYaw());
		cfg.set("leavepoint", leavepoint.getWorld().getName() + "," + leavepoint.getX() + "," + leavepoint.getY() + "," + leavepoint.getZ() + "," + leavepoint.getYaw());
		
		cfg.set("Signs.leave", leavesign.getBlockX() + "," + leavesign.getBlockY() + "," + leavesign.getBlockZ());
		cfg.set("Signs.items", itemsign.getBlockX() + "," + itemsign.getBlockY() + "," + itemsign.getBlockZ());
		cfg.set("Signs.itemchance", itemchancesign.getBlockX() + "," + itemchancesign.getBlockY() + "," + itemchancesign.getBlockZ());
		cfg.set("Signs.live", livesign.getBlockX() + "," + livesign.getBlockY() + "," + livesign.getBlockZ());
		cfg.set("Signs.events", eventsign.getBlockX() + "," + eventsign.getBlockY() + "," + eventsign.getBlockZ());
		
		StringBuilder sb1 = new StringBuilder();
		for (Location l : spawnpoints){
			sb1.append(l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getYaw() + "#");
		}
		cfg.set("Playerspawns", sb1.toString());
		
		StringBuilder sb2 = new StringBuilder();
		for (Location l : itemspawns){
			sb2.append(l.getX() + "," + l.getY() + "," + l.getZ() + "#");
		}
		cfg.set("Itemspawns", sb2.toString());

        StringBuilder sb3 = new StringBuilder();
        for (Location l : teleporters){
            sb3.append(l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getYaw() + "#");
        }
        cfg.set("Teleporters", sb3.toString());
		
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sb1 = null;
		sb2 = null;
		
		if (first){
			//Bukkit.getWorld(worldtoCopy).save();
			maps.add(this);
		}
		Bukkit.getWorld(worldtoCopy).save();
		
		System.out.println("[Smash] Die Map " + name + " wurde erfolgreich erstellt.");
		
		//Init
	}
	
	public File getWorldtoCopy(){
		if (Bukkit.getWorld(cfg.getString("World_to_Copy")) == null) new WorldCreator(cfg.getString("World_to_Copy")).createWorld();
		return Bukkit.getWorld(cfg.getString("World_to_Copy")).getWorldFolder();
	}
	
	public Sign getleaveSign(World w){
		String[] s = cfg.getString("Signs.leave").split(",");
		Location l = new Location(w, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		if (l.getBlock().getType().equals(Material.SIGN_POST) || l.getBlock().getType().equals(Material.WALL_SIGN)){
			return (Sign) l.getBlock().getState();
		}else{
			System.out.println("[Smash] Das Schild zum Verlassen existiert in der Welt " + getWorldtoCopy().getName() + " nicht mehr!");
			return null;
		}
	}
	public Sign getItemSign(World w){
		String[] s = cfg.getString("Signs.items").split(",");
		Location l = new Location(w, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		if (l.getBlock().getType().equals(Material.SIGN_POST) || l.getBlock().getType().equals(Material.WALL_SIGN)){
			return (Sign) l.getBlock().getState();
		}else{
			System.out.println("[Smash] Das Schild zum Deaktivieren der Items existiert in der Welt " + getWorldtoCopy().getName() + " nicht mehr!");
			return null;
		}
	}
	public Sign getItemChanceSign(World w){
		String[] s = cfg.getString("Signs.itemchance").split(",");
		Location l = new Location(w, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		if (l.getBlock().getType().equals(Material.SIGN_POST) || l.getBlock().getType().equals(Material.WALL_SIGN)){
			return (Sign) l.getBlock().getState();
		}else{
			System.out.println("[Smash] Das Schild zum Einstellen der Item-Seltenheit existiert in der Welt " + getWorldtoCopy().getName() + " nicht mehr!");
			return null;
		}
	}
	public Sign getLiveSign(World w){
		String[] s = cfg.getString("Signs.live").split(",");
		Location l = new Location(w, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		if (l.getBlock().getType().equals(Material.SIGN_POST) || l.getBlock().getType().equals(Material.WALL_SIGN)){
			return (Sign) l.getBlock().getState();
		}else{
			System.out.println("[Smash] Das Schild um die Anzahl der Leben zu ver�ndern existiert in der Welt " + getWorldtoCopy().getName() + " nicht mehr!");
			return null;
		}
	}
	public Sign getEventSign(World w){
		String[] s = cfg.getString("Signs.events").split(",");
		Location l = new Location(w, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		if (l.getBlock().getType().equals(Material.SIGN_POST) || l.getBlock().getType().equals(Material.WALL_SIGN)){
			return (Sign) l.getBlock().getState();
		}else{
			System.out.println("[Smash] Das Schild zum Deaktivieren der Events existiert in der Welt " + getWorldtoCopy().getName() + " nicht mehr!");
			return null;
		}
	}
	
	public final World generateNewWorldandID(){
		int i = 0;
		File file = new File("Smash_Map_" + name + "_W_" + i);
		while (file.exists()){
			i++;
			file = new File("Smash_Map_" + name + "_W_" + i);
		}
		String wn = "Smash_Map_" + name + "_W_" + i;
		try{
			File worldfile = new File(wn);
			FileUtils.copyDirectory(getWorldtoCopy(), worldfile);
			File DATA = new File(wn, "uid.dat");
			DATA.delete();
			WorldCreator wc = new WorldCreator(wn);
			wc.createWorld();
			World w = Bukkit.getWorld(wn);
			w.setAutoSave(false);
			for (Entity e : w.getEntities()){
				if ((e.getType().equals(EntityType.PAINTING)) || (e.getType().equals(EntityType.PLAYER)) || (e.getType().equals(EntityType.ITEM_FRAME)) || (e.getType().equals(EntityType.MINECART_CHEST)) || (e.getType().equals(EntityType.MINECART_FURNACE)) || (e.getType().equals(EntityType.MINECART_HOPPER))) continue;
				e.remove();
			}
			w.setGameRuleValue("doDaylightCycle", "false");
			w.setGameRuleValue("doMobSpawning", "false");
			//w.setGameRuleValue("mobGriefing", "false");
			w.setGameRuleValue("randomTickSpeed", "0");
			w.setGameRuleValue("showDeathMessages", "false");
			w.setTime(cfg.getInt("Time"));
			w.setWeatherDuration(0);
			w.setStorm(false);
			w.setThundering(false);
			
			return w;
		}catch(Exception e){
			System.out.println("[Smash] Die Welt " + wn + " konnte nicht vollst�ndig erstellt werden.");
		}
		return null;
	}
	
	public void deleteWorld(World w){
		String wn = w.getName();
		try{
			File file = new File(wn);
			DynamicClassFunctions.bindRegionFiles();
            DynamicClassFunctions.forceUnloadWorld(w);
            DynamicClassFunctions.clearWorldReference(wn);
            FileUtils.deleteDirectory(file);
        	System.out.println("[Smash] Die Welt " + wn + " wurde gel�scht.");
		}catch(Exception e){
			System.out.println("[Smash] Die Welt " + wn + " konnte nicht vollst�ndig gel�scht werden.");
		}
		
	}
	
	
}
