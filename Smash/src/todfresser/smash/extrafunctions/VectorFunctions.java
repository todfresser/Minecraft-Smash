package todfresser.smash.extrafunctions;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorFunctions {
	
	public static Vector getStandardVector(double EntityYaw, double extraY){
		double pitch = (90 * Math.PI) / 180;
		double yaw = ((EntityYaw + 90) * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.cos(pitch);
		double z = Math.sin(pitch) * Math.sin(yaw);
		return new Vector(x, y, z).setY(extraY);
	}
	
	public static Vector getStandardJumpVector(Location PlayerLocation){
		double pitch = ((0.4 * PlayerLocation.getPitch() + 90) * Math.PI) / 180;
		double yaw = ((PlayerLocation.getYaw() + 90) * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = 0.4 * Math.cos(pitch) + 0.5;
		double z = Math.sin(pitch) * Math.sin(yaw);
		return new Vector(x, y, z);
	}
	
	public static Vector getVectorbetweenLocations(Location from, Location to){
		return to.toVector().subtract(from.toVector()).normalize();
	}
}
