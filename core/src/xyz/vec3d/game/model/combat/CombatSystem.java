package xyz.vec3d.game.model.combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import xyz.vec3d.game.entities.Enemy;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.InventoryComponent;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.model.DefinitionLoader;
import xyz.vec3d.game.model.DefinitionProperty;
import xyz.vec3d.game.model.Inventory;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 3/17/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 *
 * This class handles all owner combat interaction.
 */

public class CombatSystem implements IMessageReceiver {

    private Engine engine;

    /**
     * The owner of the combat system.
     */
    private PocketRogueEntity owner;

    private float timeSinceLastAttack;
    private float timeTillNextAttack;

    /**
     * Create a new combat system operating on the provided engine and based on
     * the provided owner.
     *
     * @param engine The engine being operated on.
     * @param owner The reference owner object.
     */
    public CombatSystem(Engine engine, PocketRogueEntity owner) {
        this.owner = owner;
        this.engine = engine;
        this.timeSinceLastAttack = this.timeTillNextAttack = 2f;
    }

    public void update(float delta) {
        timeSinceLastAttack += delta;
    }

    /**
     * Attempts to perform a melee attack for the owner.
     */
    public void doPlayerAttack() {
        //Check that owner can perform an attack.
        if (timeSinceLastAttack < timeTillNextAttack) {
            return;
        }
        //First, get all entities in a 3 tile radius.
        ArrayList<PocketRogueEntity> entitiesInRange = Utils.getEntitiesWithinRange(
                engine.getEntities(), owner, 3f);
        //Next, check each entity and see if they meet the criteria for being hit.
        Vector2 playerDirection = owner.getDirection();
        //Do some maths to get the corner being used for sweep.
        Vector2 attackSweepPosition = owner.getPosition().cpy().add(0.5f, 0.5f);
        for (PocketRogueEntity otherEntity : entitiesInRange) {
            Vector2 enemyPos = otherEntity.getPosition().cpy().add(0.5f, 0.5f);
            //Computer distance between sweep position and enemy
            float distance = attackSweepPosition.dst(enemyPos);
            if (distance <= 2) {
                //Logger.log("Sweep pos: " + attackSweepPosition + ", Enemy pos: " + enemyPos);
                otherEntity.doHit(owner, calculateOwnerDamage(otherEntity));
            }
        }
        //Reset attack timer.
        timeSinceLastAttack = 0;
    }

    private float calculateOwnerDamage(PocketRogueEntity entityBeingHit) {
        if (owner instanceof Player) {
            return ((Player)owner).getInventory().getEquipmentManager().getTotalDamageBonuses();
        }
        if (owner instanceof Enemy) {
            return (float)((double)DefinitionLoader.getEntityDefinition(((Enemy) owner).getId()).getProperty(DefinitionProperty.DAMAGE));
        }
        return -1f;
    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case ITEM_EQUIPPED:
                Inventory inventory = owner.getComponent(InventoryComponent.class).getInventory();
                if (inventory != null) {
                    timeTillNextAttack = inventory.getEquipmentManager().getAttackSpeed();
                }
                break;
        }
    }
}
