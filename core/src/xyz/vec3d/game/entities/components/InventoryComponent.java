package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;

import xyz.vec3d.game.model.Inventory;

/**
 * Created by darakelian on 7/24/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */
public class InventoryComponent implements Component {

    /**
     * The {@link Inventory} object backing this component.
     */
    private Inventory inventory;

    /**
     * Creates a new {@link InventoryComponent} with an empty {@link Inventory}.
     */
    public InventoryComponent() {
        inventory = new Inventory();
    }

    /**
     * Retrieves the {@link Inventory} that this component represents.
     *
     * @return The component's {@link Inventory}.
     */
    public Inventory getInventory() {
        return inventory;
    }

}
