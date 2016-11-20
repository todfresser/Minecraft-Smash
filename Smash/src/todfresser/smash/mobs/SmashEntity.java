package todfresser.smash.mobs;

import java.lang.reflect.Field;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class SmashEntity {
	private Entity entity;
	private CraftEntity craftentity;
	private net.minecraft.server.v1_11_R1.Entity entityS;
	
	public SmashEntity(Location loc, Class<? extends Entity> entityclass){
		this.entity = loc.getWorld().spawn(loc, entityclass);
		this.craftentity = ((CraftEntity) entity);
		this.entityS = craftentity.getHandle();
	}
	
	public SmashEntity setDisplayName(String name){
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
		return this;
	}
	
	public SmashEntity setDisplayNameVisible(Boolean visible) {
        entity.setCustomNameVisible(visible);
        return this;
	}
	
	public SmashEntity playEffekt(EntityEffect entityeffect) {
        entity.playEffect(entityeffect);
        return this;
    }
	
	public void remove() {
        entity.remove();
        craftentity = null;
        entityS = null;
    }
	
	public SmashEntity setPassenger(Entity passenger) {
        entity.setPassenger(passenger);
        return this;
	}
	
	public SmashEntity setFireTicks(int ticks) {
        entity.setFireTicks(ticks);
        return this;
	}
	
	public SmashEntity teleport(Location location) {
        entity.teleport(location);
        return this;
	}
	
	public SmashEntity setYawPitch(float yaw,float pitch) {
        Location loc = entity.getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        teleport(loc);
        return this;
	}
	
	public void die() {
        entityS.die();
        craftentity = null;
        entityS = null;
	}
	
	public SmashEntity setInvisible(boolean invisible) {
        entityS.setInvisible(invisible);
        return this;
	}
	
	/*public SmashEntity noClip(boolean noClip) {
        entityS.noclip = noClip;
        return this;
	}*/
	
	public SmashEntity noMovement(boolean shouldntMove){
		NBTTagCompound tag = new NBTTagCompound();
        entityS.c(tag);
        tag.setBoolean("NoAI", shouldntMove);
        EntityLiving el = (EntityLiving) entityS;
        el.a(tag);
        return this;
	}
	
	public SmashEntity setInvulnerable(boolean invulnerable) {
        try {
                Field invulnerableField = net.minecraft.server.v1_11_R1.Entity.class.getDeclaredField("invulnerable");
                invulnerableField.setAccessible(true);
                invulnerableField.setBoolean(entityS, invulnerable);
        } catch (Exception ex) {
                ex.printStackTrace();
        }
        return this;
	}
	
	public SmashEntity setCanPickUpLoot(boolean canpickuploot) {
        NBTTagCompound tag = new NBTTagCompound();
        entityS.c(tag);
        tag.setBoolean("CanPickUpLoot", canpickuploot);
        EntityLiving el = (EntityLiving) entityS;
        el.a(tag);
        return this;
	}
	
	public SmashEntity setHealth(float health) {
        NBTTagCompound tag = new NBTTagCompound();
        entityS.c(tag);
        tag.setFloat("HealF", health);
        EntityLiving el = (EntityLiving) entityS;
        el.a(tag);
        return this;
	}
	
	public SmashEntity setCanDespawn(boolean candespawn) {
        candespawn = !candespawn;
        NBTTagCompound tag = new NBTTagCompound();
        entityS.c(tag);
        tag.setBoolean("PersistenceRequired", candespawn);
        EntityLiving el = (EntityLiving) entityS;
        el.a(tag);
        return this;
	}
	
	public SmashEntity walkToLocation(Location location, float speed) {
        ((CraftCreature) entity).getHandle().getNavigation().a(location.getX(), location.getY(), location.getZ(), speed);
        return this;
	}
	
}
