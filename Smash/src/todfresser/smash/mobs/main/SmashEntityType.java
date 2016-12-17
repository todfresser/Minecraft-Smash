package todfresser.smash.mobs.main;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Zombie;

public enum SmashEntityType {
	BLAZE(Blaze.class, SmashEntity.BLAZE_AI, 4, 0.8, 1, 35, 0.2),
	ZOMBIE(Zombie.class, SmashEntity.ZOMBIE_AI, 6, 0.2, 0.4, 35, 0.3),
	SPIDER(CaveSpider.class, SmashEntity.SPIDER_AI, 3, 0.3, 0.5, 35, 0.45),
	ENDERMITE(Endermite.class, SmashEntity.ENDERMITE_AI, 4, 0.3, 0.3, 35, 0.3),
	VEX(Vex.class, SmashEntity.VEX_AI, 6, 0.15, 0.3, 35, 0.4);
	
	private Class<? extends LivingEntity> entity;
	private int attackdamage;
	private double velocitydamage;
	private double knockback;
	private SmashAI ai;
	private double movementspeed;
	private double followrange;

	private SmashEntityType(Class<? extends LivingEntity> entity, SmashAI ai, int attackdamage, double velocitydamage, double knockback, double followRange, double movementSpeed){
		this.entity = entity;
		this.attackdamage = attackdamage;
		this.velocitydamage = velocitydamage;
		this.knockback = knockback;
		this.ai = ai;
		this.followrange = followRange;
		this.movementspeed = movementSpeed;
	}

	public int getAttackdamage() {
		return attackdamage;
	}

	public double getVelocitydamage() {
		return velocitydamage;
	}

	public double getKnockback() {
		return knockback;
	}

	public Class<? extends LivingEntity> getEntityClass(){
		return entity;
	}
	
	public SmashAI getAI(){
		return ai;
	}
	
	
	public double getMovementspeed() {
		return movementspeed;
	}

	public double getFollowrange() {
		return followrange;
	}
}
