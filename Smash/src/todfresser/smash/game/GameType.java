package todfresser.smash.game;

import org.bukkit.entity.Player;

public interface GameType {

    Game buildGame(Map map);

    void validateMap(Map map);

    MapEditor buildMapEditor(Player player, Map map);

}
