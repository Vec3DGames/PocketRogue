package xyz.vec3d.game.model;

import java.util.ArrayList;
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

    public EquipmentManager(Inventory inventory) {
        this.inventory = inventory;
        equipment = new HashMap<>();
    }

    public boolean equipItem(ItemStack itemStack) {
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
    public float getTotalDamageBonuses() {
        float damage = 0f;
        for (ItemStack item : equipment.values()) {
            damage += item.getItem().getBonus(Item.ATTACK);
        }
        return damage;
    }
}
