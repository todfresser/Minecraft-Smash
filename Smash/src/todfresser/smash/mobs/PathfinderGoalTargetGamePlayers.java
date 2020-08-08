package todfresser.smash.mobs;

import net.minecraft.server.v1_16_R1.EntityLiving;
import net.minecraft.server.v1_16_R1.PathfinderGoal;
import net.minecraft.server.v1_16_R1.Vec3D;

public class PathfinderGoalTargetGamePlayers extends PathfinderGoal {

    private SmashEntity entity;

    public PathfinderGoalTargetGamePlayers(SmashEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean a() {
        EntityLiving target = entity.getEntityHandle().getGoalTarget();
        double followDistance = entity.getAttributes().getFollowRange();
        if (target == null || calculateDistance(target) > followDistance) {
            return entity.updateTarget();
        }
        return false;
    }

    private double calculateDistance(EntityLiving other) {
        Vec3D pos1 = other.getPositionVector();
        Vec3D pos2 = entity.getEntityHandle().getPositionVector();
        return pos1.f(pos2);
    }
}
