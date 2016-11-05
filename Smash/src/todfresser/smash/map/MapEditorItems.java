package todfresser.smash.map;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapEditorItems {
	
	enum EditorInventoryType{
		GLOBALSPAWNS,
		PLAYERSPAWNS,
		ITEMSPAWNS,
		COMMANDSIGNS,
		SAVEDELETE;
	}
	
	public static void setInventory(MapEditorData d, EditorInventoryType type){
		Inventory i = d.Inv;
		i.setItem(3, EmptySpace_GRAY());
		i.setItem(4, GLOBALSPAWNS(d));
		i.setItem(5, PLAYERSPAWNS(d.playerspawns));
		i.setItem(6, ITEMSPAWNS(d.itemspawns));
		i.setItem(7, SIGNS(d.leavesign, d.livesign, d.itemsign));
		i.setItem(8, EmptySpace_GRAY());
		clear(i);
		updateIcon(i, d.type);
		if (type.equals(EditorInventoryType.SAVEDELETE)){
			i.setItem(0, DELETE(d.exists));
			i.setItem(1, CANCEL());
			i.setItem(2, CREATE(d.exists));
			for (int in = 10; in< 18; in++){
				i.setItem(in, EmptySpace());
			}
			return;
		}
		if (type.equals(EditorInventoryType.GLOBALSPAWNS)){
			i.setItem(21, SPECTATORSPAWN(d.spectatorspawn));
			i.setItem(22, LEAVEPOINT(d.leavepoint));
			i.setItem(23, LOBBY(d.lobbyspawnpoint));
			return;
		}
		if (type.equals(EditorInventoryType.PLAYERSPAWNS)){
			i.setItem(21, DELETEPLAYERSPAWNS(d.playerspawns));
			i.setItem(22, SHOWPLAYERSPAWNS(d.playerspawns));
			i.setItem(23, ADDPLAYERSPAWN(d.playerspawns));
			return;
		}
		if (type.equals(EditorInventoryType.ITEMSPAWNS)){
			i.setItem(21, DELETEITEMSPAWNS(d.itemspawns));
			i.setItem(22, SHOWITEMSPAWNS(d.itemspawns));
			i.setItem(23, ADDITEMSPAWN(d.itemspawns));
			return;
		}
		if (type.equals(EditorInventoryType.COMMANDSIGNS)){
			i.setItem(21, LEAVESIGN(d.leavesign));
			i.setItem(22, ITEMSIGN(d.itemsign));
			i.setItem(23, LIVESIGN(d.livesign));
			return;
		}
	}
	
	private static void clear(Inventory inv){
		for (int i = 18; i< 27; i++){
			inv.setItem(i, EmptySpace_GRAY());
		}
	}
	public static void updateIcon(Inventory inv, int type){
		inv.setItem(9, CHANGEICON(type));
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CHANGEICON(int ItemID){
		ItemStack i = new ItemStack(ItemID, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		meta.setDisplayName(ChatColor.WHITE + "Icon");
		l.add(ChatColor.RED + "Klicke um das Icon gegen das Item");
		l.add(ChatColor.RED + "in deiner Hand auszutauschen.");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack SPECTATORSPAWN(Location loc){
		ItemStack i = new ItemStack(Material.WATCH, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Spectatorspawn");
			meta.setLore(l);
		}else meta.setDisplayName(ChatColor.RED + "Spectatorspawn");
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack LEAVEPOINT(Location loc){
		ItemStack i = new ItemStack(Material.WATCH, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Leavepoint");
			meta.setLore(l);
		}else meta.setDisplayName(ChatColor.RED + "Leavepoint");
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack LOBBY(Location loc){
		ItemStack i = new ItemStack(Material.WATCH, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Lobby");
			meta.setLore(l);
		}else meta.setDisplayName(ChatColor.RED + "Lobby");
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack LEAVESIGN(Location loc){
		ItemStack i = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum Verlassen");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "leave");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum Verlassen");
			meta.setLore(l);
		}
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack LIVESIGN(Location loc){
		ItemStack i = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum Ändern der Leben");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "lives");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum Ändern der Leben");
			meta.setLore(l);
		}
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ITEMSIGN(Location loc){
		ItemStack i = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum Deaktivieren von Items");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "items");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum Deaktivieren von Items");
			meta.setLore(l);
		}
		i.setItemMeta(meta);
		return i;
	}
	
	public static ItemStack SHOWPLAYERSPAWNS(ArrayList<Location> collection){
		ItemStack i = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Zeige alle Spielerspawnpunkte");
		List<String> l = new ArrayList<>();
		for (Location loc : collection){
			l.add(ChatColor.GRAY + ">> " + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		}
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ADDPLAYERSPAWN(ArrayList<Location> collection){
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte)2);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Setze den " + (collection.size() + 1) + " Spielerspawn");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack DELETEPLAYERSPAWNS(ArrayList<Location> collection){
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte)14);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Entferne alle " + collection.size() + " Spielerpawns");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack SHOWITEMSPAWNS(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.MOB_SPAWNER, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Zeige alle Itemspawnpunkte");
		List<String> l = new ArrayList<>();
		for (Location loc : itemspawns){
			l.add(ChatColor.GRAY + ">> " + loc.getX() + "," + loc.getBlockY() + "," + loc.getZ());
		}
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ADDITEMSPAWN(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte)2);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Setze den " + (itemspawns.size() + 1) + " Itemspawn");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack DELETEITEMSPAWNS(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (byte)14);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Entferne alle " + itemspawns.size() + " Itemspawns");
		i.setItemMeta(meta);
		return i;
	}
	
	
	public static ItemStack GLOBALSPAWNS(MapEditorData d){
		ItemStack i = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Globale Spawnpunkte");
		List<String> l = new ArrayList<>();
		if (d.spectatorspawn == null){
			l.add(ChatColor.RED + "Spectatorspawn");
		}else l.add(ChatColor.GREEN + "Spectatorspawn");
		if (d.leavepoint == null){
			l.add(ChatColor.RED + "Leavepoint");
		}else l.add(ChatColor.GREEN + "Leavepoint");
		if (d.lobbyspawnpoint == null){
			l.add(ChatColor.RED + "Lobbyspawnpoint");
		}else l.add(ChatColor.GREEN + "Lobbyspawnpoint");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack PLAYERSPAWNS(ArrayList<Location> collection){
		ItemStack i = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Spielerspawnpunkte");
		List<String> l = new ArrayList<>();
		if (collection.size() < 2) l.add(ChatColor.RED + Integer.toString(collection.size()) + " Spielerspawns");
		if (collection.size() > 1) l.add(ChatColor.GREEN + Integer.toString(collection.size()) + " Spielerspawns");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ITEMSPAWNS(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.MOB_SPAWNER, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Itemspawnpunkte");
		List<String> l = new ArrayList<>();
		if (itemspawns.size() < 1) l.add(ChatColor.RED + Integer.toString(itemspawns.size()) + " Itemspawns");
		if (itemspawns.size() > 0) l.add(ChatColor.GREEN + Integer.toString(itemspawns.size()) + " Itemspawns");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack SIGNS(Location leavesign, Location livesign, Location itemsign){
		ItemStack i = new ItemStack(Material.SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Schilder");
		List<String> l = new ArrayList<>();
		if (leavesign != null){
			l.add(ChatColor.GREEN + "Leavesign");
		}else l.add(ChatColor.RED + "Leavesign");
		if (itemsign != null){
			l.add(ChatColor.GREEN + "Itemsign");
		}else l.add(ChatColor.RED + "Itemsign");
		if (livesign != null){
			l.add(ChatColor.GREEN + "Livesign");
		}else l.add(ChatColor.RED + "Livesign");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack EmptySpace(){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack EmptySpace_GRAY(){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CREATE(boolean exists){
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData());
		ItemMeta meta = i.getItemMeta();
		if (exists == false) meta.setDisplayName(ChatColor.WHITE + "Erstellen");
		if (exists == true) meta.setDisplayName(ChatColor.WHITE + "Überschreiben");
		i.setItemMeta(meta);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CANCEL(){
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.ORANGE.getData());
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Abbrechen");
		i.setItemMeta(meta);
		return i;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack DELETE(boolean exists){
		if (exists){
			ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "Löschen");
			i.setItemMeta(meta);
			return i;
		}
		return EmptySpace_GRAY();
	}
	
}
