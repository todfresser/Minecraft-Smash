package todfresser.smash.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import todfresser.smash.extrafunctions.Item;
import todfresser.smash.main.SmashNew;

import java.util.ArrayList;
import java.util.UUID;

public class ComponentInventory implements Listener {

    public static VisualComponent EMPTY_COMPONENT = new EmptyVisualComponent();

    private Inventory inventory;
    private VisualComponent[] components;

    private ArrayList<UUID> players;


    public ComponentInventory(String title, int rows) {
        int size = rows * 9;
        this.players = new ArrayList<>();
        this.inventory = Bukkit.createInventory(null, size, title);
        this.components = new VisualComponent[size];
    }


    public VisualComponent get(int row, int index) {
        return get(row * 9 + index);
    }

    public VisualComponent get(int index) {
        return components[index];
    }

    public void set(int row, int index, VisualComponent component) {
        set(row * 9 + index, component);
    }

    public void set(int index, VisualComponent component) {
        components[index] = component;
        component.show(this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getRows() {
        return components.length / 9;
    }


    public void show(Player p) {
        if (!players.contains(p.getUniqueId())) {
            players.add(p.getUniqueId());
            p.openInventory(this.inventory);
        }
    }

    protected void updateInventory() {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).updateInventory();
        }
    }



    void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(this, SmashNew.getInstance());
    }

    void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        players.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if (players.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == inventory) {
                components[e.getSlot()].onContentInteraction((Player)e.getWhoClicked(), this);
                updateInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        players.remove(e.getPlayer().getUniqueId());
    }


    public static class EmptyVisualComponent implements VisualComponent {

        private ItemStack item = Item.build(Material.VOID_AIR);

        @Override
        public ItemStack show(ComponentInventory inventory) {
            return item;
        }

        @Override
        public void onContentInteraction(Player player, ComponentInventory inventory) {

        }
    }

}
