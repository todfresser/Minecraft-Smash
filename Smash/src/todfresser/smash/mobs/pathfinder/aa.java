package todfresser.smash.mobs.pathfinder;

import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.EntityVex;
import net.minecraft.server.v1_14_R1.PathfinderGoal;
import net.minecraft.server.v1_14_R1.SoundEffects;
import net.minecraft.server.v1_14_R1.Vec3D;

import java.util.EnumSet;

public class aa extends PathfinderGoal {
	private EntityVex vex;
	public aa(EntityVex vex) {
		this.vex = vex;
		a(EnumSet.of(Type.MOVE));
	}
	public boolean a() {
		if (vex.getGoalTarget() == null) return false;
		return (vex.h(vex.getGoalTarget()) > 4.0D);
	}
	public boolean b() {
		return ((vex.getControllerMove().b()) && (vex.isCharging()) && (vex.getGoalTarget() != null) && (vex.getGoalTarget().isAlive()));
	}
	public void c() {
		EntityLiving entityliving = vex.getGoalTarget();
		Vec3D vec3d = entityliving.f(1.0F);
		vex.getControllerMove().a(vec3d.x, vec3d.y, vec3d.z, 1.0D);
		vex.setCharging(true);
		vex.a(SoundEffects.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
	}
	public void d() {
		vex.setCharging(false);
	}
	public void e() {
		EntityLiving entityliving = vex.getGoalTarget();
		if (vex.getBoundingBox().c(entityliving.getBoundingBox())) {
			vex.C(entityliving);
			vex.setCharging(false);
		} else {
				double d0 = vex.h(entityliving);
			if (d0 < 9.0D) {
				Vec3D vec3d = entityliving.j(1.0F);

				vex.getControllerMove().a(vec3d.x, vec3d.y, vec3d.z, 1.0D);
			}
		}
	}

}
