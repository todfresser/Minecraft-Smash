package todfresser.smash.mobs.pathfinder;

import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityVex;
import net.minecraft.server.v1_11_R1.PathfinderGoal;
import net.minecraft.server.v1_11_R1.SoundEffects;
import net.minecraft.server.v1_11_R1.Vec3D;

public class aa extends PathfinderGoal {
	private EntityVex vex;
	public aa(EntityVex vex) {
		this.vex = vex;
		a(1);
	}
	public boolean a() {
		if (vex.getGoalTarget() == null) return false;
		return (vex.h(vex.getGoalTarget()) > 4.0D);
	}
	public boolean b() {
		return ((vex.getControllerMove().a()) && (vex.dj()) && (vex.getGoalTarget() != null) && (vex.getGoalTarget().isAlive()));
	}
	public void c() {
		EntityLiving entityliving = vex.getGoalTarget();
		Vec3D vec3d = entityliving.g(1.0F);
		vex.getControllerMove().a(vec3d.x, vec3d.y, vec3d.z, 1.0D);
		vex.a(true);
		vex.a(SoundEffects.hd, 1.0F, 1.0F);
	}
	public void d() {
		vex.a(false);
	}
	public void e() {
		EntityLiving entityliving = vex.getGoalTarget();
		if (vex.getBoundingBox().c(entityliving.getBoundingBox())) {
			vex.B(entityliving);
			vex.a(false);
		} else {
				double d0 = vex.h(entityliving);
			if (d0 < 9.0D) {
				Vec3D vec3d = entityliving.g(1.0F);

				vex.getControllerMove().a(vec3d.x, vec3d.y, vec3d.z, 1.0D);
			}
		}
	}

}
