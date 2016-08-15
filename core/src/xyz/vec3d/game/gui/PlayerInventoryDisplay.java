package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.model.Inventory;

/**
 * Created by Daron on 8/13/2016.
 *
 * Graphical representation of the player's {@link Inventory}. Set up with text
 * list on the left half and the icon plus stats on the right.
 */
public class PlayerInventoryDisplay extends Group {

    private Inventory inventory;

    /**
     * Creates a new {@link PlayerInventoryDisplay instance} of the display. The
     * constructor sets up the LibGDX components used as well as gets the needed
     * information from the inventory object.
     */
    public PlayerInventoryDisplay() {
        Skin skin = PocketRogue.getAssetManager().get("uiskin.json");
        ScrollPane itemScrollPane = new ScrollPane(null, skin);
    }

}
