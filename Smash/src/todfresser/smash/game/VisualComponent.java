package todfresser.smash.game;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VisualComponent {

    ItemStack show(ComponentInventory inventory);

    void onContentInteraction(Player player, ComponentInventory inventory);

}
