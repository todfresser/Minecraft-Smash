package todfresser.smash.extrafunctions;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import todfresser.smash.map.SmashPlayerData;

public class VectorFunctions {
	
	public static Vector getStandardVector(double EntityYaw, SmashPlayerData data, double extraY){
		double pitch = (90 * Math.PI) / 180;
		double yaw = ((EntityYaw + 90) * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.cos(pitch);
		double z = Math.sin(pitch) * Math.sin(yaw);
		System.out.println("VectorGiven: " + x + ", " + y + "," + z);
		return new Vector(x, y, z).setY(extraY).multiply(data.getDamage()/200 + 0.9);
	}
	
	public static Vector getStandardJumpVector(Location PlayerLocation){
		double pitch = ((0.4 * PlayerLocation.getPitch() + 90) * Math.PI) / 180;
		double yaw = ((PlayerLocation.getYaw() + 90) * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = 0.3 * Math.cos(pitch) + 0.5;
		double z = Math.sin(pitch) * Math.sin(yaw);
		return new Vector(x, y, z);
	}
	
	public static Vector getVectorbetweenLocations(Location from, Location to){
		return to.toVector().subtract(from.toVector());
	}
}
