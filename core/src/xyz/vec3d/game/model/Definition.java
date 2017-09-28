package xyz.vec3d.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daron on 9/28/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 */

public class Definition {

    private Map<DefinitionProperty, Object> definitions;

    public Definition() {
        definitions = new HashMap<>();
    }

    public Object getProperty(DefinitionProperty property) {
        return definitions.get(property);
    }

    public void putProperty(String propertyName, Object property) {
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
