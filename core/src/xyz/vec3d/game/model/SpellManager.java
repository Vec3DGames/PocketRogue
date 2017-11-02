package xyz.vec3d.game.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 9/28/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 *
 * Handles unlocking spells from scrolls. Also will be responsible for handling
 * what spell gets used when the spell key is pressed.
 */

public class SpellManager {

    private Map<Integer, Spell> spells;
    private Map<Integer, Spell> unlockedSpells;

    private Spell currentSpell;

    public SpellManager() {
        spells = new HashMap<>();
        unlockedSpells = new HashMap<>();

        List<Definition> loadedSpells = Utils.getDefinitionsFromFile("managed_assets/projectile_definitions.json");
        for (Definition definition : loadedSpells) {
            spells.put((int)definition.getProperty(DefinitionProperty.ID), new Spell(definition));
        }
    }

    public void unlockSpell(int spellId) {
        unlockedSpells.put(spellId, spells.get(spellId));
    }

    public Spell getCurrentSpell() {
        return currentSpell;
    }

    public void setCurrentSpell(int id) {
        currentSpell = unlockedSpells.get(id);
    }
}
