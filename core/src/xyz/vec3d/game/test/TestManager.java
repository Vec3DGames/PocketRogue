package xyz.vec3d.game.test;

import xyz.vec3d.game.utils.Logger;

/**
 * Created by Daron on 7/10/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class TestManager {

    public void executeTest(String testName) {
        try {
            switch (testName) {
                case "drops":
                    new DropSystemTest().getDropsForNpc();
                    break;
            }
        } catch (Exception e) {
            Logger.log(e.getMessage());
        }
    }
}
