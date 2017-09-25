package xyz.vec3d.game.model.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 7/9/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class DropSystem {

    private static Map<Integer, DropTable> dropTableMap = new HashMap<>();

    public static void loadDrops() {
        JsonReader jsonReader = new JsonReader();
        JsonValue values = jsonReader.parse(Gdx.files.internal("managed_assets/drop_definitions.json"));
        for (JsonValue table : values) {
            int id = table.getInt("Id");
            int leastCommonMultiple = table.getInt("LeastCommonMultiple");
            JsonValue drops = table.get("Drops");

            DropTable dropTable = new DropTable();
            dropTable.setLeastCommonMultiple(leastCommonMultiple);

            for (JsonValue dropJson : drops) {
                JsonValue itemId = dropJson.get("Id");
                JsonValue amount = dropJson.get("Amount");
                JsonValue dropRate = dropJson.get("DropRate");

                Drop drop = new Drop(itemId.asInt(), amount.asInt(), dropRate.asInt());
                if (drop.getDropRate() == -1) {
                    dropTable.addAlwaysDrop(drop);
                } else {
                    dropTable.addDrop(drop);
                }
            }

            dropTableMap.put(id, dropTable);
        }
    }

    public static List<Drop> getDropsForNpc(int npcId) {
        Logger.log(DropSystem.class, "Generating drops for NPC: " + npcId);

        DropTable table = dropTableMap.get(npcId);
        List<Drop> drops = new ArrayList<>();
        drops.addAll(table.getGuaranteedDrops());

        Logger.log(DropSystem.class, "Dropping " + drops.size() + " guaranteed drops.");

        int leastCommonMultiple = table.getLeastCommonMultiple();
        int roll = Utils.generateRandomNumber(leastCommonMultiple);

        Drop drop = rollForDrop(roll, leastCommonMultiple, table.getDrops());

        if (drop != null) {
            drops.add(drop);
        }

        for (Drop drop1 : drops) {
            Logger.log(DropSystem.class, "NPC dropped Item ID: " + drop1.getItemId());
        }

        return drops;
    }

    private static Drop rollForDrop(int roll, int leastCommonMultiple, List<Drop> possibleDrops) {
        int startOfRange = 0;
        for (Drop drop : possibleDrops) {
            int proportionalDropRate = leastCommonMultiple / drop.getDropRate();
            if (roll >= startOfRange && roll < startOfRange + proportionalDropRate) {
                return drop;
            } else {
                startOfRange += proportionalDropRate;
            }
        }

        return null;
    }

}
