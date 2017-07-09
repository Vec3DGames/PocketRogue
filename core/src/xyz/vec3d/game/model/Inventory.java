package xyz.vec3d.game.model;

import java.util.ArrayList;

import xyz.vec3d.game.utils.Logger;

/**
 * Created by darakelian on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Represents the player's inventory via {@link ItemStack item stacks}.
 */
public class Inventory {

    /**
     * An ArrayList of ItemStacks that the Inventory is made of.
     */
    private ArrayList<ItemStack> items;

    /**
     * Represents all the items that are currently equipped by the player.
     */
    private EquipmentManager equipmentManager;

    /**
     * The maximum number of ItemStacks that the Inventory can have. This value
     * can be modified via things such as achievements or upgrades that increase
     * the inventory size. This value is not a static final because it can be
     * changed and it is not static because the max items in the inventory is
     * not something that needs to be known by other areas of the code easily.
     */
    private int maxItems = 40;

    private ItemStack[] hotBarItems;

    /**
     * Creates a new Inventory and pre-allocates the ArrayList to a size of
     * 40 ItemStacks.
     */
    public Inventory() {
        items = new ArrayList<>(maxItems);
        equipmentManager = new EquipmentManager(this);
        hotBarItems = new ItemStack[9];
    }

    /**
     * Retrieves the ArrayList of ItemStacks contained in the Inventory.
     *
     * @return The ArrayList of ItemStacks.
     */
    public ArrayList<ItemStack> getItems() {
        return items;
    }

    /**
     * Retrieves an array of ItemStacks instead of the ArrayList version. Not
     * really sure where having the ItemStacks as an array is more helpful than
     * an ArrayList but I'm bored so I'm writing this method anyways.
     *
     * @return The array version of the ItemStack ArrayList.
     */
    public ItemStack[] getItemsAsArray() {
        return items.toArray(new ItemStack[items.size()]);
    }

    /**
     * Retrieves the maximum amount of ItemStacks that can be stored in the
     * Inventory.
     *
     * @return The max number of possible ItemStacks.
     */
    public int getMaxItems() {
        return maxItems;
    }

    /**
     * Changes the max size of the Inventory by a specified integer modified.
     *
     * @param deltaMaxItems The amount to change the Inventory size by (can be negative).
     */
    public void changeMaxItems(int deltaMaxItems) {
        maxItems += deltaMaxItems;
    }

    /**
     * Adds an Item to the Inventory.
     *
     * @param itemToAdd The Item being added.
     */
    public void addItem(Item itemToAdd) {
        addItem(itemToAdd, 1);
    }

    /**
     * Adds n Items to the Inventory where n is the quantity specified in the
     * method.
     *
     * @param itemToAdd The Item being added.
     * @param quantity The amount of the Item to add.
     */
    private void addItem(Item itemToAdd, int quantity) {
        addItem(new ItemStack(itemToAdd, quantity));
    }

    /**
     * Adds an ItemStack to the Inventory. If the Inventory already contains the
     * ItemStack then the two stacks are merged. If the number of ItemStacks in
     * the Inventory would exceed the max inventory size by adding the
     * ItemStack, the ItemStack is not added to the Inventory.
     *
     * @param itemStackToAdd The ItemStack to add to the Inventory.
     */
    public void addItem(ItemStack itemStackToAdd) {
        if (hasItem(itemStackToAdd) && itemStackToAdd.getItem().isStackable()) {
            getItemStackForItem(itemStackToAdd.getItem()).merge(itemStackToAdd);
            Logger.log(Inventory.class, "Merged ItemStack with existing ItemStack.");
            return;
        }
        if (items.size() + 1 > getMaxItems()) {
            Logger.log(Inventory.class, "Tried adding an ItemStack to a full inventory.");
            return;
        }
        items.add(itemStackToAdd);
        Logger.log(Inventory.class, "Added new ItemStack to Inventory.");
    }

    /**
     * Removes 1 of the Item from the Inventory.
     *
     * @param itemToRemove The Item to remove.
     */
    public void removeItem(Item itemToRemove) {
        removeItem(itemToRemove, 1);
    }

    /**
     * Removes n Items from the ItemStack where n is a specified value that is
     * not to exceed the number of items existing in the ItemStack. If n is
     * greater than or equal to the number of items, the stack is removed from
     * the inventory.
     *
     * @param itemToRemove The Item being removed from the inventory.
     * @param amount The number of the item to remove.
     */
    private void removeItem(Item itemToRemove, int amount) {
        ItemStack stack = getItemStackForItem(itemToRemove);

        removeItem(stack, amount);
    }

    private void removeItem(ItemStack itemStack, int amount) {
        if (amount >= itemStack.getQuantity()) {
            items.remove(itemStack);
        }
        itemStack.removeQuantity(amount);
        //Fail-safe to ensure the ItemStack never has a negative size.
        if (itemStack.getQuantity() <= 0) {
            items.remove(itemStack);
        }
    }

    /**
     * Completely removes an Item from the inventory. This method should be
     * called as opposed to removeItem(item, int) when the number of items being
     * dropped is not important as long as the item is dropped.
     *
     * @param itemToDrop The Item being dropped.
     */
    public ItemStack dropItem(Item itemToDrop) {
        ItemStack itemStackDropping = getItemStackForItem(itemToDrop);
        items.remove(itemStackDropping);

        return itemStackDropping;
    }

    /**
     * Gets the ItemStack that contains an Item.
     *
     * @param item The Item that is supposed to be in a stack.
     *
     * @return The ItemStack that has the Item or null if one isn't found.
     */
    private ItemStack getItemStackForItem(Item item) {
        if (item == null) {
            return null;
        }
        for (ItemStack itemStack : items) {
            if (itemStack.getItem().equals(item)) {
                return itemStack;
            }
        }
        return null;
    }

    /**
     * Checks if the Inventory contains an Item by creating a dummy ItemStack
     * and searching the ArrayList of ItemStacks for it.
     *
     * @param itemToCheck The Item being searched for.
     *
     * @return True if the Inventory has the Item.
     */
    public boolean hasItem(Item itemToCheck) {
        return hasItem(new ItemStack(itemToCheck, 1));
    }

    /**
     * Checks if the Inventory contains an ItemStack.
     *
     * @param itemStackToCheck The ItemStack being searched for.
     *
     * @return True if the Inventory has the ItemStack.
     */
    public boolean hasItem(ItemStack itemStackToCheck) {
        return items.contains(itemStackToCheck);
    }

    /**
     * Tries to equip an item. Will do nothing if the item is not able to be
     * equipped.
     *
     * @param itemStack The item trying to be equipped.
     */
    public boolean equipItem(ItemStack itemStack) {
        return equipmentManager.equipItem(itemStack);
    }

    public EquipmentManager getEquipmentManager() {
        return equipmentManager;
    }

    public ItemStack[] getHotBarItems() {
        return hotBarItems;
    }

    public void setHotBarItem(int hotBarSlot, ItemStack itemToEquip) {
        for (int slot = 0; slot < hotBarItems.length; slot++) {
            if (hotBarItems[slot] != null && hotBarItems[slot].equals(itemToEquip)) {
                hotBarItems[slot] = null;
            }
        }
        hotBarItems[hotBarSlot] = itemToEquip;
    }

    public void useItem(ItemStack itemStack) {
        removeItem(itemStack, 1);
    }
}
