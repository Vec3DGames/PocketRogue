package xyz.vec3d.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
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
        JsonReader jsonReader = new JsonReader();
        //Load item definitions
        JsonValue values = jsonReader.parse(Gdx.files.internal("managed_assets/item_definitions.json"));
        for (int i = 0; i < values.size; i++) {
            JsonValue child = values.get(i);
            Definition definition = new Definition();
            for (int childIndex = 0; childIndex < child.size; childIndex++) {
                JsonValue value = child.get(childIndex);
                String key = value.name;
                definition.putProperty(key, Utils.getJsonTypeValue(value));
            }
            definition.putProperty("id", i);
            itemDefinitions.put((int)definition.getProperty(DefinitionProperty.ID), definition);
            System.out.println("Loaded definition: " + definition);
        }
        //Load entity definitions
        values = jsonReader.parse(Gdx.files.internal("managed_assets/entity_definitions.json"));
        for (int i = 0; i < values.size; i++) {
            JsonValue child = values.get(i);
            Definition definition = new Definition();
            for (int childIndex = 0; childIndex < child.size; childIndex++) {
                JsonValue value = child.get(childIndex);
                String key = value.name;
                definition.putProperty(key, Utils.getJsonTypeValue(value));
            }
            entityDefinitions.put((int)definition.getProperty(DefinitionProperty.ID), definition);
            System.out.println("Loaded definition: " + definition);
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

    public class Definition {

        private Map<DefinitionProperty, Object> definitions;

        Definition() {
            definitions = new HashMap<>();
        }

        public Object getProperty(DefinitionProperty property) {
            return definitions.get(property);
        }

        void putProperty(String propertyName, Object property) {
            definitions.put(DefinitionProperty.value(propertyName), property);
        }

        @Override
        public String toString() {
            String toString = "\n";
            for (DefinitionProperty property : definitions.keySet()) {
                toString += ("Property: " + property.name() + ", Value: " + definitions.get(property) + "\n");
            }
            return toString;
        }

    }
}
