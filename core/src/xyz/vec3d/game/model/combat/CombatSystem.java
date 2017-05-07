package xyz.vec3d.game.model.combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.model.Item;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 3/17/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 *
 * This class handles all player combat interaction.
 */

public class CombatSystem implements IMessageReceiver {

    private Engine engine;

    /**
     * The Player object.
     */
    private Player player;

    private float timeSinceLastAttack;
    private float timeTillNextAttack;

    /**
     * Create a new combat system operating on the provided engine and based on
     * the provided player.
     *
     * @param engine The engine being operated on.
     * @param player The reference player object.
     */
    public CombatSystem(Engine engine, Player player) {
        this.player = player;
        this.engine = engine;
        this.timeSinceLastAttack = this.timeTillNextAttack = 2f;
    }

    public void update(float delta) {
        timeSinceLastAttack += delta;
    }

    /**
     * Attempts to perform a melee attack for the player.
     */
    public void doPlayerAttack() {
        //Check that player can perform an attack.
        if (timeSinceLastAttack < timeTillNextAttack) {
            return;
        }
        //First, get all entities in a 2 tile radius.
        ArrayList<PocketRogueEntity> entitiesInRange = Utils.getEntitiesWithinRange(
                engine.getEntities(), player, 2f);
        //Next, check each entity and see if they meet the criteria for being hit.
        Vector2 playerDirection = player.getDirection();
        //Do some maths to get the corner being used for sweep.
        Vector2 attackSweepPosition = player.getPosition().cpy().add(0.5f, 0.5f);
        attackSweepPosition.add(playerDirection.cpy().scl(0.5f));
        for (PocketRogueEntity otherEntity : entitiesInRange) {
            Vector2 enemyPos = otherEntity.getPosition().cpy().add(0.5f, 0.5f);
            //Computer distance between sweep position and enemy
            float distance = attackSweepPosition.dst(enemyPos);
            float angle = enemyPos.cpy().sub(attackSweepPosition).angle();
            if (distance <= 1) {
                Logger.log("Sweep pos: " + attackSweepPosition + ", Enemy pos: " + enemyPos);
                Logger.log("Hit entity from distance: " + distance + " at angle: " + angle);
                otherEntity.doHit(player, calculatePlayerDamage(otherEntity));
            }
        }
        //Reset attack timer.
        timeSinceLastAttack = 0;
    }

    private float calculatePlayerDamage(PocketRogueEntity entityBeingHit) {
        return player.getInventory().getEquipmentManager().getTotalDamageBonuses();
    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case ITEM_EQUIPPED:
                Item itemEquipped = player.getInventory().getEquipmentManager()
                        .getItem(Item.ItemType.PRIMARY_HAND);
                if (itemEquipped != null) {
                    this.timeTillNextAttack = itemEquipped.getBonus(Item.ATTACK_SPEED);
                }
                break;
        }
    }
}
