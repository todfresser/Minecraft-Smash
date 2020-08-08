package todfresser.smash.mobs;

import org.bukkit.entity.LivingEntity;

public class SmashEntityAttributes {

    private final Class<? extends LivingEntity> entityClass;
    private int health;
    private int attackDamage;
    private double velocityDamage;
    private double knockback;
    private double speed;
    private double followRange;

    public SmashEntityAttributes(Class<? extends LivingEntity> entityClass, int health, int attackDamage,
                                 double velocityDamage, double knockback, double speed, double followRange) {
        this.entityClass = entityClass;
        this.health = health;
        this.attackDamage = attackDamage;
        this.velocityDamage = velocityDamage;
        this.knockback = knockback;
        this.speed = speed;
        this.followRange = followRange;
    }

    public SmashEntityAttributes(SmashEntityAttributes attributes) {
        this.entityClass = attributes.getEntityClass();
        this.health = attributes.getHealth();
        this.attackDamage = attributes.getAttackDamage();
        this.velocityDamage = attributes.getVelocityDamage();
        this.knockback = attributes.getKnockback();
        this.speed = attributes.getSpeed();
        this.followRange = attributes.getFollowRange();
    }

    public Class<? extends LivingEntity> getEntityClass() {
        return entityClass;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public double getVelocityDamage() {
        return velocityDamage;
    }

    public void setVelocityDamage(double velocityDamage) {
        this.velocityDamage = velocityDamage;
    }

    public double getKnockback() {
        return knockback;
    }

    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFollowRange() {
        return followRange;
    }

    public void setFollowRange(double followRange) {
        this.followRange = followRange;
    }
}
