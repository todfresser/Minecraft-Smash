package todfresser.smash.mobs;

import net.minecraft.server.v1_11_R1.EntityCaveSpider;
import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EntitySpider;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStrollLand;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import todfresser.smash.mobs.main.SmashAI;
import todfresser.smash.mobs.pathfinder.PathfinderGoalSpiderMeleeAttack;
import todfresser.smash.mobs.pathfinder.PathfinderGoalSpiderNearestAttackableTarget;

public class SmashSpiderAI implements SmashAI{

	@Override
	public void setupPathfinder(EntityCreature e, PathfinderGoalSelector goal, PathfinderGoalSelector targets) {
		if (!(e instanceof EntityCaveSpider)) return;
		goal.a(1, new PathfinderGoalFloat(e));
		goal.a(3, new PathfinderGoalLeapAtTarget(e, 0.4F));
		goal.a(4, new PathfinderGoalSpiderMeleeAttack((EntitySpider) e));
		goal.a(5, new PathfinderGoalRandomStrollLand(e, 0.8D));
		goal.a(6, new PathfinderGoalLookAtPlayer(e, EntityPlayer.class, 8.0F));
		goal.a(6, new PathfinderGoalRandomLookaround(e));
		targets.a(1, new PathfinderGoalHurtByTarget(e, false, new Class[0]));
		targets.a(2, new PathfinderGoalSpiderNearestAttackableTarget<EntityPlayer>((EntitySpider) e, EntityPlayer.class));
	}

}