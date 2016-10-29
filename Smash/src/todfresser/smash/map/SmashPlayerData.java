package todfresser.smash.map;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.items.ItemManager;
import todfresser.smash.items.SmashItemData;
import todfresser.smash.main.Smash;

public class SmashPlayerData {
	
	//
	
	//previusData:
	private final PlayerInventory previousInv;
	private final GameMode previousGameMode;
	
	//ItemTasks
	List<BukkitRunnable> tasks = new ArrayList<>();
	
    public void registerItemRunnable(BukkitRunnable itemrunnable, long delay, long period){
    	itemrunnable.runTaskTimer(Smash.getInstance(), delay, period);
    	//itemrunnable.runTaskLater(Smash.getInstance(), delay);
    	tasks.add(itemrunnable);
    }
    public void cancelItemRunnable(BukkitRunnable itemrunnable){
    	if (tasks.contains(itemrunnable)){
    		tasks.remove(itemrunnable);
    		itemrunnable.cancel();
    	}
    }
    public void cancelAllItemRunnables(){
    	for (BukkitRunnable run : tasks){
    		run.cancel();
    	}
    	tasks.clear();
    }
	
	//ItemInHand
    private int oldItemID;
	private int itemID;
	private int ItemUsesLeft;
	public boolean canUseItem;
	
	public int getItemData(){
		return itemID;
	}
	
	public boolean hasItem(){
		return itemID != 0;
	}
	public int getItemUsesLeft(){
		return ItemUsesLeft;
	}
	public void changeItem(int itemID){
		SmashItemData data = ItemManager.getItemData(itemID);
		if (itemID != this.oldItemID) this.canUseItem = true;
		this.ItemUsesLeft = data.getmaxItemUses();
		this.itemID = itemID;
	}
	public void removeItems(Player p){
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		/*for (int slot = 0; slot < p.getInventory().getSize(); slot++){
			ItemStack item = p.getInventory().getItem(slot);
			if (item != null && item.getType() != Material.AIR){
				p.getInventory().setItem(slot, new ItemStack(Material.AIR));
			}
		}*/
		this.oldItemID = itemID;
		this.itemID = 0;
		p.updateInventory();
	}
	public boolean OnInteract(Action a, Player whoclicked, Game game){
		if (hasItem()){
			SmashItemData data = ItemManager.getItemData(itemID);
			if (data.hasOnRightClickEvent()){
				if (canUseItem == true){
					canUseItem = false;
					data.onRightClickEvent(this, a, whoclicked, game);
					ItemUsesLeft--;
					if (ItemUsesLeft <= 0) removeItems(whoclicked);
				}
				return true;
			}
		}
		return false;
	}
	public boolean OnPlayerHitPlayer(Player p, Player target, Game game){
		if (hasItem()){
			SmashItemData data = ItemManager.getItemData(itemID);
			if (data.hasOnPlayerHitPlayerEvent()){
				if (canUseItem == true){
					canUseItem = false;
					data.onPlayerHitPlayerEvent(this, p, target, game);
					ItemUsesLeft--;
					if (ItemUsesLeft <= 0) removeItems(p);
				}
				return true;
			}
		}
		return false;
	}
	public void OnBowShootEvent(Player p, float force, Game game){
		if (hasItem()){
			SmashItemData data = ItemManager.getItemData(itemID);
			if (data.hasOnPlayerShootBowEvent()){
				if (canUseItem == true){
					canUseItem = false;
					data.onPlayerShootBowEvent(this, p, force, game);
					ItemUsesLeft--;
					if (ItemUsesLeft <= 0) removeItems(p);
				}
				return;
			}
		}
		return;
	}
	//Ende von ItemInHand
	
	
	//IngameStats:
	private PlayerType type;
	private int lives;
	private int damage;
	private int damagedone;
	private int totaldamage;
	
	public SmashPlayerData(PlayerInventory PreviousInv, GameMode PreviusGameMode, int startlives){
		type = PlayerType.Ingame;
		previousInv = PreviousInv;
		previousGameMode = PreviusGameMode;
		lives = startlives;
		damage = 0;
		damagedone = 0;
		totaldamage = 0;
		itemID = 0;
	}
	
	public PlayerType getType() {
		return type;
	}
	
	public PlayerInventory getpreviousInventory(){
		return previousInv;
	}
	
	public GameMode getpreviousGameMode(){
		return previousGameMode;
	}

	public void setSpectator() {
		this.type = PlayerType.Spectator;
	}

	public int getHealth() {
		return lives*2;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getDamageDone() {
		return damagedone;
	}
	
	public int getTotalDamge(){
		return totaldamage;
	}
	
	public int getDamage() {
		return damage;
	}

	public void addDamage(int damage) {
		this.damage = this.damage + damage;
		this.totaldamage = this.totaldamage + damage;
	}
	
	public void removeDamage(int damage){
		int i = damage;
		while (this.damage - i < 0){
			i--;
		}
		this.damage = this.damage - i;
	}
	
	public void addDamageDone(int damage){
		this.damagedone = this.damagedone + damage;
	}
	
	public void resetDamage(){
		this.damage = 0;
		this.lives--;
	}
}
