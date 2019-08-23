package todfresser.smash.mobs.pathfinder;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.EntityVex;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

import java.util.EnumSet;

public class d extends PathfinderGoal {
	private EntityVex vex;
	
	public d(EntityVex vex) {
		this.vex = vex;
		a(EnumSet.of(Type.MOVE));
	}

	public boolean a() {
		return ((!(vex.getControllerMove().b())) && (vex.getRandom().nextInt(7) == 0));
	}

	public boolean b() {
		return false;
	}

	public void e() {
		BlockPosition blockposition = vex.dV();

		if (blockposition == null) {
			blockposition = new BlockPosition(vex);
		}

		for (int i = 0; i < 3; ++i) {
			BlockPosition blockposition1 = blockposition.b(vex.getRandom().nextInt(15) - 7,
					vex.getRandom().nextInt(11) - 5, vex.getRandom().nextInt(15) - 7);

			if (vex.world.isEmpty(blockposition1)) {
				vex.getControllerMove().a(blockposition1.getX() + 0.5D, blockposition1.getY() + 0.5D,
						blockposition1.getZ() + 0.5D, 0.25D);
				if (vex.getGoalTarget() != null)
					return;
				vex.getControllerLook().a(blockposition1.getX() + 0.5D, blockposition1.getY() + 0.5D,
						blockposition1.getZ() + 0.5D, 180.0F, 20.0F);

				return;
			}
		}
	}
}
