package todfresser.smash.game.components;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import todfresser.smash.game.*;
import todfresser.smash.game.signs.SignManager;

public class VisualGameMapSelectionComponent implements VisualComponent {

    private Map map;


    public VisualGameMapSelectionComponent(Map map) {
        this.map = map;
    }

    @Override
    public ItemStack show(ComponentInventory inventory) {
        return null;
    }

    @Override
    public void onContentInteraction(Player player, ComponentInventory inventory) {
        if (map.isValid()) {
            Game g = GameManager.createGame(map);
            player.closeInventory();
            g.addPlayer(player);
            SignManager.bindSign(player, g);
        }
    }
}
