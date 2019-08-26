package todfresser.smash.game;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Map {

    private static final String PATH = "plugins/Smash/Maps";

    private final String name;
    private final String type;
    private boolean valid;

    private FileConfiguration cfg;

    public Map(String name) {
        this.name = name;

        File file = new File(PATH, name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        type = cfg.getString("core.gametype");
        valid = cfg.getBoolean("core.valid");
    }

    public Map(String name, String type) {
        this.name = name;

        File file = new File(PATH, name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(file);
        if (cfg.contains("core.gametype")) {
            this.type = cfg.getString("core.gametype");
        }else {
            this.type = type;
            cfg.set("core.gametype", type);
        }
        valid = cfg.getBoolean("core.valid", false);
    }

    public void save() {
        save(valid);
    }

    public void save(boolean valid) {
        cfg.set("core.valid", valid);
        File file = new File(PATH, name + ".yml");
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public <T extends MapComponent> void get(T component) {
        component.read(cfg);
    }

    public <T extends MapComponent> void set(T component) {
        component.write(cfg);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        valid = false;
    }

}
