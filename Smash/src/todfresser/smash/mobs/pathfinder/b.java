package todfresser.smash.mobs.pathfinder;

import org.bukkit.event.entity.EntityTargetEvent;

import net.minecraft.server.v1_11_R1.EntityVex;
import net.minecraft.server.v1_11_R1.PathfinderGoalTarget;

public class b extends PathfinderGoalTarget {
	private EntityVex vex;

	public b(EntityVex vex) {
		super(vex, false);
		this.vex = vex;
	}

	public boolean a() {
		return ((vex != null) && (vex.getGoalTarget() != null)
				&& (a(vex.getGoalTarget(), false)));
	}

	public void c() {
		vex.setGoalTarget(vex.getGoalTarget(), EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET, true);
		super.c();
	}

}
