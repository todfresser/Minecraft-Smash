package todfresser.smash.mobs.main;

import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;

public interface SmashAI {
	
	public void setupPathfinder(EntityCreature e, PathfinderGoalSelector goal, PathfinderGoalSelector targets);
}
