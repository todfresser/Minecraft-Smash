package todfresser.smash.basic.mobs.pathfinder;

import net.minecraft.server.v1_14_R1.*;

public class PathfinderGoalSpiderNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
	
	public PathfinderGoalSpiderNearestAttackableTarget(EntitySpider entityspider, Class<T> oclass) {
		super(entityspider, oclass, true);
	}
	
	/*public boolean a() {

		//float f = this.e.e(1.0F);

		return ((!(f >= 0.5F)) && super.a());
	}*/
}
