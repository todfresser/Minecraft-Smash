package todfresser.smash.main;

import org.bukkit.plugin.java.JavaPlugin;
import todfresser.smash.extrafunctions.DynamicClassFunctions;
import todfresser.smash.game.GameManager;

public class SmashNew  extends JavaPlugin {

    private static SmashNew instance;
    public static SmashNew getInstance(){
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
        //EventManager.registerEvent(new Tornado());

        instance = this;

        getServer().getScheduler().scheduleSyncDelayedTask(this, GameManager::loadAllMaps);
    }

    @Override
    public void onDisable(){

    }
}