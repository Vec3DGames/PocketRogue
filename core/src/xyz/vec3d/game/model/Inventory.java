package xyz.vec3d.game.model;

import java.util.ArrayList;

/**
 * Created by darakelian on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Represents the player's inventory via {@link ItemStack}s.
 */
public class Inventory {

    /**
     * An ArrayList of ItemStacks that the Inventory is made of.
     */
    private ArrayList<ItemStack> items;

    /**
     * The maximum number of ItemStacks that the Inventory can have. This value
     * can be modified via things such as achievements or upgrades that increase
     * the inventory size. This value is not a static final because it can be
     * changed and it is not static because the max items in the inventory is
     * not something that needs to be known by other areas of the code easily.
     */
    private int maxItems = 40;

    /**
     * Creates a new Inventory and pre-allocates the ArrayList to a size of
     * 40 ItemStacks.
     */
    public Inventory() {
        items = new ArrayList<ItemStack>(maxItems);
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
    public void addItem(Item itemToAdd, int quantity) {
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
        if (hasItem(itemStackToAdd)) {
            getItemStackForItem(itemStackToAdd.getItem()).merge(itemStackToAdd);
            return;
        }
        if (items.size() + 1 > getMaxItems()) {
            return;
        }
        items.add(itemStackToAdd);
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
     * @param itemToRemove
     * @param amount
     */
    public void removeItem(Item itemToRemove, int amount) {
        ItemStack stack = getItemStackForItem(itemToRemove);
        if (amount >= stack.getQuantity()) {
            items.remove(stack);
        }
        stack.removeQuantity(amount);
        //Fail-safe to ensure the ItemStack never has a negative size.
        if (stack.getQuantity() <= 0) {
            items.remove(stack);
        }
    }

    /**
     * Completely removes an Item from the inventory. This method should be
     * called as opposed to removeItem(item, int) when the number of items being
     * dropped is not important as long as the item is dropped.
     *
     * @param itemToDrop The Item being dropped.
     */
    public void dropItem(Item itemToDrop) {
        items.remove(getItemStackForItem(itemToDrop));
    }

    /**
     * Gets the ItemStack that contains an Item.
     *
     * @param item The Item that is supposed to be in a stack.
     *
     * @return The ItemStack that has the Item or null if one isn't found.
     */
    private ItemStack getItemStackForItem(Item item) {
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
}
