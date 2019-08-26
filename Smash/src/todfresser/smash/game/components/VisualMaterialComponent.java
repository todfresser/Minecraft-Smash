package todfresser.smash.game.components;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import todfresser.smash.game.MapComponent;
import todfresser.smash.game.VisualComponent;
import todfresser.smash.game.ComponentInventory;

public class VisualMaterialComponent implements MapComponent, VisualComponent {

    private String identifier;

    private Material material = Material.VOID_AIR;


    public VisualMaterialComponent(String identifier) {
        super();
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
        return false;
    }

    @Override
    public ItemStack show(ComponentInventory inventory) {
        return null;
    }

    @Override
    public void onContentInteraction(Player player, ComponentInventory inventory) {

    }
}
