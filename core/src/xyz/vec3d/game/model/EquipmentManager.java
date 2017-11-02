package xyz.vec3d.game.model;

import java.util.HashMap;
import java.util.Map;

import xyz.vec3d.game.model.Item.ItemType;

/**
 * Created by Daron on 4/16/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * An {@link Inventory} will have an EquipmentManager in order to separate the
 * code for equipment and pure inventory from each other.
 */

public class EquipmentManager {

    private Map<ItemType, ItemStack> equipment;

    /**
     * The parent Inventory object.
     */
    private Inventory inventory;

    EquipmentManager(Inventory inventory) {
        this.inventory = inventory;
        equipment = new HashMap<>();
    }

    boolean equipItem(ItemStack itemStack) {
        ItemType slot = itemStack.getItem().getType();
        if (!itemStack.getItem().isEquipable()) {
            return false;
        }
        if (equipment.containsKey(slot)) {
            ItemStack oldItem = equipment.remove(slot);
            if (oldItem.equals(itemStack)) {
                itemStack.unequipItem();
            } else {
                oldItem.unequipItem();
                itemStack.equipItem();
                equipment.put(slot, itemStack);
            }
        } else {
            itemStack.equipItem();
            equipment.put(slot, itemStack);

        }
        return true;
    }

    @Override
    public String toString() {
        String s = "[Equipment]";
        for (ItemType slot : equipment.keySet()) {
            s += (equipment.get(slot) + "\n");
        }
        return s;
    }

    public Item getItem(ItemType slot) {
        return equipment.get(slot).getItem();
    }

    /*public ArrayList<ItemStack> getEquipment() {
        return (ArrayList<ItemStack>) equipment.values();
    }*/
    public float getBaseDamage() {
        float damage = 0f;

        for (ItemStack item : equipment.values()) {
            damage += item.getItem().getBonus(Item.ATTACK);
        }

        return damage;
    }

    public float getAttackSpeed() {
        float attackSpeed = 0f;

        for (ItemStack item : equipment.values()) {
            attackSpeed += item.getItem().getBonus(Item.ATTACK_SPEED);
        }

        return attackSpeed;
    }

    /**
     * Returns the defense bonuses for melee, magic and range attacks.
     *
     * @return An array of the bonuses index such that 0 is melee defense, 1 is
     * magic defense and 2 is range defense.
     */
    public int[] getDefenseBonuses() {
        int meleeDefense = 1, magicDefense = 1, rangeDefense = 1;

        for (ItemStack item : equipment.values()) {
            meleeDefense += item.getItem().getBonus(Item.MELEE_DEFENSE);
            magicDefense += item.getItem().getBonus(Item.MAGIC_DEFENSE);
            rangeDefense += item.getItem().getBonus(Item.RANGE_DEFENSE);
        }

        return new int[] {meleeDefense, magicDefense, rangeDefense};
    }
}
