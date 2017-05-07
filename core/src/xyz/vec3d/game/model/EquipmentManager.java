package xyz.vec3d.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daron on 4/16/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * An {@link Inventory} will have an EquipmentManager in order to separate the
 * code for equipment and pure inventory from each other.
 */

public class EquipmentManager {

    private Map<Item.ItemType, ItemStack> equipment;

    /**
     * The parent Inventory object.
     */
    private Inventory inventory;

    public EquipmentManager(Inventory inventory) {
        this.inventory = inventory;
        equipment = new HashMap<>();
    }

    public void equipItem(ItemStack itemStack) {
        Item.ItemType slot = itemStack.getItem().getType();
        //If there is already an item in the slot, get the old item, unequip it
        //and equip the new one.
        if (equipment.containsKey(slot)) {
            ItemStack oldItem = equipment.remove(slot);
            if (oldItem != null) {
                oldItem.unequipItem();
                itemStack.equipItem();
                equipment.put(slot, itemStack);
            }
        } else { //Otherwise, put the new item in the slot
            itemStack.equipItem();
            equipment.put(slot, itemStack);
        }
    }

    @Override
    public String toString() {
        String s = "[Equipment]";
        for (Item.ItemType slot : equipment.keySet()) {
            s += (equipment.get(slot) + "\n");
        }
        return s;
    }
}
