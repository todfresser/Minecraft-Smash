package todfresser.smash.mobs;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Set;
import java.util.UUID;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import todfresser.smash.main.Smash;
import todfresser.smash.map.Game;

public class SmashEntity{
	
	private final LivingEntity entityBukkit;
	private final EntityInsentient entityMinecraft;
	private final SmashEntityAttributes attributes;
	private final Game game;
	private boolean canbeHit;
	private UUID creator;

	public static SmashEntity spawn(Game game, Location location, SmashEntityAttributes attributes) {
		return spawn(game, null, location, attributes);
	}

	public static SmashEntity spawn(Game game, Player creator, Location location, SmashEntityAttributes attributes) {
		SmashEntity entity = new SmashEntity(game, location, attributes);
		entity.setCreator(creator);
		game.registerEntity(entity);
		return entity;
	}

	private SmashEntity(Game game, Location location, SmashEntityAttributes attributes){
		this.attributes = new SmashEntityAttributes(attributes);
		this.game = game;
		this.entityBukkit = game.getWorld().spawn(location, attributes.getEntityClass());
		this.entityMinecraft = (EntityInsentient) ((CraftEntity) entityBukkit).getHandle();
		this.canbeHit = true;
		if (entityMinecraft instanceof EntityFlying) {
			entityMinecraft.getAttributeInstance(GenericAttributes.FLYING_SPEED).setValue(attributes.getSpeed());
		} else {
			entityMinecraft.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(attributes.getSpeed());
		}
		entityMinecraft.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(attributes.getFollowRange());
		prepareTargetSelectors();
	}

	public LivingEntity getEntity() {
		return entityBukkit;
	}

	public EntityInsentient getEntityHandle() {
		return entityMinecraft;
	}

	public Game getGame() {
		return game;
	}

	public void setCreator(Player player) {
		if (player != null) {
			this.creator = player.getUniqueId();
			entityBukkit.setCustomName(ChatColor.BLUE + player.getDisplayName());
			entityBukkit.setCustomNameVisible(true);
		} else {
			this.creator = null;
			entityBukkit.setCustomNameVisible(false);
		}
	}

	public Player getCreator() {
		if (creator == null) {
			return null;
		} else {
			return Bukkit.getPlayer(creator);
		}
	}

	public boolean updateTarget() {
		Location entityLocation = entityBukkit.getLocation();
		Player near = null;
		if (game.getIngamePlayers().size() == 1) {
			near = Bukkit.getPlayer(game.getIngamePlayers().get(0));
		} else {
			double distance = Double.MAX_VALUE;
			for (UUID p : game.getIngamePlayers()){
				if (p != creator && Bukkit.getPlayer(p).getLocation().distance(entityLocation) < distance) {
					near = Bukkit.getPlayer(p);
					distance = near.getLocation().distance(entityLocation);
				}
			}
		}
		if (near != null) {
			setTarget(near);
			return true;
		}
		return false;
	}

	public SmashEntityAttributes getAttributes() {
		return attributes;
	}
	
	public void setVelocity(Vector v){
		if (!isDead()) entityBukkit.setVelocity(v);
	}
	
	public void damage(int damage){
		if (!canbeHit) return;
		if (attributes.getHealth() <= damage){
			kill(true);
			return;
		}
		canbeHit = false;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				attributes.setHealth(attributes.getHealth() - damage);
				canbeHit = true;
			}
		}.runTaskLater(Smash.getInstance(), 7);
	}
	
	public UUID getUniqueId(){
		return entityBukkit.getUniqueId();
	}
	
	public SmashEntity setTarget(Player player){
		entityMinecraft.setGoalTarget(((EntityLiving)((CraftEntity)player).getHandle()), TargetReason.CLOSEST_PLAYER, true);
		return this;
	}


	private void removeGoalSelectors(){
		try {
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			Field dField = PathfinderGoalSelector.class.getDeclaredField("d");
			dField.setAccessible(true);
			((EnumMap)cField.get(entityMinecraft.goalSelector)).clear();
			((Set)dField.get(entityMinecraft.goalSelector)).clear();
		} catch (Exception exc) {exc.printStackTrace();}
	}

	private void prepareTargetSelectors(){
		try {
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			Field dField = PathfinderGoalSelector.class.getDeclaredField("d");
			dField.setAccessible(true);
			((EnumMap)cField.get(entityMinecraft.targetSelector)).clear();
			((Set)dField.get(entityMinecraft.targetSelector)).clear();
			if (entityMinecraft instanceof EntityCreature) {
				entityMinecraft.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature)entityMinecraft));
			}
			entityMinecraft.targetSelector.a(2, new PathfinderGoalTargetGamePlayers(this));
		} catch (Exception exc) {exc.printStackTrace();}
	}
	
	public boolean isDead(){
		return entityBukkit.isDead();
	}
	public boolean isEmpty(){
		return entityBukkit.isEmpty();
	}
	
	public void kill(boolean slowDeath){
		game.removeEntity(this);
		removeGoalSelectors();
		if (slowDeath){
			entityMinecraft.getWorld().broadcastEntityEffect(entityMinecraft, (byte) 3);
			new BukkitRunnable() {
				@Override
				public void run() {
					if (entityBukkit != null && !isDead()){
						entityBukkit.remove();
						entityMinecraft.dead = true;
					}
				}
			}.runTaskLater(Smash.getInstance(), 6);
		}else{
			entityBukkit.remove();
			entityMinecraft.dead = true;
		}
	}
	
}
