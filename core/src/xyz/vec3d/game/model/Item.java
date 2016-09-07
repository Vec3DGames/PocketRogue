package xyz.vec3d.game.model;

import xyz.vec3d.game.model.ItemDefinitionLoader.ItemDefinition;

/**
 * Created by Paul on 7/22/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Representation of a game item.
 */
public class Item {

    private int id;

    private int attackBonus;

    private int magicBonus;

    private int rangeBonus;

    private int dexterityBonus;

    private ItemType type;

    public Item(int id, ItemType type) {
        this.id = id;
        this.type = type;
    }

    public Item(int id, int attackBonus, int magicBonus, int rangeBonus,
                int dexterityBonus, ItemType type) {
        this.id = id;
        this.attackBonus = attackBonus;
        this.magicBonus = magicBonus;
        this.rangeBonus = rangeBonus;
        this.dexterityBonus = dexterityBonus;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public int getMagicBonus() {
        return magicBonus;
    }

    public int getRangeBonus() {
        return rangeBonus;
    }

    public int getDexterityBonus() {
        return dexterityBonus;
    }

    public ItemType getType() {
        return type;
    }

    /**
     * Gets the Item's name directly from the ItemDefinition.
     *
     * @return The Item's name.
     */
    public String getName() {
        return (String) ItemDefinitionLoader.getDefinition(getId()).getProperty(ItemProperty.NAME);
    }

    /**
     * Returns the boolean value for if the Item is stackable. A stackable Item
     * does not take up multiple slots in the Inventory and rather combines
     * quantities when multiple of the same Item are added to the Inventory.
     *
     * @return True if the Item is stackable.
     */
    public boolean isStackable() {
        return (Boolean) ItemDefinitionLoader.getDefinition(getId()).getProperty(ItemProperty.STACKABLE);
    }

    /**
     * Checks if an object is equivalent to this Item by checking if it is: an
     * Item; the same ItemType as this Item's type; and the same ID.
     *
     * @param object The object being compared.
     *
     * @return False if the above conditions are not met.
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Item && (((Item) object).getType().equals(getType())
                && (((Item) object).getId() == getId()));
    }

    public enum ItemType {
        HELMET, CHEST, LEGS, BOOTS, RING, PRIMARY_HAND, OFF_HAND;
    }
}
