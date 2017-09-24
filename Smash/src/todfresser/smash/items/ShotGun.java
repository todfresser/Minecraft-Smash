package todfresser.smash.items;

import multiworld.addons.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.extrafunctions.VectorFunctions;
import todfresser.smash.items.main.SmashItem;
import todfresser.smash.map.Game;
import todfresser.smash.map.SmashPlayerData;
import todfresser.smash.particles.ParticleEffect;

public class ShotGun extends SmashItem{

    private String displayName = ChatColor.RED + "Shot" + ChatColor.DARK_GRAY + "gun";
    private int damage = 3;

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Material getType() {
        return Material.ARMOR_STAND;
    }

    @Override
    public int getmaxItemUses() {
        return 2;
    }

    @Override
    public int getSpawnChance() {
        return 8;
    }

    @Override
    public boolean hasOnRightClickEvent() {
        return true;
    }

    @Override
    public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
        playerdata.canUseItem = true;
        player.setVelocity(VectorFunctions.getStandardVector(player.getLocation().getYaw() - 180, 1).multiply(0.3));
        playerdata.registerItemRunnable(new BukkitRunnable() {
            Location loc = player.getLocation().add(0, 1, 0);
            Vector direction = loc.getDirection().normalize();
            double x = direction.getX();
            double y = direction.getY();
            double z = direction.getZ();
            Vector[] directions = new Vector[]{new Vector(x, y, z), new Vector(x + 0.15, y, z), new Vector(x - 0.15, y ,z), new Vector(x, y, z + 0.15), new Vector(x, y ,z - 0.15), new Vector(x, y + 0.15, z), new Vector(x, y - 0.15,z)};
            Location[] locations = new Location[]{loc, loc.clone(), loc.clone(), loc.clone(), loc.clone(), loc.clone(), loc.clone()};
            int t = 0;
            @Override
            public void run() {
                if (t > 5){
                    playerdata.cancelItemRunnable(this);
                    return;
                }
                boolean hit = false;
                for (int i = 0, length = directions.length; i < length; i++){
                    double x = directions[i].getX() * 1.3;
                    double y = directions[i].getY() * 1.3;
                    double z = directions[i].getZ() * 1.3;
                    Location loc = locations[i];
                    loc.add(x, y, z);
                    if (t > 1){
                        ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, loc, 40);
                    }else ParticleEffect.FLAME.display(0.2f, 0.1f, 0.2f, 0.0f, 3, loc, 40);
                    for (Entity e : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)){
                        if (e.getType().equals(EntityType.PLAYER)){
                            if (!((Player)e).getUniqueId().equals(player.getUniqueId())){
                                if (game.getIngamePlayers().contains(((Player)e).getUniqueId())){
                                    PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(loc.getYaw(), 0.5).multiply(0.8f), damage, false);
                                    hit = true;
                                }
                            }
                        }
                    }
                }
                if (hit){
                    playerdata.cancelItemRunnable(this);
                    return;
                }
                t++;
            }
        }, 0, 1);
        return true;
    }
}
