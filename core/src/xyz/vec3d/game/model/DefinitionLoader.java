package xyz.vec3d.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/19/2016.
 *
 * Loads item definitions (name and texture information) as well as the enemy
 * definitions.
 */
public class DefinitionLoader {

    public static Map<Integer, Definition> itemDefinitions;
    public static Map<Integer, Definition> entityDefinitions;

    public DefinitionLoader() {
        itemDefinitions = new HashMap<>();
        entityDefinitions = new HashMap<>();
    }

    /**
     * Loads up ItemDefinitions and EntityDefinition
     */
    public void loadDefinitions() {
        JsonReader jsonReader = new JsonReader();
        JsonValue values = jsonReader.parse(Gdx.files.internal("managed_assets/item_definitions.json"));
        for (int i = 0; i < values.size; i++) {
            JsonValue child = values.get(i);
            Definition definition = new Definition();
            for (int childIndex = 0; childIndex < child.size; childIndex++) {
                JsonValue value = child.get(childIndex);
                String key = value.name;
                definition.putProperty(key, Utils.getJsonTypeValue(value));
            }
            itemDefinitions.put((int)definition.getProperty(DefinitionProperty.ID), definition);
            System.out.println("Loaded definition: " + definition);
        }

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
    }

    public static Definition getItemDefinition(int itemId) {
        return itemDefinitions.get(itemId);
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
