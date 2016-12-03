package todfresser.smash.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class JetPack extends SmashItem{

	@Override
	public String getDisplayName() {
		return "§fJ§7e§ft§7P§fa§7c§fk";
	}

	@Override
	public Material getType() {
		return Material.FLINT_AND_STEEL;
	}
	
	@Override
	public int getmaxItemUses() {
		return 4;
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
	public void onRightClickEvent(SmashPlayerData playerdata, Player whoclicked, Game game) {
		//whoclicked.setVelocity(VectorFunctions.getStandardVector(whoclicked.getLocation().getYaw(), 5).multiply(0.2f));
		whoclicked.setVelocity(VectorFunctions.getStandardJumpVector(whoclicked.getLocation()).setY(1.5).multiply(0.6));
		//whoclicked.getLocation().getWorld().spigot().playEffect(whoclicked.getLocation().subtract(0, 0.5, 0), Effect.MOBSPAWNER_FLAMES, 1, 1, 0.4f, 0.0f, 0.4f, 0.1f, 4, 20);
		ParticleEffect.FLAME.display(0.4f, 0.0f, 0.4f, 0.1f, 4, whoclicked.getLocation(), 20);
		playerdata.canUseItem = true;
	}
}
