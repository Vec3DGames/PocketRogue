package xyz.vec3d.game.model;

/**
 * Created by Paul on 7/22/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Representation of a game item.
 */
public class Item {

    /**
     * The Item's ID.
     */
    private int id;

    /**
     * Integer array of the bonuses that an Item has.
     */
    private int[] bonuses;

    /**
     * Type of Item this is which indicates what slot it goes in or if it is a
     * normal item such as a potion.
     */
    private ItemType type;

    /**
     * Constant representing the index in bonus array for the attack bonus.
     */
    public static final int ATTACK = 0;

    /**
     * Constant representing the index in bonus array for the magic bonus.
     */
    public static final int MAGIC = 1;

    /**
     * Constant representing the index in bonus array for the range bonus.
     */
    public static final int RANGE = 2;

    /**
     * Constant representing the index in bonus array for the dexterity bonus.
     */
    public static final int DEXTERITY = 3;

    /**
     * Creates a new Item with an ID and {@link ItemType} but no bonuses.
     *
     * @param id The Item ID.
     * @param type The ItemType.
     */
    public Item(int id, ItemType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Creates a new Item with an ID, bonus array and {@link ItemType}. This
     * constructor will be used when creating item drops from monsters and
     * chests as well as any other time an item is to be randomly generated.
     *
     * @param id The Item ID.
     * @param bonuses The Item bonuses.
     * @param type The ItemType.
     */
    public Item(int id, int[] bonuses, ItemType type) {
        this.id = id;
        this.bonuses = bonuses;
        this.type = type;
    }

    /**
     * Returns the Item ID to be used for looking up definitions.
     *
     * @return Item ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the integer array containing all the bonuses for an item.
     *
     * @return Integer array of bonuses indexed with the class constants.
     */
    public int[] getBonuses() {
        return bonuses == null ? new int[] {0, 0, 0, 0} : bonuses;
    }

    /**
     * Gets a specific bonus for the item.
     *
     * @param bonusType Slot in the bonuses array to return.
     *
     * @return The value of the specified bonus type.
     */
    public int getBonus(int bonusType) {
        return bonuses[bonusType];
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
        return (String) DefinitionLoader.getItemDefinition(getId()).getProperty(DefinitionProperty.NAME);
    }

    /**
     * Returns the boolean value for if the Item is stackable. A stackable Item
     * does not take up multiple slots in the Inventory and rather combines
     * quantities when multiple of the same Item are added to the Inventory.
     *
     * @return True if the Item is stackable.
     */
    public boolean isStackable() {
        return (Boolean) DefinitionLoader.getItemDefinition(getId()).getProperty(DefinitionProperty.STACKABLE);
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

    /**
     * Enum representing the slots that the item could go into.
     */
    public enum ItemType {
        HELMET, CHEST, LEGS, BOOTS, RING, PRIMARY_HAND, OFF_HAND, GENERAL;
    }
}
