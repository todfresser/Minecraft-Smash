package todfresser.smash.mobs.pathfinder;

import net.minecraft.server.v1_16_R1.EntityLiving;
import net.minecraft.server.v1_16_R1.EntitySpider;
import net.minecraft.server.v1_16_R1.PathfinderGoalMeleeAttack;

public class PathfinderGoalSpiderMeleeAttack extends PathfinderGoalMeleeAttack {

	public PathfinderGoalSpiderMeleeAttack(EntitySpider entityspider) {
		super(entityspider, 1.0D, true);
	}

	/*public boolean b() {
		float f = this.a.e(1.0F);
		if ((f >= 0.5F) && (this.a.getRandom().nextInt(100) == 0)) {
			this.a.setGoalTarget(null);
			return false;
		}
		return super.b();
	}*/

	protected double a(EntityLiving entityliving) {
		return (4.0F + entityliving.getWidth());
	}
}
