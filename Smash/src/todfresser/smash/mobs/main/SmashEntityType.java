package todfresser.smash.mobs.main;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Zombie;

public enum SmashEntityType {
	BLAZE(Blaze.class, SmashEntity.BLAZE_AI, 2, 4, 0.8, 1),
	ZOMBIE(Zombie.class, SmashEntity.ZOMBIE_AI, 4, 6, 0.2, 0.4),
	SPIDER(CaveSpider.class, SmashEntity.SPIDER_AI, 3, 3, 0.3, 0.5),
	ENDERMITE(Endermite.class, SmashEntity.ENDERMITE_AI, 1, 4, 0.3, 0.3),
	VEX(Vex.class, SmashEntity.VEX_AI, 1, 8, 0.15, 0.3);
	
	private Class<? extends LivingEntity> entity;
	private int maxhealth;
	private int attackdamage;
	private double velocitydamage;
	private double knockback;
	private SmashAI ai;
	
	
	private SmashEntityType(Class<? extends LivingEntity> entity, SmashAI ai, int maxhealth, int attackdamage, double velocitydamage, double knockback){
		this.entity = entity;
		this.maxhealth = maxhealth;
		this.attackdamage = attackdamage;
		this.velocitydamage = velocitydamage;
		this.knockback = knockback;
		this.ai = ai;
	}
	
	public int getMaxhealth() {
		return maxhealth;
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
}
