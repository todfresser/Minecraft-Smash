package todfresser.smash.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class InstantTeleporter extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§2Instant Teleporter";
	}

	@Override
	public Material getType() {
		return Material.EYE_OF_ENDER;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 20;
	}

	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}

	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		Location loc = whoclicked.getLocation().add(0, 1, 0);
		final Vector direction = loc.getDirection().normalize();
		double t = 0.25;
		while (t < 5.0){
			t = t + 0.1;
			double x = direction.getX() * 0.7;
			double y = direction.getY() * 0.7;
			double z = direction.getZ() * 0.7;
			if (loc.getBlock() != null && !loc.getBlock().getType().equals(Material.AIR)){
				whoclicked.teleport(loc);
				playerdata.canUseItem = true;
				return true;
			}
			loc.add(x, y, z);
			ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0.1f, 1, loc, 40);
		}
		whoclicked.teleport(loc);
		playerdata.canUseItem = true;
		return true;
	}
}
