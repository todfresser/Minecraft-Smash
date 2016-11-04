package todfresser.smash.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import multiworld.MultiWorldPlugin;
import multiworld.api.MultiWorldWorldData;
import multiworld.api.flag.FlagName;
import todfresser.smash.extrafunctions.DynamicClassFunctions;
import todfresser.smash.items.BlackHole;
import todfresser.smash.items.FireStick;
import todfresser.smash.items.GoldenApple;
import todfresser.smash.items.ItemManager;
import todfresser.smash.items.JetPack;
import todfresser.smash.items.MagicStaff;
import todfresser.smash.items.RocketLauncher;
import todfresser.smash.items.Smasher;
import todfresser.smash.items.SpeedItem;
import todfresser.smash.items.Steak;
import todfresser.smash.items.TripleBow;
import todfresser.smash.map.Game;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;
import todfresser.smash.map.events.BlockBreak;
import todfresser.smash.map.events.ChatEvent;
import todfresser.smash.map.events.CommandReprocess;
import todfresser.smash.map.events.DamageEvent;
import todfresser.smash.map.events.DropItem;
import todfresser.smash.map.events.FlyToggleEvent;
import todfresser.smash.map.events.InteractEvent;
import todfresser.smash.map.events.PickupItemEvent;
import todfresser.smash.map.events.RegenerationEvents;
import todfresser.smash.map.events.SwapHandItem;
import todfresser.smash.map.events.PlayerQuit;
import todfresser.smash.map.events.PlayerShootBow;
import todfresser.smash.signs.SignManager;

public class Smash extends JavaPlugin{
	
	private static Smash instance;
	public static Smash getInstance(){
		return instance;
	}
	
	//public static MultiWorldAPI multiworld = null;
	
	public static String pr;
	
	@Override
	public void onEnable(){
		/*if (getServer().getPluginManager().getPlugin("MultiWorld") != null){
			getServer().getConsoleSender().sendMessage(org.bukkit.ChatColor.DARK_GREEN + "[Smash] Das Plugin Multiworld wurde gefunden!");
		}*/
		//saveDefaultConfig();
		pr = SM.Prefix.toString();
		
		if (!DynamicClassFunctions.setPackages()) {
			System.out.println("NMS/OBC package could not be detected, using " + DynamicClassFunctions.nmsPackage + " and " + DynamicClassFunctions.obcPackage);
		}
		DynamicClassFunctions.setClasses();
		DynamicClassFunctions.setMethods();
		DynamicClassFunctions.setFields();
		
		getServer().getPluginManager().registerEvents(new SignManager(), this);
		getServer().getPluginManager().registerEvents(new MapEditor(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new RegenerationEvents(), this);
		getServer().getPluginManager().registerEvents(new FlyToggleEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getPluginManager().registerEvents(new PickupItemEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerShootBow(), this);
		getServer().getPluginManager().registerEvents(new DropItem(), this);
		getServer().getPluginManager().registerEvents(new SwapHandItem(), this);
		getServer().getPluginManager().registerEvents(new CommandReprocess(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		
		ItemManager.registerItem(new SpeedItem());
		ItemManager.registerItem(new TripleBow());
		ItemManager.registerItem(new Smasher());
		ItemManager.registerItem(new FireStick());
		ItemManager.registerItem(new RocketLauncher());
		ItemManager.registerItem(new JetPack());
		ItemManager.registerItem(new MagicStaff());
		ItemManager.registerItem(new BlackHole());
		ItemManager.registerItem(new Steak());
		ItemManager.registerItem(new GoldenApple());
		
		instance = this;
		Map.deleteAllWorlds();
		this.getCommand("smash").setExecutor(new SmashCommands());
		
		Map.deleteAllWorlds();
		for (String name : Map.getallMapnames()){
			new Map(name);
		}
		SignManager.loadSigns();
	}
	
	@Override
	public void onDisable(){
		SignManager.save();
		for (Game g : Game.getrunningGames()){
			g.delete(false);
		}
		Game.getrunningGames().clear();
	}
	
	public static void unloadMultiWorldWorld(World w){
		if (Bukkit.getPluginManager().getPlugin("MultiWorld").isEnabled()){
			MultiWorldWorldData d = ((MultiWorldPlugin) Bukkit.getPluginManager().getPlugin("MultiWorld")).getApi().getWorld(w.getName());
			try {
				d.setOptionValue(FlagName.SAVEON, false);
				d.unloadWorld();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
