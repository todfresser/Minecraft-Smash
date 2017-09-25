package todfresser.smash.map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import todfresser.smash.main.Smash;
import todfresser.smash.particles.ParticleEffect;

import java.util.ArrayList;

public class TeleporterRunnable extends BukkitRunnable{
    private final Game game;
    private final ArrayList<Location> teleporters;
    private Location first = null;
    private Location last = null;
    private int time = 0;
    private boolean scheduled = false;

    public TeleporterRunnable(Game game){
        teleporters = game.getMap().getTeleporters(game.getWorld());
        if (teleporters.size() > 2){
            this.runTaskTimer(Smash.getInstance(), 400, 10);
            scheduled = true;
        }
        this.game = game;
    }

    private Location getRandomTeleporter(){
        Location l = teleporters.get(Math.max((int)(Math.random() * teleporters.size()), teleporters.size() - 1));
        int i = 0;
        while ((l == first || l == last) && i < 4 ){
            l = teleporters.get(Math.max((int)(Math.random() * teleporters.size()), teleporters.size() - 1));
            i++;
        }
        return l;
    }

    private void resetTeleporters(){
        first = null;
        last = null;
        time = 0;
    }

    public boolean isScheduled(){
        return scheduled;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        scheduled = false;
    }

    @Override
    public void run() {
        if (first == null) first = getRandomTeleporter();
        if (last == null) last = getRandomTeleporter();
        time++;
        if (time < 4) return;
        ParticleEffect.PORTAL.display(0.5f, 1.0f, 0.5f, 0.0f, 8, first, 50);
        ParticleEffect.PORTAL.display(0.5f, 1.0f, 0.5f, 0.0f, 8, last, 50);
        for  (Entity e : last.getWorld().getNearbyEntities(last, 0.5, 1, 0.5)){
            if (e instanceof Player && game.getIngamePlayers().contains(e.getUniqueId())){
                e.teleport(first);
                resetTeleporters();
                return;
            }
        }
        for  (Entity e : first.getWorld().getNearbyEntities(first, 0.5, 1, 0.5)){
            if (e instanceof Player && game.getIngamePlayers().contains(e.getUniqueId())){
                e.teleport(last);
                resetTeleporters();
                return;
            }
        }
        if (time > 60){
            resetTeleporters();
            return;
        }
    }
}
