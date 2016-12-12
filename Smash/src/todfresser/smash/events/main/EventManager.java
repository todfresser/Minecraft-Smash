package todfresser.smash.events.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import todfresser.smash.map.Game;

public class EventManager {
	private static HashMap<Integer, SmashEventData> eventdata = new HashMap<>();
	
	public static void registerEvent(SmashEventData item){
		for (SmashEventData data : eventdata.values()){
			if (data.getDisplayName().equals(item.getDisplayName())) return;
		}
		eventdata.put(eventdata.size()+1, item);
	}
	
	public static Collection<Integer> getAllEventDataIDs(){
		return eventdata.keySet();
	}
	
	public static List<ItemStack> getStandardDeactivationItems(Collection<Integer> allowedEvents){
		List<ItemStack> items = new ArrayList<>();
		for (int id : getAllEventDataIDs()){
			ItemStack i = new ItemStack(eventdata.get(id).getType(), 1, eventdata.get(id).getSubID());
			ItemMeta m = i.getItemMeta();
			m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
			m.setDisplayName(eventdata.get(id).getDisplayName());
			List<String> lore = new ArrayList<>();
			if (allowedEvents.contains(id)){
				lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.GREEN + "true");
			}else lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.RED + "false");
			m.setLore(lore);
			if (eventdata.get(id).isEnchanted()) m.addEnchant(Enchantment.DURABILITY, 1, false);
			i.setItemMeta(m);
			items.add(i);
		}
		return items;
	}
	public static ItemStack getStandardDeactivationItem(SmashEventData d, boolean allowed){
		ItemStack i = new ItemStack(d.getType(), 1, d.getSubID());
		ItemMeta m = i.getItemMeta();
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
		m.setDisplayName(d.getDisplayName());
		List<String> lore = new ArrayList<>();
		if (allowed){
			lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.GREEN + "true");
		}else lore.add(ChatColor.GOLD + "Aktiv: " + ChatColor.RED + "false");
		//lore.add(ChatColor.GOLD + "Seltenheit: " + ChatColor.GRAY +  d.getSpawnChance());
		m.setLore(lore);
		if (d.isEnchanted()) m.addEnchant(Enchantment.DURABILITY, 1, false);
		i.setItemMeta(m);
		return i;
	}
	
	public static SmashEventData activateRandomEvent(Game g){
		Random r = new Random();
		if (eventdata.size() < 1) return null;
		HashMap<Integer, Integer> eventIDs = new HashMap<>();
		for (int id : g.getAllowedEventIDs()){
			for (int quantity = 0; quantity <= getEventData(id).getChance() && quantity <= 50; quantity++){
				eventIDs.put(eventIDs.size(), id);
			}
		}
		SmashEventData d = null;
		int maxtime = 30;
		do{
			d = getEventData(eventIDs.get(r.nextInt(eventIDs.size())));
			maxtime--;
		}while(!d.perform(g) && maxtime > 0);
		return d;
	}
	
	public static SmashEventData getEventData(int eventdataID){
		return eventdata.get(eventdataID);
	}
}
