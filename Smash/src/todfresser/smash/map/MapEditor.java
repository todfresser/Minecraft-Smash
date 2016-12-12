package todfresser.smash.map;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.main.Smash;
import todfresser.smash.map.MapEditorItems.EditorInventoryType;
import todfresser.smash.particles.ParticleEffect;

public class MapEditor implements Listener{
	public static List<MapEditorData> editors = new ArrayList<>();
	public static List<UUID> cantClick = new ArrayList<>();
	
	//public static List<UUID> players = new ArrayList<>();
	
	public static void open(Player p, String MapName){
		for (MapEditorData d : editors){
			if (d.SpielerID.equals(p.getUniqueId())){
				if (d.name.equals(MapName) == false){
					p.sendMessage(Smash.pr + "Du erstellst schon die Map " + d.name + ".");
					return;
				}else{
					cantClick.add(p.getUniqueId());
					p.openInventory(d.Inv);
					return;
				}
			}
			if (d.name.equals(MapName)){
				p.sendMessage(Smash.pr + "Diese Map wird bereits von " + Bukkit.getPlayer(d.SpielerID) + " erstellt.");
				return;
			}
		}
		if (Map.getloadedMapNames().contains(MapName)){
			MapEditorData e = new MapEditorData(p.getUniqueId(), Map.getMapfromString(MapName));
			editors.add(e);
			cantClick.add(p.getUniqueId());
			p.openInventory(e.Inv);
		}else{
			MapEditorData e = new MapEditorData(p.getUniqueId(), MapName);
			editors.add(e);
			cantClick.add(p.getUniqueId());
			p.openInventory(e.Inv);
		}
	}
	public static MapEditorData getMapEditorDatafromPlayer(Player p){
		for (MapEditorData d : editors){
			if (d.SpielerID.equals(p.getUniqueId())){
				return d;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if (cantClick.contains(((Player) e.getPlayer()).getUniqueId())){
			cantClick.remove(((Player) e.getPlayer()).getUniqueId());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if (!e.getWhoClicked().getType().equals(EntityType.PLAYER)) return;
		if (cantClick.contains(((Player) e.getWhoClicked()).getUniqueId())){
			e.setCancelled(true);
			MapEditorData d = null;
			for (MapEditorData data : editors){
				if (data.SpielerID.equals(e.getWhoClicked().getUniqueId())){
					d = data;
				}
			}
			if (d == null) return;
			if (e.getClickedInventory() == null) return;
			if (e.getCurrentItem() == null) return;
			if (e.getCurrentItem().getItemMeta() == null) return;
			if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
			ItemMeta i = e.getCurrentItem().getItemMeta();
			if (i.getDisplayName().equals(ChatColor.WHITE + "Globale Spawnpunkte")){
				MapEditorItems.setInventory(d, EditorInventoryType.GLOBALSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(ChatColor.WHITE + "Spielerspawnpunkte")){
				MapEditorItems.setInventory(d, EditorInventoryType.PLAYERSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(ChatColor.WHITE + "Itemspawnpunkte")){
				MapEditorItems.setInventory(d, EditorInventoryType.ITEMSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(ChatColor.WHITE + "Schilder")){
				MapEditorItems.setInventory(d, EditorInventoryType.COMMANDSIGNS);
				return;
			}
			
			
			if (i.getDisplayName().equals(ChatColor.WHITE + "Löschen")){
				Map.getMapfromString(d.name).delete();
				d.exists = false;
				MapEditorItems.setInventory(d, EditorInventoryType.SAVEDELETE);
				((Player) e.getWhoClicked()).sendMessage(Smash.pr + "Um das Löschen rückgängig zu machen,");
				((Player) e.getWhoClicked()).sendMessage(Smash.pr + "klicke erneut auf Erstellen!");
				return;
			}
				
			if (i.getDisplayName().equals(MapEditorItems.CANCEL().getItemMeta().getDisplayName())){
				((Player) e.getWhoClicked()).closeInventory();
				if (cantClick.contains(e.getWhoClicked().getUniqueId())) cantClick.remove(e.getWhoClicked().getUniqueId());
				editors.remove(d);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.CREATE(d.exists).getItemMeta().getDisplayName())){
				if (d.playerspawns.size() > 1 && d.itemspawns.size() > 1 && d.leavesign != null && d.livesign != null && d.itemsign != null && d.itemchancesign != null && d.eventsign != null && d.leavepoint != null && d.spectatorspawn != null && d.worldtoCopy != null && d.lobbyspawnpoint != null){
					((Player) e.getWhoClicked()).closeInventory();
					cantClick.remove(e.getWhoClicked().getUniqueId());
					editors.remove(d);
					d.time = (int) Bukkit.getWorld(d.worldtoCopy).getTime();
					d.create();
				}else e.getWhoClicked().sendMessage(Smash.pr + "ERROR: Du hast die Map noch nicht vollständig erstellt.");
				return;
			}
			
			
			if (i.getDisplayName().equals(MapEditorItems.ADDPLAYERSPAWN(d.playerspawns).getItemMeta().getDisplayName())){
				Location l = new Location(e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation().getBlockX() + 0.5, e.getWhoClicked().getLocation().getY(), e.getWhoClicked().getLocation().getBlockZ() + 0.5);
				l.setYaw(e.getWhoClicked().getLocation().getYaw());
				d.playerspawns.add(l);
				MapEditorItems.setInventory(d, EditorInventoryType.PLAYERSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.DELETEPLAYERSPAWNS(d.playerspawns).getItemMeta().getDisplayName())){
				d.playerspawns.clear();
				MapEditorItems.setInventory(d, EditorInventoryType.PLAYERSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.SHOWPLAYERSPAWNS(d.playerspawns).getItemMeta().getDisplayName())){
				showLocations((Player) e.getWhoClicked(), d.playerspawns);
				((Player) e.getWhoClicked()).closeInventory();
				MapEditorItems.setInventory(d, EditorInventoryType.PLAYERSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.ADDITEMSPAWN(d.itemspawns).getItemMeta().getDisplayName())){
				Location l = new Location(e.getWhoClicked().getWorld(), e.getWhoClicked().getLocation().getBlockX() + 0.5, e.getWhoClicked().getLocation().getY(), e.getWhoClicked().getLocation().getBlockZ() + 0.5);
				d.itemspawns.add(l);
				MapEditorItems.setInventory(d, EditorInventoryType.ITEMSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.DELETEITEMSPAWNS(d.itemspawns).getItemMeta().getDisplayName())){
				d.itemspawns.clear();
				MapEditorItems.setInventory(d, EditorInventoryType.ITEMSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.SHOWITEMSPAWNS(d.itemspawns).getItemMeta().getDisplayName())){
				showLocations((Player) e.getWhoClicked(), d.itemspawns);
				((Player) e.getWhoClicked()).closeInventory();
				MapEditorItems.setInventory(d, EditorInventoryType.ITEMSPAWNS);
				return;
			}
			
			if (i.getDisplayName().equals(MapEditorItems.SPECTATORSPAWN(d.spectatorspawn).getItemMeta().getDisplayName())){
				d.spectatorspawn = e.getWhoClicked().getLocation();
				MapEditorItems.setInventory(d, EditorInventoryType.GLOBALSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.LOBBY(d.lobbyspawnpoint).getItemMeta().getDisplayName())){
				d.lobbyspawnpoint = e.getWhoClicked().getLocation();
				MapEditorItems.setInventory(d, EditorInventoryType.GLOBALSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.LEAVEPOINT(d.leavepoint).getItemMeta().getDisplayName())){
				d.leavepoint = e.getWhoClicked().getLocation();
				MapEditorItems.setInventory(d, EditorInventoryType.GLOBALSPAWNS);
				return;
			}
			if (i.getDisplayName().equals(MapEditorItems.CHANGEICON(d.type).getItemMeta().getDisplayName())){
				if ((e.getWhoClicked().getInventory().getItemInMainHand() != null) && (!e.getWhoClicked().getInventory().getItemInMainHand().getType().equals(Material.AIR))){
					d.type = e.getWhoClicked().getInventory().getItemInMainHand().getTypeId();
					MapEditorItems.updateIcon(d.Inv, d.type);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onNewSign(SignChangeEvent e){
		for (MapEditorData data : editors){
			if (data.SpielerID.equals(e.getPlayer().getUniqueId())){
				Sign s = (Sign) e.getBlock().getState();
				if (e.getLine(0).equals("leave")){
					s.setLine(0, "");
					s.setLine(1, ChatColor.BLUE + "Spiel");
					s.setLine(2, ChatColor.BLUE + "verlassen");
					s.setLine(3, "");
					s.update();
					e.setCancelled(true);
					data.leavesign = s.getLocation();
					MapEditorItems.setInventory(data, EditorInventoryType.COMMANDSIGNS);
					return;
				}
				if (e.getLine(0).equals("lives")){
					s.setLine(0, "");
					s.setLine(1, ChatColor.BLUE + "Lebensanzahl");
					s.setLine(2, ChatColor.BLUE + "verändern");
					s.setLine(3, "");
					s.update();
					e.setCancelled(true);
					data.livesign = s.getLocation();
					MapEditorItems.setInventory(data, EditorInventoryType.COMMANDSIGNS);
					return;
				}
				if (e.getLine(0).equals("items")){
					s.setLine(0, "");
					s.setLine(1, ChatColor.BLUE + "Items");
					s.setLine(2, ChatColor.BLUE + "deaktivieren");
					s.setLine(3, "");
					s.update();
					e.setCancelled(true);
					data.itemsign = s.getLocation();
					MapEditorItems.setInventory(data, EditorInventoryType.COMMANDSIGNS);
					return;
				}
				if (e.getLine(0).equals("chance")){
					s.setLine(0, "");
					s.setLine(1, ChatColor.BLUE + "Item");
					s.setLine(2, ChatColor.BLUE + "Seltenheit");
					s.setLine(3, "");
					s.update();
					e.setCancelled(true);
					data.itemchancesign = s.getLocation();
					MapEditorItems.setInventory(data, EditorInventoryType.COMMANDSIGNS);
					return;
				}
				if (e.getLine(0).equals("events")){
					s.setLine(0, "");
					s.setLine(1, ChatColor.BLUE + "Events");
					s.setLine(2, ChatColor.BLUE + "deaktivieren");
					s.setLine(3, "");
					s.update();
					e.setCancelled(true);
					data.eventsign = s.getLocation();
					MapEditorItems.setInventory(data, EditorInventoryType.COMMANDSIGNS);
					return;
				}
				return;
			}
		}
	}
	
	private static void showLocations(final Player p, final List<Location> collection){
		new BukkitRunnable() {
			int count = 50;
			@Override
			public void run() {
				if (count <= 0){
					this.cancel();
					return;
				}
				count--;
				for (Location l : collection){
					ParticleEffect.PORTAL.display(0.1f, 0.1f, 0.1f, 0, 15, l, 70);
				}
			}
		}.runTaskTimer(Smash.getInstance(), 0, 5);
	}
	/*private static void showLocations(Player p, List<Vector> collection, World w){
		new BukkitRunnable() {
			Location l;
			int count = 50;
			@Override
			public void run() {
				if (count <= 0){
					this.cancel();
					return;
				}
				count--;
				for (Vector v : collection){
					l = new Location(w, v.getX(), v.getY(), v.getZ());
					p.spigot().playEffect(l, Effect.PORTAL, 1, 1, 0.1f, 0.1f, 0.1f, 0.001f, 15, 50);
				}
			}
		}.runTaskTimer(Smash.getInstance(), 0, 5);
	}*/
}
