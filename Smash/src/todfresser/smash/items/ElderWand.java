package todfresser.smash.items;

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
import todfresser.smash.mobs.main.SmashEntity;
import todfresser.smash.mobs.main.SmashEntityType;
import todfresser.smash.particles.ParticleEffect;

public class ElderWand extends SmashItem{
    private final String displayName = ChatColor.DARK_PURPLE + "Elder Wand";
    private final int damage = 3;


    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Material getType() {
        return Material.STICK;
    }

    @Override
    public boolean isEnchanted() {
        return true;
    }

    @Override
    public int getmaxItemUses() {
        return 2;
    }

    @Override
    public int getSpawnChance() {
        return 4;
    }

    @Override
    public boolean hasOnRightClickEvent() {
        return true;
    }

    @Override
    public boolean onRightClickEvent(SmashPlayerData playerdata, Player player, Game game) {
        playerdata.canUseItem = true;
        playerdata.registerItemRunnable(new BukkitRunnable() {
            Location loc = player.getLocation().add(0, 1, 0);
            Vector direction = loc.getDirection().normalize();
            int t = 0;
            @Override
            public void run() {
                t++;
                direction.setY(direction.getY() - 0.005);
                double x = direction.getX() * 1.3;
                double y = direction.getY() * 1.3;
                double z = direction.getZ() * 1.3;
                loc.add(x, y, z);
                ParticleEffect.DRAGON_BREATH.display(0.1f, 0.1f, 0.1f, 0.0f, 1, loc, 40);
                for (Entity e : loc.getWorld().getNearbyEntities(loc, 1.5, 1.5, 1.5)){
                    if (e.getType().equals(EntityType.PLAYER)){
                        if (!(e.getUniqueId().equals(player.getUniqueId()))){
                            if (game.getIngamePlayers().contains(e.getUniqueId())){
                                PlayerFunctions.playOutDamage(game, (Player) e, player, VectorFunctions.getStandardVector(loc.getYaw(), 0.5).multiply(0.8f), damage, false);
                                playerdata.registerItemRunnable(new BukkitRunnable() {
                                    Player target = (Player) e;
                                    int t = 0;
                                    @Override
                                    public void run() {
                                        if (target.isOnline()){
                                            ParticleEffect.DRAGON_BREATH.display(t / 8f + 0.3f, 2f, t / 8f + 0.3f, 0.2f, t / 2 + 10, target.getLocation(), 40);
                                            if (t == 5 || t == 7 || t == 9){
                                                SmashEntity e = new SmashEntity(game, target.getLocation().add(Math.random() * 3 - 1.5, 1 + Math.random(), Math.random() * 3 - 1.5), SmashEntityType.VEX, 1);
                                                e.setTarget(target);
                                            }
                                            t++;
                                            if (t < 10) return;
                                        }
                                        playerdata.cancelItemRunnable(this);
                                    }
                                }, 0, 2);
                                playerdata.cancelItemRunnable(this);
                                return;
                            }
                        }
                    }
                }
                if (t > 50 || loc.getBlock().getType() != Material.AIR){
                    playerdata.cancelItemRunnable(this);
                    return;
                }
            }
        }, 0, 1);
        return true;
    }
}
