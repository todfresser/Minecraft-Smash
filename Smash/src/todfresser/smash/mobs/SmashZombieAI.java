package todfresser.smash.mobs;

import net.minecraft.server.v1_16_R1.EntityCreature;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.EntityZombie;
import net.minecraft.server.v1_16_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R1.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_16_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_16_R1.PathfinderGoalZombieAttack;
import todfresser.smash.mobs.main.SmashAI;

public class SmashZombieAI implements SmashAI{

	@Override
	public void setupPathfinder(EntityCreature e, PathfinderGoalSelector goal, PathfinderGoalSelector targets) {
		if (!(e instanceof EntityZombie)) return;
		goal.a(0, new PathfinderGoalFloat(e));
		goal.a(2, new PathfinderGoalZombieAttack((EntityZombie) e, 1.0D, false));
		goal.a(5, new PathfinderGoalMoveTowardsRestriction(e, 1.0D));
		goal.a(7, new PathfinderGoalRandomStrollLand(e, 1.0D));
		goal.a(8, new PathfinderGoalLookAtPlayer(e, EntityPlayer.class, 8.0F));
		goal.a(8, new PathfinderGoalRandomLookaround(e));
		targets.a(1, new PathfinderGoalHurtByTarget(e));
		targets.a(2, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(e, EntityPlayer.class, true));
	}

}
