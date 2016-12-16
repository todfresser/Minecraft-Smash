package todfresser.smash.mobs.main;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.LivingEntity;

public enum SmashEntityType {
	BLAZE(Blaze.class, SmashEntity.BLAZE_AI, 2, 4, 0.8, 1);
	
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
