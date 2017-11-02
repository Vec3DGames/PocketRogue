package xyz.vec3d.game.model.drops;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daron on 7/9/2017.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 */

public class DropTable {

    private List<Drop> guaranteedDrops;
    private List<Drop> drops;

    private int leastCommonMultiple;

    public DropTable() {
        guaranteedDrops = new ArrayList<>();
        drops = new ArrayList<>();
    }

    void addAlwaysDrop(Drop drop) {
        guaranteedDrops.add(drop);
    }

    void addDrop(Drop drop) {
        drops.add(drop);
    }

    public List<Drop> getGuaranteedDrops(){
        return guaranteedDrops;
    }

    public List<Drop> getDrops() {
        return drops;
    }

    public int getLeastCommonMultiple() {
        return leastCommonMultiple;
    }

    public void setLeastCommonMultiple(int leastCommonMultiple) {
        this.leastCommonMultiple = leastCommonMultiple;
    }
}
