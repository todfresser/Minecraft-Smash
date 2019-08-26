package todfresser.smash.game.components;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import todfresser.smash.game.MapComponent;

public class MaterialComponent implements MapComponent {

    private String identifier;

    private Material material = Material.VOID_AIR;


    public MaterialComponent(String identifier) {
        this.identifier = identifier;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void read(FileConfiguration cfg) {
        material = Material.getMaterial(cfg.getString(identifier));
    }

    @Override
    public void write(FileConfiguration cfg) {
        cfg.set(identifier, material);
    }

    @Override
    public boolean validate() {
        return true;
    }
}
