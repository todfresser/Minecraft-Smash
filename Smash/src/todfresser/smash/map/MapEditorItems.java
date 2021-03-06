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
        TELEPORTERS,
		COMMANDSIGNS,
		SAVEDELETE;
	}
	
	public static void setInventory(MapEditorData d, EditorInventoryType type){
		Inventory i = d.Inv;
		i.setItem(3, EmptySpace_GRAY());
		i.setItem(4, GLOBALSPAWNS(d));
		i.setItem(5, PLAYERSPAWNS(d.playerspawns));
		i.setItem(6, ITEMSPAWNS(d.itemspawns));
        i.setItem(7, TELEPORTER(d.teleporters));
		i.setItem(8, SIGNS(d.leavesign, d.livesign, d.itemsign, d.itemchancesign, d.eventsign));
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
		if (type.equals(EditorInventoryType.TELEPORTERS)){
            i.setItem(21, DELETETELEPORTERS(d.teleporters));
            i.setItem(22, SHOWTELEPORTERS(d.teleporters));
            i.setItem(23, ADDTELEPORTER(d.teleporters));
        }
		if (type.equals(EditorInventoryType.COMMANDSIGNS)){
			i.setItem(21, LEAVESIGN(d.leavesign));
			i.setItem(22, ITEMSIGN(d.itemsign));
			i.setItem(23, ITEMCHANCESIGN(d.itemchancesign));
			i.setItem(24, LIVESIGN(d.livesign));
			i.setItem(25, EVENTSIGN(d.eventsign));
			return;
		}
	}
	
	private static void clear(Inventory inv){
		for (int i = 18; i< 27; i++){
			inv.setItem(i, EmptySpace_GRAY());
		}
	}
	public static void updateIcon(Inventory inv, Material material){
		inv.setItem(9, CHANGEICON(material));
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CHANGEICON(Material material){
		ItemStack i = new ItemStack(material, 1);
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
		ItemStack i = new ItemStack(Material.LEGACY_WATCH, 1);
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
		ItemStack i = new ItemStack(Material.LEGACY_WATCH, 1);
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
		ItemStack i = new ItemStack(Material.LEGACY_WATCH, 1);
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
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
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
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum �ndern der Leben");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "lives");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum �ndern der Leben");
			meta.setLore(l);
		}
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ITEMSIGN(Location loc){
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
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
	public static ItemStack ITEMCHANCESIGN(Location loc){
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum Einstellen der Item-Seltenheit");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "chance");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum Einstellen der Item-Seltenheit");
			meta.setLore(l);
		}
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack EVENTSIGN(Location loc){
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		List<String> l = new ArrayList<>();
		if (loc != null ){
			l.add(ChatColor.GRAY + Integer.toString(loc.getBlockX()) + ", " + Integer.toString(loc.getBlockY()) + ", " + Integer.toString(loc.getBlockZ()));
			meta.setDisplayName(ChatColor.GREEN + "Schild zum Deaktivieren von Events");
			meta.setLore(l);
		}else{
			l.add(ChatColor.GRAY + "Schreibe " + ChatColor.GREEN + "events");
			l.add(ChatColor.GRAY + "auf ein Schild");
			meta.setDisplayName(ChatColor.RED + "Schild zum Deaktivieren von Events");
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
		ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)2);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Setze den " + (collection.size() + 1) + " Spielerspawn");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack DELETEPLAYERSPAWNS(ArrayList<Location> collection){
		ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)14);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Entferne alle " + collection.size() + " Spielerpawns");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack SHOWITEMSPAWNS(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.LEGACY_MOB_SPAWNER, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Zeige alle Itemspawnpunkte");
		List<String> l = new ArrayList<>();
		for (Location loc : itemspawns){
			l.add(ChatColor.GRAY + ">> " +  + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		}
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack ADDITEMSPAWN(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)2);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Setze den " + (itemspawns.size() + 1) + " Itemspawn");
		i.setItemMeta(meta);
		return i;
	}
	public static ItemStack DELETEITEMSPAWNS(ArrayList<Location> itemspawns){
		ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)14);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Entferne alle " + itemspawns.size() + " Itemspawns");
		i.setItemMeta(meta);
		return i;
	}

    public static ItemStack SHOWTELEPORTERS(ArrayList<Location> teleporters){
        ItemStack i = new ItemStack(Material.LEGACY_ENDER_PORTAL_FRAME, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Zeige alle Teleporter");
        List<String> l = new ArrayList<>();
        for (Location loc : teleporters){
            l.add(ChatColor.GRAY + ">> " + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
        }
        meta.setLore(l);
        i.setItemMeta(meta);
        return i;
    }
    public static ItemStack ADDTELEPORTER(ArrayList<Location> teleporters){
        ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)2);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Setze den " + (teleporters.size() + 1) + " Teleporter");
        i.setItemMeta(meta);
        return i;
    }
    public static ItemStack DELETETELEPORTERS(ArrayList<Location> teleporters){
        ItemStack i = new ItemStack(Material.LEGACY_INK_SACK, 1, (byte)14);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Entferne alle " + teleporters.size() + " Teleporter");
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
		ItemStack i = new ItemStack(Material.LEGACY_MOB_SPAWNER, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Itemspawnpunkte");
		List<String> l = new ArrayList<>();
		if (itemspawns.size() < 1) l.add(ChatColor.RED + Integer.toString(itemspawns.size()) + " Itemspawns");
		if (itemspawns.size() > 0) l.add(ChatColor.GREEN + Integer.toString(itemspawns.size()) + " Itemspawns");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
    public static ItemStack TELEPORTER(ArrayList<Location> teleporter){
        ItemStack i = new ItemStack(Material.LEGACY_ENDER_PORTAL_FRAME, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Teleporter");
        List<String> l = new ArrayList<>();
        if (teleporter.size() < 2) l.add(ChatColor.RED + Integer.toString(teleporter.size()) + " Teleporter");
        if (teleporter.size() > 1) l.add(ChatColor.GREEN + Integer.toString(teleporter.size()) + " Teleporter");
        meta.setLore(l);
        i.setItemMeta(meta);
        return i;
    }
	public static ItemStack SIGNS(Location leavesign, Location livesign, Location itemsign, Location itemchancesign, Location eventsign){
		ItemStack i = new ItemStack(Material.LEGACY_SIGN, 1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Schilder");
		List<String> l = new ArrayList<>();
		if (leavesign != null){
			l.add(ChatColor.GREEN + "Leavesign");
		}else l.add(ChatColor.RED + "Leavesign");
		if (itemsign != null){
			l.add(ChatColor.GREEN + "Itemsign");
		}else l.add(ChatColor.RED + "Itemsign");
		if (itemchancesign != null){
			l.add(ChatColor.GREEN + "Raritysign");
		}else l.add(ChatColor.RED + "Raritysign");
		if (livesign != null){
			l.add(ChatColor.GREEN + "Livesign");
		}else l.add(ChatColor.RED + "Livesign");
		if (eventsign != null){
			l.add(ChatColor.GREEN + "Eventsign");
		}else l.add(ChatColor.RED + "Eventsign");
		meta.setLore(l);
		i.setItemMeta(meta);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack EmptySpace(){
		ItemStack item = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, DyeColor.BLACK.getDyeData());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack EmptySpace_GRAY(){
		ItemStack item = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, DyeColor.GRAY.getDyeData());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CREATE(boolean exists){
		ItemStack i = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, DyeColor.GREEN.getDyeData());
		ItemMeta meta = i.getItemMeta();
		if (exists == false) meta.setDisplayName(ChatColor.WHITE + "Erstellen");
		if (exists == true) meta.setDisplayName(ChatColor.WHITE + "�berschreiben");
		i.setItemMeta(meta);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CANCEL(){
		ItemStack i = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, DyeColor.ORANGE.getDyeData());
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Abbrechen");
		i.setItemMeta(meta);
		return i;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack DELETE(boolean exists){
		if (exists){
			ItemStack i = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, DyeColor.RED.getDyeData());
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "L�schen");
			i.setItemMeta(meta);
			return i;
		}
		return EmptySpace_GRAY();
	}
	
}
