package todfresser.smash.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import todfresser.smash.main.SmashNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class MapEditor implements Listener {

    private static HashMap<UUID, MapEditor> editors;

    public static void open(Player p, String mapName, String mapType) {
        if (editors.containsKey(p.getUniqueId())) {
            MapEditor editor = editors.get(p.getUniqueId());
            if (editor.getMap().getName().equals(mapName)) {
                editor.show();
            } else {

            }
        } else {
            GameType type = GameManager.getGameType(mapType);
            if (type != null) {
                Map m = GameManager.getMap(mapName, mapType);
                MapEditor editor = type.buildMapEditor(p, m);
                if (editor != null) {
                    editors.put(p.getUniqueId(), editor);
                    editor.show();
                    editor.registerListeners();
                }
            }
        }
    }

    public static void destroy(Player p) {
        MapEditor editor = editors.get(p.getUniqueId());
        if (editor != null) {
            editors.remove(p.getUniqueId());
            editor.unregisterListeners();
        }
    }



    private Map map;

    private ArrayList<MapComponent> components;
    private ComponentInventory inventory;

    private final Player player;
    private boolean visible;

    public MapEditor(Player p, Map m) {
        this.player = p;
        this.map = m;
        this.visible = false;

        this.components = new ArrayList<>();
        this.inventory = createInventory();
    }


    protected abstract ComponentInventory createInventory();

    public Map getMap() {
        return map;
    }

    public ComponentInventory getInventory() {
        return inventory;
    }

    public void show() {
        inventory.show(player);
    }

    public void save() {
        for (MapComponent comp : components) {
            map.set(comp);
        }
        map.save();
    }

    public void add(MapComponent component) {
        if (!components.contains(component)) components.add(component);
    }

    public void remove(MapComponent component) {
        components.remove(component);
    }


    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(this, SmashNew.getInstance());
        inventory.registerListeners();
    }

    private void unregisterListeners() {
        HandlerList.unregisterAll(this);
        inventory.unregisterListeners();
    }

}
