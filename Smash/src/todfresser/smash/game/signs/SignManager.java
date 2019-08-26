package todfresser.smash.game.signs;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import todfresser.smash.game.Game;

import java.util.ArrayList;
import java.util.UUID;

public class SignManager implements Listener {

    private static ArrayList<JoinSign> signs = new ArrayList<>();

    public static void bindSign(Player player, Game game) {
        for (JoinSign sign : signs) {
            if (sign.creator == player.getUniqueId()) {
                sign.bind(game);
            }
        }
    }

    public static void unbindSign(Game g) {
        for (JoinSign sign : signs) {
            sign.bind(null);
        }
    }

    @EventHandler
    public void onSignChangeEvent(SignChangeEvent e){

    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e){

    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){

    }

    private static class JoinSign {
        private UUID creator = null;
        private Sign s;
        private Game game = null;

        public JoinSign(Sign sign) {
            this.s = sign;

            s.setLine(0, ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
            s.setLine(3, ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
            update();
        }

        public void bind(Game game) {
            this.game = game;
            update();
        }

        public void setCreator(UUID creator) {
            this.creator = creator;
            update();
        }

        public void onInteract(Player player) {
            if (creator == null) {
                if (game == null) {

                }
            }
        }

        private void update() {
            if (creator == null) {
                if (game == null) {
                    setText(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
                            ChatColor.BLUE + "Neues",
                            ChatColor.BLUE + "Spiel",
                            ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
                }else {
                    setText(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
                            ChatColor.GOLD + "Spiel wird",
                            ChatColor.GOLD + "erstellt",
                            ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
                }
            }else {
                setText(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]",
                        ChatColor.GOLD + "Spiel wird",
                        ChatColor.GOLD + "erstellt",
                        ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Smash" + ChatColor.GOLD + "]");
            }
            s.update();
        }

        private void setText(String l0, String l1, String l2, String l3) {
            s.setLine(0, l0);
            s.setLine(1, l1);
            s.setLine(2, l2);
            s.setLine(3, l3);
        }

    }

}
