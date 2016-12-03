package todfresser.smash.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import multiworld.MultiWorldPlugin;
import multiworld.api.MultiWorldWorldData;
import multiworld.api.flag.FlagName;
import todfresser.smash.extrafunctions.DynamicClassFunctions;
import todfresser.smash.items.BlackHole;
import todfresser.smash.items.Cloud;
import todfresser.smash.items.DiamondSword;
import todfresser.smash.items.FireStick;
import todfresser.smash.items.Freezer;
import todfresser.smash.items.GoldenApple;
import todfresser.smash.items.GoldenSword;
import todfresser.smash.items.Grenade;
import todfresser.smash.items.Hook;
import todfresser.smash.items.InstantTeleporter;
import todfresser.smash.items.IronSword;
import todfresser.smash.items.ItemGrabber;
import todfresser.smash.items.JetPack;
import todfresser.smash.items.MagicStaff;
import todfresser.smash.items.MolotovCocktail;
import todfresser.smash.items.RocketLauncher;
import todfresser.smash.items.Smasher;
import todfresser.smash.items.SmokeGrenade;
import todfresser.smash.items.Sniper;
import todfresser.smash.items.Snowball;
import todfresser.smash.items.SpeedItem;
import todfresser.smash.items.Steak;
import todfresser.smash.items.StoneSword;
import todfresser.smash.items.UltraBow;
import todfresser.smash.items.WoodenSword;
import todfresser.smash.items.main.ItemManager;
import todfresser.smash.map.Game;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;
import todfresser.smash.map.events.BlockBreak;
import todfresser.smash.map.events.ChangeBlock;
import todfresser.smash.map.events.ChatEvent;
import todfresser.smash.map.events.CommandReprocess;
import todfresser.smash.map.events.DamageEvent;
import todfresser.smash.map.events.DropItem;
import todfresser.smash.map.events.DropPotionEvent;
import todfresser.smash.map.events.FlyToggleEvent;
import todfresser.smash.map.events.HookEvent;
import todfresser.smash.map.events.InteractEvent;
import todfresser.smash.map.events.PhysikEvent;
import todfresser.smash.map.events.PickupItemEvent;
import todfresser.smash.map.events.RegenerationEvents;
import todfresser.smash.map.events.SwapHandItem;
import todfresser.smash.map.events.WeatherChange;
import todfresser.smash.map.events.PlayerQuit;
import todfresser.smash.map.events.PlayerShootBow;
import todfresser.smash.map.events.ProjectileThrowEvent;
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
		getServer().getPluginManager().registerEvents(new DropPotionEvent(), this);
		getServer().getPluginManager().registerEvents(new PhysikEvent(), this);
		getServer().getPluginManager().registerEvents(new HookEvent(), this);
		getServer().getPluginManager().registerEvents(new WeatherChange(), this);
		getServer().getPluginManager().registerEvents(new ProjectileThrowEvent(), this);
		getServer().getPluginManager().registerEvents(new ChangeBlock(), this);
		
		ItemManager.registerItem(new SpeedItem());
		ItemManager.registerItem(new UltraBow());
		ItemManager.registerItem(new Sniper());
		ItemManager.registerItem(new Smasher());
		ItemManager.registerItem(new FireStick());
		ItemManager.registerItem(new RocketLauncher());
		ItemManager.registerItem(new JetPack());
		ItemManager.registerItem(new MagicStaff());
		ItemManager.registerItem(new BlackHole());
		ItemManager.registerItem(new Steak());
		ItemManager.registerItem(new GoldenApple());
		ItemManager.registerItem(new WoodenSword());
		ItemManager.registerItem(new GoldenSword());
		ItemManager.registerItem(new StoneSword());
		ItemManager.registerItem(new IronSword());
		ItemManager.registerItem(new DiamondSword());
		ItemManager.registerItem(new MolotovCocktail());
		ItemManager.registerItem(new Freezer());
		ItemManager.registerItem(new Grenade());
		ItemManager.registerItem(new SmokeGrenade());
		ItemManager.registerItem(new Cloud());
		ItemManager.registerItem(new InstantTeleporter());
		ItemManager.registerItem(new Hook());
		ItemManager.registerItem(new ItemGrabber());
		ItemManager.registerItem(new Snowball());
		
		instance = this;
		
		Map.deleteAllWorlds();
		this.getCommand("smash").setExecutor(new SmashCommands());
		this.getCommand("sound").setExecutor(new SoundCommands());
		//this.getCommand("particle").setTabCompleter(new ParticleEffectCommands());
		this.getCommand("particle").setExecutor(new ParticleEffectCommands());
		//this.getCommand("particle").setTabCompleter(new ParticleEffectCommands());
		
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
