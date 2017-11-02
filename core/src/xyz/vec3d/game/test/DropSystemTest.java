package xyz.vec3d.game.test;

import java.util.List;

import xyz.vec3d.game.model.drops.Drop;
import xyz.vec3d.game.model.drops.DropSystem;
import xyz.vec3d.game.utils.Logger;

/**
 * Created by Daron on 7/10/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class DropSystemTest {

    public void getDropsForNpc() throws Exception {
        DropSystem.loadDrops();

        int NUM_DROPS_TO_SIMULATE = 1000;

        int[] npcIdsToGenerateDrops = new int[]{1};

        for (int i = 0; i < NUM_DROPS_TO_SIMULATE; i++) {
            for (int npcId : npcIdsToGenerateDrops) {
                Logger.log("Drop #" + i + " for NPC " + npcId + " yielded:");
                List<Drop> drops = DropSystem.getDropsForNpc(npcId);
                printDrops(drops);
                Logger.log("---------------------");
            }
        }
    }

    private void printDrops(List<Drop> drops) {
        drops.forEach(System.out::println);
    }
}
