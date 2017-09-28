package xyz.vec3d.game.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/19/2016.
 *
 * Loads item definitions (name and texture information) as well as the enemy
 * definitions.
 */
public class DefinitionLoader {

    private static Map<Integer, Definition> itemDefinitions;
    private static Map<Integer, Definition> entityDefinitions;

    public static int NUMBER_OF_ENTITIES;

    public DefinitionLoader() {
        itemDefinitions = new HashMap<>();
        entityDefinitions = new HashMap<>();
    }

    /**
     * Loads up ItemDefinitions and EntityDefinition
     */
    public void loadDefinitions() {
        //Load item definitions
        List<Definition> loadedItemDefinitions = Utils.getDefinitionsFromFile("managed_assets/item_definitions.json");
        for (Definition definition : loadedItemDefinitions) {
            itemDefinitions.put((int)definition.getProperty(DefinitionProperty.ID), definition);
        }
        //Load entity definitions
        List<Definition> loadedEntityDefinitions = Utils.getDefinitionsFromFile("managed_assets/entity_definitions.json");
        for (Definition definition : loadedEntityDefinitions) {
            entityDefinitions.put((int)definition.getProperty(DefinitionProperty.ID), definition);
        }
        NUMBER_OF_ENTITIES = entityDefinitions.size();
    }

    /**
     * Returns the definition associated with a specific item ID.
     *
     * @param itemId The ID of the item to get definitions for.
     *
     * @return A Definition object attached to the item.
     */
    public static Definition getItemDefinition(int itemId) {
        if (!itemDefinitions.containsKey(itemId)) {
            return itemDefinitions.get(0);
        }
        return itemDefinitions.get(itemId);
    }

    public static Definition getEntityDefinition(int entityId) {
        if (!entityDefinitions.containsKey(entityId)) {
            return entityDefinitions.get(0);
        }
        return entityDefinitions.get(entityId);
    }

}
