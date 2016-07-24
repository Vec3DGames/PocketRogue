package xyz.vec3d.game.model;

/**
 * Created by darakelian on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Represents a slot in the {@link Inventory}. An ItemStack can have any number
 * of items ranging from 1-Integer.MAXIMUM.
 */
public class ItemStack {

    /**
     * The {@link Item} being stored in this item stack.
     */
    private Item item;

    /**
     * The number of items in the stack.
     */
    private int quantity;

    /**
     * Creates a new ItemStack being passed an Item and a quantity.
     *
     * @param item The Item backing this ItemStack.
     * @param quantity The quantity of the Item in the stack.
     */
    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Retrieves the Item that this ItemStack represents.
     *
     * @return The instance of the Item in the stack.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Retrieves the size of the ItemStack.
     *
     * @return The number of items in the stack.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Removes a quantity of the Item from the ItemStack.
     *
     * @param quantityToRemove The number of Items to remove.
     */
    public void removeQuantity(int quantityToRemove) {
        quantity -= quantityToRemove;
    }

    /**
     * Merges another ItemStack to the ItemStack if and only if both ItemStacks
     * have the same Item contained within. Merging two stacks constitutes adding
     * the amount of the merging ItemStack to the existing ItemStack.
     *
     * @param itemStackToMerge The ItemStack being merged into this ItemStack.
     *
     * @return The new ItemStack instance with the combined quantities.
     */
    public ItemStack merge(ItemStack itemStackToMerge) {
        if (!this.getItem().equals(itemStackToMerge.getItem())) {
            System.out.println("ItemStacks must be of same Item type to merge.");
            return this;
        }
        this.quantity += itemStackToMerge.getQuantity();
        return this;
    }

    /**
     * Checks if two ItemStacks are equal by comparing the Item in them.
     *
     * @param object The object (hopefully an ItemStack) being compared.
     *
     * @return False if object isn't an ItemStack or the Items don't match.
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof ItemStack &&
                ((ItemStack) object).getItem().equals(getItem());
    }
}
