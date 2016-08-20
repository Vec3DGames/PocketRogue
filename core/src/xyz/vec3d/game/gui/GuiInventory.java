package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import xyz.vec3d.game.model.Inventory;

/**
 * Created by Daron on 8/16/2016.
 *
 * GUI overlay for the player {@link Inventory};
 */
public class GuiInventory extends Gui {

    private Inventory inventory;

    public GuiInventory() {
        super();
    }

    @Override
    public void setup() {
        this.inventory = (Inventory) getParameters()[0];
        Skin skin = (Skin) getParameters()[1];
        Window window = new Window("Inventory", skin);
        window.setSize(200, 200);
        getStage().addActor(window);
    }

}
