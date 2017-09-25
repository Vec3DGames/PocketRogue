package xyz.vec3d.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import java.util.ArrayList;
import java.util.List;

import xyz.vec3d.game.entities.Enemy;
import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.PocketRogueEntity;
import xyz.vec3d.game.entities.components.AiComponent;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 5/13/2017.
 * Copyright vec3d.xyz 2017
 * All rights reserved.
 *
 * Handles the start and end of waves. Tracks when entities are killed and determines
 * when to star the next wave.
 */

class WaveManager implements EntityListener {

    private GameScreen gameScreen;
    private Engine engine;

    private int waveNumber;
    private int entitiesLeft;

    private List<PocketRogueEntity> waveEnemies;

    WaveManager(GameScreen gameScreen, Engine engine) {
        this.gameScreen = gameScreen;
        this.engine = engine;
        this.engine.addEntityListener(this);
        this.waveNumber = 1;
        this.entitiesLeft = this.waveNumber;
        this.waveEnemies = new ArrayList<>();
    }

    void startWave(int waveNumber) {
        this.waveNumber = waveNumber;
        this.entitiesLeft = this.waveNumber;
        startWave();
    }

    void startWave() {
        //Spawn an entity per wave number.
        for (int i = 0; i < waveNumber; i++) {
            Enemy enemyToSpawn = generateEnemy();
            engine.addEntity(enemyToSpawn);
            waveEnemies.add(enemyToSpawn);
        }
    }

    /**
     * Called when the number of entities left is 0. Increments the wave number
     * and starts a new wave.
     */
    void endWave() {
        this.waveNumber++;
        this.entitiesLeft = this.waveNumber;
        if (waveEnemies.size() > 0) {
            waveEnemies.clear();
        }
        startWave();
    }

    private Enemy generateEnemy() {
        int entityIdToSpawn = Utils.generateEntityId();
        int x = Utils.generateRandomNumber(gameScreen.getMapWidth());
        int y = Utils.generateRandomNumber(gameScreen.getMapHeight());
        Enemy enemy = new Enemy(entityIdToSpawn, x, y);
        enemy.add(new AiComponent(gameScreen.getPlayer()));
        return enemy;
    }

    @Override
    public void entityAdded(Entity entity) {

    }

    /**
     * Triggered whenever an entity is removed from the engine. We have to make
     * sure we don't handle entities that aren't the player or mobs.
     *
     * @param entity The entity being removed from the world.
     */
    @Override
    public void entityRemoved(Entity entity) {
        PocketRogueEntity pocketRogueEntity = (PocketRogueEntity) entity;
        if (!isEntityValid(pocketRogueEntity)) {
            return;
        }
        if (waveEnemies.stream().anyMatch(enemy -> enemy.equals(entity))) {
            entitiesLeft--;
            waveEnemies.remove(entity);
            if (entitiesLeft <= 0) {
                endWave();
            }
        }
    }

    private boolean isEntityValid(PocketRogueEntity pocketRogueEntity) {
        return pocketRogueEntity instanceof Enemy || pocketRogueEntity instanceof Player;
    }
}
