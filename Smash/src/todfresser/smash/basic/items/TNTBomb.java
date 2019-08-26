package todfresser.smash.basic.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.basic.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;

public class TNTBomb extends SmashItem{
	
	private String displayName = ChatColor.DARK_RED + "TNTBomb";

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Material getType() {
		return Material.TNT;
	}

	@Override
	public int getmaxItemUses() {
		return 1;
	}

	@Override
	public int getSpawnChance() {
		return 3;
	}
	
	@Override
	public boolean hasOnRightClickEvent() {
		return true;
	}
	
	@Override
	public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
		Item tntBomb = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.TNT));
		tntBomb.setVelocity(player.getLocation().getDirection().multiply(1.2D));
	    playerdata.registerItemRunnable(new BukkitRunnable() {

			@Override
			public void run() {
				tntBomb.remove();
				Entity tnt = game.getWorld().spawn(tntBomb.getLocation().add(0, 1, 0), TNTPrimed.class);
		        ((TNTPrimed)tnt).setFuseTicks((int) (30));
		        tnt.setVelocity(VectorFunctions.getStandardVector(-90, 2).multiply(0.2f));
		        
				Entity tnt2 = game.getWorld().spawn(tntBomb.getLocation().add(0, 1, 0), TNTPrimed.class);
		        ((TNTPrimed)tnt2).setFuseTicks((int) (30));
		        tnt2.setVelocity(VectorFunctions.getStandardVector(0, 2).multiply(0.2f));
		        
				Entity tnt3 = game.getWorld().spawn(tntBomb.getLocation().add(0, 1, 0), TNTPrimed.class);
		        ((TNTPrimed)tnt3).setFuseTicks((int) (30));
		        tnt3.setVelocity(VectorFunctions.getStandardVector(90, 2).multiply(0.2f));
		        
				Entity tnt4 = game.getWorld().spawn(tntBomb.getLocation().add(0, 1, 0), TNTPrimed.class);
		        ((TNTPrimed)tnt4).setFuseTicks((int) (30));
		        tnt4.setVelocity(VectorFunctions.getStandardVector(180, 2).multiply(0.2f));
		        
		        playerdata.cancelItemRunnable(this);
		        
			}
	    	
	    },35,0);
	    playerdata.canUseItem = true;
		return true;
	}

}
