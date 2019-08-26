package todfresser.smash.basic.mobs.main;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_14_R1.EntityCreature;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.GenericAttributes;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import todfresser.smash.main.Smash;
import todfresser.smash.map.Game;
import todfresser.smash.basic.mobs.SmashBlazeAI;
import todfresser.smash.basic.mobs.SmashEndermiteAI;
import todfresser.smash.basic.mobs.SmashSpiderAI;
import todfresser.smash.basic.mobs.SmashVexAI;
import todfresser.smash.basic.mobs.SmashZombieAI;

public class SmashEntity{
	public final static SmashAI BLAZE_AI = new SmashBlazeAI();
	public final static SmashAI ZOMBIE_AI = new SmashZombieAI();
	public final static SmashAI SPIDER_AI = new SmashSpiderAI();
	public final static SmashAI ENDERMITE_AI = new SmashEndermiteAI();
	public final static SmashAI VEX_AI = new SmashVexAI();
	
	private LivingEntity e;
	private EntityCreature c;
	private final int maxhealth;
	private int health;
	private boolean canbeHit;
	private int ad;
	private double vdm;
	private double km;
	private EntityType type;
	
	public SmashEntity(Game g, Location loc, SmashEntityType type, int maxhealth){
		this(loc, type, maxhealth);
		Player near = null;
		for (UUID p : g.getIngamePlayers()){
			if (near == null || Bukkit.getPlayer(p).getLocation().distance(e.getLocation()) < near.getLocation().distance(e.getLocation())){
				near = Bukkit.getPlayer(p);
			}
		}
		if (near != null) setTarget(near);
		g.registerEntity(this);
	}
	public SmashEntity(Location loc, SmashEntityType type, int maxhealth){
		this.maxhealth = maxhealth;
		this.health = maxhealth;
		this.e = loc.getWorld().spawn(loc, type.getEntityClass());
		this.c = (EntityCreature)((EntityInsentient)((CraftEntity)e).getHandle());
		removeGoals();
		type.getAI().setupPathfinder(c, c.goalSelector, c.targetSelector);
		this.canbeHit = true;
		this.ad = type.getAttackdamage();
		this.vdm = type.getVelocitydamage();
		this.km = type.getKnockback();
		c.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(type.getMovementspeed());
		c.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(type.getFollowrange());
		this.type = e.getType();
	}
	
	public EntityType getType(){
		return type;
	}
	
	public void setAttackDamage(int damage){
		this.ad = damage;
	}
	
	public int getAttackDamage(){
		return ad;
	}
	
	public double getVelocityDamageMultiplier(){
		return vdm;
	}
	
	public double getKnockbackMultiplier(){
		return km;
	}
	
	public void setVelocity(Vector v){
		if (!isDead()) e.setVelocity(v);
	}
	
	public void damage(Game g, int damage){
		if (canbeHit == false) return;
		if (health <= damage){
			remove(g, true);
			return;
		}
		canbeHit = false;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				health = health - damage;
				canbeHit = true;
			}
		}.runTaskLater(Smash.getInstance(), 7);
	}
	
	public void heal(int heal){
		if (heal + this.health > maxhealth){
			this.health = maxhealth;
			return;
		}else this.health = this.health + heal;
		
	}
	
	public UUID getUniqueId(){
		return e.getUniqueId();
	}
	
	public SmashEntity setTarget(LivingEntity e){
		c.setGoalTarget(((EntityLiving)((CraftEntity)e).getHandle()), TargetReason.RANDOM_TARGET, false);
		return this;
	}
	
	private void removeGoals(){
		try {
		     Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
		     bField.setAccessible(true);
		     Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
		     cField.setAccessible(true);
		     bField.set(c.goalSelector, Sets.newLinkedHashSet());
		     cField.set(c.goalSelector, Sets.newLinkedHashSet());
		     bField.set(c.targetSelector, Sets.newLinkedHashSet());
		     cField.set(c.targetSelector, Sets.newLinkedHashSet());
		} catch (Exception exc) {exc.printStackTrace();}
	}
	
	public boolean isDead(){
		return e.isDead();
	}
	public boolean isEmpty(){
		return e.isEmpty();
	}
	
	public void remove(Game g, boolean slowDeath){
		g.removeEntity(this);
		removeGoals();
		if (slowDeath){
			c.getWorld().broadcastEntityEffect(c, (byte) 3);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (e != null && !isDead()){
						e.remove();
						c.dead = true;
						e = null;
						c = null;
					}
					
				}
			}.runTaskLater(Smash.getInstance(), 6);
		}else{
			e.remove();
			c.dead = true;
			e = null;
			c = null;
		}
	}
	
}
