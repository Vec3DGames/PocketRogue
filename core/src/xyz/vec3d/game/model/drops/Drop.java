package xyz.vec3d.game.model.drops;

/**
 * Created by Daron on 7/9/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class Drop {

    private int itemId;

    private int amount;

    private int dropRate;

    public Drop(int itemId, int amount, int dropRate) {
        this.itemId = itemId;
        this.amount = amount;
        this.dropRate = dropRate;
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getDropRate() {
        return dropRate;
    }

    @Override
    public String toString() {
        return "ID: " + itemId + " Amount: " + amount + " Drop Rate: " + dropRate;
    }
}
