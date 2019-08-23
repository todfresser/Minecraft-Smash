package todfresser.smash.mobs;

import net.minecraft.server.v1_14_R1.EntityCreature;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntityVex;
import net.minecraft.server.v1_14_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_14_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_14_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_14_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import todfresser.smash.mobs.main.SmashAI;
import todfresser.smash.mobs.pathfinder.aa;
import todfresser.smash.mobs.pathfinder.b;
import todfresser.smash.mobs.pathfinder.d;

public class SmashVexAI implements SmashAI{

	@Override
	public void setupPathfinder(EntityCreature e, PathfinderGoalSelector goal, PathfinderGoalSelector targets) {
		if (!(e instanceof EntityVex)) return;
		goal.a(0, new PathfinderGoalFloat(e));
		goal.a(4, new aa((EntityVex) e));
		goal.a(8, new d((EntityVex) e));
		goal.a(9, new PathfinderGoalLookAtPlayer(e, EntityPlayer.class, 3.0F, 1.0F));
		goal.a(10, new PathfinderGoalLookAtPlayer(e, EntityPlayer.class, 8.0F));
		targets.a(1, new PathfinderGoalHurtByTarget(e, EntityVex.class));
		targets.a(2, new b((EntityVex) e));
		targets.a(3, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(e, EntityPlayer.class, true));
		
	}

}
