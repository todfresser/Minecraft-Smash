package todfresser.smash.mobs;

import net.minecraft.server.v1_11_R1.EntityBlaze;
import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import todfresser.smash.mobs.main.SmashAI;
import todfresser.smash.mobs.pathfinder.PathfinderGoalBlazeFireball;

public class SmashBlazeAI implements SmashAI{
	
	@Override
	public void setupPathfinder(EntityCreature e, PathfinderGoalSelector goal, PathfinderGoalSelector targets) {
		if (!(e instanceof EntityBlaze)) return;
		goal.a(0, new PathfinderGoalFloat(e));
		goal.a(4, new PathfinderGoalBlazeFireball((EntityBlaze) e));
		goal.a(5, new PathfinderGoalMoveTowardsRestriction(e, 1.0D));
		goal.a(7, new PathfinderGoalRandomStrollLand(e, 1.0D, 0.0F));
		goal.a(8, new PathfinderGoalLookAtPlayer(e, EntityPlayer.class, 8.0F));
		goal.a(8, new PathfinderGoalRandomLookaround(e));
		targets.a(1, new PathfinderGoalHurtByTarget(e, true, new Class[0]));
		targets.a(2, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(e, EntityPlayer.class, true));
	}
}
