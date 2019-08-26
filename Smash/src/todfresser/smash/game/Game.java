package todfresser.smash.game;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Game implements Listener {

    protected abstract void onCreate();

    protected abstract void onDestroy();


    public abstract Map getMap();

    public abstract GameState getGameState();


    public abstract void addPlayer(Player player);

    public abstract void removePlayer(Player player);

}