package todfresser.smash.items;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class InstantTeleporter implements SmashItemData{

	@Override
	public String getDisplayName() {
		return "§2Instant Teleporter";
	}

	@Override
	public Material getType() {
		return Material.EYE_OF_ENDER;
	}

	@Override
	public byte getSubID() {
		return 0;
	}

	@Override
	public List<String> getLore() {
		return null;
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
	public boolean hasOnPlayerHitPlayerEvent() {
		return false;
	}

	@Override
	public boolean hasOnPlayerShootBowEvent() {
		return false;
	}

	@Override
	public boolean hasOnHookEvent() {
		return false;
	}

	@Override
	public void onRightClickEvent(SmashPlayerData playerdata, Action action, Player whoclicked, Game game) {
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
				return;
			}
			loc.add(x, y, z);
			//loc.getWorld().spigot().playEffect(loc, Effect.WITCH_MAGIC, 0, 0, 0, 0, 0, 0, 1, 40);
			ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0.1f, 1, loc, 40);
		}
		whoclicked.teleport(loc);
		playerdata.canUseItem = true;
		return;
	}

	@Override
	public void onPlayerHitPlayerEvent(SmashPlayerData playerdata, Player player, Player target, Game game) {
	}

	@Override
	public void onPlayerShootBowEvent(SmashPlayerData playerdata, Player player, float force, Game game) {
	}

	@Override
	public void onHookEvent(SmashPlayerData playerdata, Player player, Location target, Game game) {
	}
	
}
