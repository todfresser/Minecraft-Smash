package todfresser.smash.signs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import todfresser.smash.items.ItemManager;
import todfresser.smash.main.Smash;
import todfresser.smash.map.Game;
import todfresser.smash.map.GameState;
import todfresser.smash.map.Map;
import todfresser.smash.map.MapEditor;
import todfresser.smash.map.MapEditorData;

public class SignManager implements Listener{
	public static ArrayList<SignJ> signs = new ArrayList<>();
	
	private static Inventory getLiveSignInventory(Game g){
		Inventory inv = Bukkit.createInventory(null, 18, ChatColor.GOLD + "Lebensanzahl");
		for (int i = 3; i <= 20; i++){
			inv.addItem(LIVEITEM(i, g.getMaxLives() == i));
		}
		return inv;
		
	}
	private static Inventory getItemSignInventory(Game g){
		Inventory inv = Bukkit.createInventory(null, 9*((int)((ItemManager.getAllItemDataIDs().size()-1)/9) + 1), ChatColor.GOLD + "Items");
		for (ItemStack item : ItemManager.getStandardDeactivationItems(g.getAllowedItemIDs())){
			inv.addItem(item);
		}
		return inv;
		
	}
	
	private static ItemStack LIVEITEM(int lives, boolean current){
		ItemStack i;
		if (current){
			i = new ItemStack(Material.INK_SACK, lives, (byte)10);
		}else i = new ItemStack(Material.INK_SACK, lives, (byte)1);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + Integer.toString(lives) + ChatColor.DARK_RED + " Leben");
		i.setItemMeta(meta);
		return i;
	}
	//public static HashMap<UUID, SignJ> creators = new HashMap<>();
	
	public static void removeGamefromSign(Game g){
		for (SignJ s : signs){
			if (s.hasGame()){
				if (s.getGame().getWorld().getName().equals(g.getWorld().getName())){
					s.removeGame();
					return;
				}
			}
		}
	}
	
	public static void update(Game g){
		for (SignJ s : signs){
			if (s.hasGame() == true){
				if (s.getGame().getWorld().getName().equals(g.getWorld().getName())){
					s.write(ChatColor.GOLD + "[" + ChatColor.BLUE + "Smash" + ChatColor.GOLD + "]",
							ChatColor.BOLD + g.getMap().getName(), 
							ChatColor.BLUE + Integer.toString(g.getAllPlayers().size()) + ChatColor.GOLD + "/" + ChatColor.BLUE + Integer.toString(g.getMap().getmaxPlayers()),
							ChatColor.RED + g.getGameState().getName());
					return;
				}
			}
		}
	}
	
	public static void loadSigns(){
		File file = new File("plugins/Smash", "Signs.txt");
		if (file.exists()){
			FileReader fr = null;
			try{
				fr = new FileReader(file);
			}catch (Exception e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			try{
				Location l;
				while((line = br.readLine()) != null){
					String[] s = line.split(",");
					l = new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
					if (l.getWorld().getBlockAt(l).getType().equals(Material.SIGN_POST) || l.getWorld().getBlockAt(l).getType().equals(Material.WALL_SIGN)){
						new SignJ(l).write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
								ChatColor.BLUE + "Neues", 
								ChatColor.BLUE + "Spiel",
								ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void save(){
		if (signs.size() > 0){
			File file = new File("plugins/Smash", "Signs.txt");
			String newLine = System.getProperty("line.separator");
			if (file.exists()){
				file.delete();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for (SignJ sign : signs){
					if (sign.getLocation().getBlock().getType().equals(Material.SIGN_POST) || sign.getLocation().getBlock().getType().equals(Material.WALL_SIGN)){
						bw.write(sign.getLocation().getWorld().getName() + "," + Integer.toString(sign.getLocation().getBlockX()) + "," + Integer.toString(sign.getLocation().getBlockY()) + "," + Integer.toString(sign.getLocation().getBlockZ()));
						bw.write(newLine);
					}
				}
				bw.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onNewSign(SignChangeEvent e){
		for (MapEditorData data : MapEditor.editors){
			if (data.SpielerID.equals(e.getPlayer().getUniqueId())){
				e.setCancelled(true);
				return;
			}
		}
		if (e.getLine(0).equals("[Smash]") && e.getLine(1).equals("")){
			if (e.getPlayer().hasPermission("SMASH.admin")){
				SignJ s = new SignJ(e.getBlock().getLocation());
				s.write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
						ChatColor.BLUE + "Neues", 
						ChatColor.BLUE + "Spiel",
						ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onSignclick(PlayerInteractEvent e){
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if ((e.getClickedBlock().getType().equals(Material.WALL_SIGN)) || (e.getClickedBlock().getType().equals(Material.SIGN_POST))){
				for (SignJ s : signs){
					if (s.getLocation().getBlockX() == e.getClickedBlock().getLocation().getBlockX() && s.getLocation().getBlockY() == e.getClickedBlock().getLocation().getBlockY() && s.getLocation().getBlockZ() == e.getClickedBlock().getLocation().getBlockZ()){
						if (s.getGame() != null){
							e.setCancelled(true);
							s.getGame().addPlayer(e.getPlayer());
							return;
						}else{
							if (s.Creator == null){
								e.setCancelled(true);
								s.Creator = e.getPlayer().getUniqueId();
								s.write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
										ChatColor.GOLD + "Spiel wird", 
										ChatColor.GOLD + "erstellt",
										ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
								Inventory i = Bukkit.createInventory(e.getPlayer(), 9*((int)(Map.getloadedMaps().size()/9) + 1), Smash.pr + ChatColor.GOLD + "Maps");
								int items = 0;
								for (Map m : Map.getloadedMaps()){
									items++;
									i.addItem(MaptoItemStack(m));
									if (items == 36) break;
								}
								e.getPlayer().openInventory(i);
								return;
							}
						}
					}
				}
				for (Game g : Game.getrunningGames()){
					if (g.getIngamePlayers().contains(e.getPlayer().getUniqueId())){
						if (g.getGameState().equals(GameState.Starting) || g.getGameState().equals(GameState.Lobby)){
							e.setCancelled(true);
							if (e.getClickedBlock().getLocation().equals(g.getMap().getleaveSign(g.getWorld()).getLocation())){
								e.getPlayer().performCommand("sm leave");
							}
							if (e.getClickedBlock().getLocation().equals(g.getMap().getItemSign(g.getWorld()).getLocation())){
								e.getPlayer().openInventory(getItemSignInventory(g));
							}
							if (e.getClickedBlock().getLocation().equals(g.getMap().getLiveSign(g.getWorld()).getLocation())){
								e.getPlayer().openInventory(getLiveSignInventory(g));
							}
						}else return;
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if (e.getClickedInventory() != null){
			for (SignJ s : signs){
				if (s.Creator != null && s.Creator.equals(e.getWhoClicked().getUniqueId())){
					Player p = (Player) e.getWhoClicked();
					e.setCancelled(true);
					if (e.getClickedInventory().getTitle().equals(Smash.pr + ChatColor.GOLD + "Maps")){
						if (e.getCurrentItem() != null){
							if (e.getCurrentItem().getType().equals(Material.AIR)) return;
							for (Map m : Map.getloadedMaps()){
								if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + ">>" + ChatColor.GOLD + m.getName() + ChatColor.BLUE + "<<")){
									p.closeInventory();
									Game g = new Game(m);
									s.addSigntoGame(g);
									s.Creator = null;
									update(g);
									g.addPlayer((Player) e.getWhoClicked());
									return;
								}
							}
						}
					}
				}
			}
			Player p = (Player) e.getWhoClicked();
			for (Game g : Game.getrunningGames()){
				if (g.containsPlayer(p)){
					if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || g.getGameState().equals(GameState.Running) || g.getGameState().equals(GameState.Ending)){
						e.setCancelled(true);
						return;
					}
					if (e.getClickedInventory().getTitle().equals(ChatColor.GOLD + "Lebensanzahl")){
						e.setCancelled(true);
						if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)){
							for (int i = 3; i <= 20; i++){
								if (LIVEITEM(i, false).getItemMeta().getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName())){
									g.changeMaxLives(i);
									p.closeInventory();
									return;
								}
							}
						}
					}
					if (e.getClickedInventory().getTitle().equals(ChatColor.GOLD + "Items")){
						e.setCancelled(true);
						if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)){
							for (int id : ItemManager.getAllItemDataIDs()){
								if (ItemManager.getItemData(id).getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName())){
									if (g.getAllowedItemIDs().contains(id)){
										if (g.getAllowedItemIDs().size() > 1){
											g.getAllowedItemIDs().remove(id);
											e.getClickedInventory().setItem(e.getSlot(), ItemManager.getStandardDeactivationItem(ItemManager.getItemData(id), false));
										}
									}else{
										g.getAllowedItemIDs().add(id);
										e.getClickedInventory().setItem(e.getSlot(), ItemManager.getStandardDeactivationItem(ItemManager.getItemData(id), true));
									}
									return;
								}
							}
						}
					return;
					}
				}
			}
			/*if (creators.containsKey(e.getWhoClicked().getUniqueId())){
				Player p = (Player) e.getWhoClicked();
				e.setCancelled(true);
				if (e.getClickedInventory().getTitle().equals(Smash.pr + ChatColor.GOLD + "Maps")){
					if (e.getCurrentItem() != null){
						for (Map m : Map.getloadedMaps()){
							if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + ">>" + ChatColor.GOLD + m.getName() + ChatColor.BLUE + "<<")){
								p.closeInventory();
								Game g = new Game(m);
								creators.get(p.getUniqueId()).addSigntoGame(g);
								creators.remove(p.getUniqueId());
								update(g);
							}
						}
					}
				}
			}*/
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		for (SignJ s : signs){
			if (s.Creator != null && s.Creator.equals(e.getPlayer().getUniqueId())){
				s.write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
					ChatColor.BLUE + "Neues", 
					ChatColor.BLUE + "Spiel",
					ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
				s.Creator = null;
			}
		}
		/*if (creators.containsKey(e.getPlayer().getUniqueId())){
			creators.get(e.getPlayer().getUniqueId()).write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
					ChatColor.BLUE + "Neues", 
					ChatColor.BLUE + "Spiel",
					ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
			creators.remove(e.getPlayer().getUniqueId());
		}*/
	}
	@EventHandler
	public void onPlayerleave(PlayerQuitEvent e){
		for (SignJ s : signs){
			if (s.Creator != null && s.Creator.equals(e.getPlayer().getUniqueId())){
				s.write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
					ChatColor.BLUE + "Neues", 
					ChatColor.BLUE + "Spiel",
					ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
				s.Creator = null;
			}
		}
		/*if (creators.containsKey(e.getPlayer().getUniqueId())){
			creators.get(e.getPlayer().getUniqueId()).write(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
					ChatColor.BLUE + "Neues", 
					ChatColor.BLUE + "Spiel",
					ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
			creators.remove(e.getPlayer().getUniqueId());
		}*/
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack MaptoItemStack(Map m){
		ItemStack item = new ItemStack(m.getTypeID(), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + ">>" + ChatColor.GOLD + m.getName() + ChatColor.BLUE + "<<");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + Integer.toString(m.getmaxPlayers()) + " Spieler");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
}
