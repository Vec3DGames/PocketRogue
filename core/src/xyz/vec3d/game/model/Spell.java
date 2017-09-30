package xyz.vec3d.game.model;

/**
 * Created by Daron on 9/28/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 *
 * Represents a spell object.
 */

public class Spell {

    private int id;
    private int damage;
    private int cooldown;

    private float manaCost;
    private float speed;

    private String spellId;
    private String spellName;

    Spell(Definition spellDefinition) {
        id = (int) spellDefinition.getProperty(DefinitionProperty.ID);
        damage = (int) spellDefinition.getProperty(DefinitionProperty.DAMAGE);
        cooldown = (int) spellDefinition.getProperty(DefinitionProperty.COOLDOWN);

        manaCost = Float.valueOf(spellDefinition.getProperty(DefinitionProperty.MANA_COST).toString());
        speed = Float.valueOf(spellDefinition.getProperty(DefinitionProperty.MOVE_SPEED).toString());

        spellId = (String)spellDefinition.getProperty(DefinitionProperty.SPELL_ID);
        spellName = (String)spellDefinition.getProperty(DefinitionProperty.NAME);
    }

    Spell(int id, int damage, int cooldown, float manaCost, float speed, String spellId, String spellName) {
        this.id = id;
        this.damage = damage;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.speed = speed;
        this.spellId = spellId;
        this.spellName = spellName;
    }

    public int getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public float getManaCost() {
        return manaCost;
    }

    public float getSpeed() {
        return speed;
    }

    public String getSpellId() {
        return spellId;
    }

    public String getSpellName() {
        return spellName;
    }
}
