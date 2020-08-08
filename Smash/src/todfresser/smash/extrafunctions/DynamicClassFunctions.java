package todfresser.smash.extrafunctions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import net.minecraft.server.v1_16_R1.ResourceKey;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import todfresser.smash.main.Smash;

public class DynamicClassFunctions {
	
	public static String nmsPackage = "net.minecraft.server.v1_16_R1";
	public static String obcPackage = "org.bukkit.craftbukkit.v1_16_R1";
	
	public static boolean setPackages() {
		Server craftServer = Bukkit.getServer();
		if (craftServer != null) {
			try {
				Class<?> craftClass = craftServer.getClass();
				Method getHandle = craftClass.getMethod("getHandle");
				Class<?> returnType = getHandle.getReturnType();

				obcPackage = craftClass.getPackage().getName();
				nmsPackage = returnType.getPackage().getName();
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}
	
	public static final HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
	public static boolean setClasses() {
		try {
			// org.bukkit.craftbukkit
			classes.put("CraftServer", Class.forName(obcPackage + ".CraftServer"));
			classes.put("CraftWorld", Class.forName(obcPackage + ".CraftWorld"));
			
			// net.minecraft.server
			classes.put("MinecraftServer", Class.forName(nmsPackage + ".MinecraftServer"));
			classes.put("WorldData", Class.forName(nmsPackage + ".WorldData"));
			classes.put("WorldServer", Class.forName(nmsPackage + ".WorldServer"));
			
			return true;
		} catch (Exception e) {
			System.out.println("Could not aquire a required class");
			return false;
		}
	}
	
	public static final HashMap<String, Method> methods = new HashMap<String, Method>();
	public static boolean setMethods() {
		try {
			// org.bukkit.craftbukkit
			methods.put("CraftWorld.getHandle()", classes.get("CraftWorld").getDeclaredMethod("getHandle"));
			methods.put("CraftServer.getServer()", classes.get("CraftServer").getDeclaredMethod("getServer"));
			
			// net.minecraft.server
			
			return true;
		} catch (Exception e) {
			System.out.println("Could not find a required method");
			return false;
		}
	}
	
	public static final HashMap<String, Field> fields = new HashMap<String, Field>();
	public static boolean setFields() {
		try {
			
			fields.put("MinecraftServer.worlds", classes.get("MinecraftServer").getDeclaredField("worldServer"));
			
			fields.put("CraftServer.worlds", classes.get("CraftServer").getDeclaredField("worlds"));
			return true;
		} catch (Exception e) {
			System.out.println("Could not find a field class");
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void forceUnloadWorld(World world)
	{
		world.setAutoSave(false);
		for ( Player player : world.getPlayers()) player.kickPlayer("Die Welt wird resettet");
		Smash.unloadMultiWorldWorld(world);

		// formerly used server.unloadWorld at this point. But it was sometimes failing, even when I force-cleared the player list
		
		try
		{
			Field f = fields.get("CraftServer.worlds");
			f.setAccessible(true);
			Map<String, World> worlds = (Map<String, World>)f.get(Bukkit.getServer());
			worlds.remove(world.getName().toLowerCase());
			f.setAccessible(false);
		} catch ( IllegalAccessException ex ) {
		}

		Object ms = getMinecraftServer();

		Collection<Object> worldList;
		try {
			worldList = ((Map<ResourceKey<World>, Object>) fields.get("MinecraftServer.worlds").get(ms)).values();

			worldList.remove(methods.get("CraftWorld.getHandle()").invoke(world));
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	
	protected static Object getMinecraftServer()
	{
		try {
			return methods.get("CraftServer.getServer()").invoke(Bukkit.getServer());
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return null;
	}

}
