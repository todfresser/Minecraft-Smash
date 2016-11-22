package todfresser.smash.items.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
	
	private static HashMap<Integer, SmashItemData> itemdata = new HashMap<>();
	
	public static void registerItem(SmashItemData item){
		for (SmashItemData data : itemdata.values()){
			if (data.getDisplayName().equals(item.getDisplayName())) return;
		}
		itemdata.put(itemdata.size()+1, item);
	}
	public static Collection<Integer> getAllItemDataIDs(){
		return itemdata.keySet();
	}
	public static ItemStack getStandardItem(int itemdataID){
		ItemStack i = new ItemStack(itemdata.get(itemdataID).getType(), 1, itemdata.get(itemdataID).getSubID());
		ItemMeta m = i.getItemMeta();
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		m.setDisplayName(itemdata.get(itemdataID).getDisplayName());
		m.setLore(itemdata.get(itemdataID).getLore());
		if (itemdata.get(itemdataID).isEnchanted()) m.addEnchant(Enchantment.DURABILITY, 1, false);
		i.setItemMeta(m);
		return i;
	}
	public static List<ItemStack> getStandardDeactivationItems(Collection<Integer> allowedItems){
		List<ItemStack> items = new ArrayList<>();
		for (int id : itemdata.keySet()){
			ItemStack i = new ItemStack(itemdata.get(id).getType(), 1, itemdata.get(id).getSubID());
			ItemMeta m = i.getItemMeta();
			m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
			m.setDisplayName(itemdata.get(id).getDisplayName());
			List<String> lore = new ArrayList<>();
			if (allowedItems.contains(id)){
				lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.GREEN + "true");
			}else lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.RED + "false");
			lore.add(ChatColor.GOLD + "Seltenheit: " + ChatColor.GRAY +  itemdata.get(id).getSpawnChance());
			m.setLore(lore);
			if (itemdata.get(id).isEnchanted()) m.addEnchant(Enchantment.DURABILITY, 1, false);
			i.setItemMeta(m);
			items.add(i);
		}
		return items;
	}
	public static ItemStack getStandardDeactivationItem(SmashItemData d, boolean allowed){
		ItemStack i = new ItemStack(d.getType(), 1, d.getSubID());
		ItemMeta m = i.getItemMeta();
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		m.setDisplayName(d.getDisplayName());
		List<String> lore = new ArrayList<>();
		if (allowed){
			lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.GREEN + "true");
		}else lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.RED + "false");
		lore.add(ChatColor.GOLD + "Seltenheit: " + ChatColor.GRAY +  d.getSpawnChance());
		m.setLore(lore);
		if (d.isEnchanted()) m.addEnchant(Enchantment.DURABILITY, 1, false);
		i.setItemMeta(m);
		return i;
	}
	
	public static void spawnRandomItem(List<Location> locations, List<Integer> allowedItems){
		Random r = new Random();
		Location l = locations.get(r.nextInt(locations.size()));
		for (Entity e : l.getWorld().getNearbyEntities(l, 1.5, 5, 1.5)){
			if (e.getType().equals(EntityType.DROPPED_ITEM) && e.getLocation().getY() <= l.getY() + 1.5) return;
		}
		if (itemdata.size() < 1) return;
		HashMap<Integer, Integer> itemIDs = new HashMap<>();
		for (int id : allowedItems){
			for (int quantity = 0; quantity <= getItemData(id).getSpawnChance() && quantity <= 50; quantity++){
				itemIDs.put(itemIDs.size(), id);
			}
		}
		int id = itemIDs.get(r.nextInt(itemIDs.size()));
		l.getWorld().dropItem(l, getStandardItem(id));
	}
	
	public static SmashItemData getItemData(int itemdataID){
		return itemdata.get(itemdataID);
	}
	
	public static int getItemDataID(Material material, String displayname){
		for (int id : itemdata.keySet()){
			if (itemdata.get(id).getDisplayName().equals(displayname) && itemdata.get(id).getType().equals(material)){
				return id;
			}
		}
		return 0;
	}
}