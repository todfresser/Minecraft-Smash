package todfresser.smash.game;

import org.bukkit.Bukkit;
import todfresser.smash.game.signs.SignManager;
import todfresser.smash.main.SmashNew;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private static ArrayList<Map> maps = new ArrayList<>();
    private static HashMap<String, GameType> types = new HashMap<>();
    private static ArrayList<Game> games = new ArrayList<>();

    public static void registerType(String type, GameType builder) {
        if (!types.containsKey(type)) {
            types.put(type, builder);
        }
    }

    public static boolean hasGameBuilder(String type) {
        return types.containsKey(type);
    }

    public static GameType getGameType(String type) {
        return types.get(type);
    }



    public static void loadAllMaps() {
        if (maps.isEmpty()) {
            File folder = new File("plugins/Smash/Maps");
            if (folder.exists()){
                String[] fileNames = folder.list();
                if (fileNames.length != 0){
                    for(int i = 0; i < fileNames.length; i++){
                        Map m = new Map(fileNames[i].replace(".yml", ""));
                        GameType gt = GameManager.getGameType(m.getType());
                        if (gt != null) {
                            gt.validateMap(m);
                            if (m.isValid()) maps.add(m);
                        }
                    }
                }
            }
        }
    }

    public static ArrayList<Map> getloadedMaps() {
        return maps;
    }

    public static Map getloadedMap(String name) {
        for (Map m : maps) {
            if (m.getName().equals(name)) return m;
        }
        return null;
    }

    public static Map getMap(String name, String type) {
        for (Map m : maps) {
            if (m.getName().equals(name)) return m;
        }
        return new Map(name, type);
    }



    public static Game createGame(Map map) {
        Game g = getGameType(map.getType()).buildGame(map);
        if (g != null) {
            games.add(g);
            g.onCreate();
        }
        return g;
    }

    static void destroyGame(Game g) {
        g.onDestroy();
        games.remove(g);

        SignManager.unbindSign(g);
    }

    public static ArrayList<Game> getrunningGames() {
        return games;
    }


}
