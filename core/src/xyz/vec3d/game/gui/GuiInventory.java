package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.model.Inventory;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/16/2016.
 *
 * GUI overlay for the player {@link Inventory};
 */
public class GuiInventory extends Gui {

    private Inventory inventory;
    private ScrollPane itemScrollPane;
    private Table itemTable;
    private Table componentTable;
    private Table itemInfoTable;

    private Label attr1, attr2, attr3, attr4;

    public GuiInventory() {
        super();
    }

    @Override
    public void setup() {
        this.inventory = (Inventory) getParameters()[0];
        Skin skin = (Skin) getParameters()[1];

        //Create and set up the window.
        Window window = new Window("Inventory", skin);
        window.setSize(400, 300);
        window.setResizable(false);
        window.setMovable(false);
        Utils.centerActor(window, getStage());

        //Set up root table.
        componentTable = new Table(skin);
        componentTable.pad(4);
        componentTable.padTop(22);
        componentTable.setFillParent(true);

        //Set up table for the ItemStackDisplays and the scroll pane.
        itemTable = new Table(skin);
        itemScrollPane = new ScrollPane(itemTable, skin);

        //Set up table for item stats.
        itemInfoTable = new Table(skin);
        attr1 = new Label("Attribute 1: ", skin);
        attr2 = new Label("Attribute 2: ", skin);
        attr3 = new Label("Attribute 3: ", skin);
        attr4 = new Label("Attribute 4: ", skin);

        itemInfoTable.add(attr1).pad(4).fillX().expandX().height(40).row();
        itemInfoTable.add(attr2).pad(4).fillX().expandX().height(40).row();
        itemInfoTable.add(attr3).pad(4).fillX().expandX().height(40).row();
        itemInfoTable.add(attr4).pad(4).fillX().expandX().height(40).row();
        itemInfoTable.add().fill().expand();
        //itemInfoTable.add().expand().fill();


        //Add item display and stat display tables to root table.
        componentTable.add(itemScrollPane).expandY().fillY().width(250);
        componentTable.add(itemInfoTable).expandX().fill();

        //Add root table to window and add the item displays.
        window.addActor(componentTable);
        refreshInventoryTable();

        //Add stuff to the stage and set scroll focus to item scroll pane.
        getStage().addActor(window);
        getStage().setScrollFocus(itemScrollPane);
    }

    private void refreshInventoryTable() {
        ItemStack[] items = inventory.getItemsAsArray();
        itemTable.clear();
        for (ItemStack item : items) {
            ItemStackDisplay display = new ItemStackDisplay(item);
            display.addListener(new ClickListener() {
               @Override
                public void clicked(InputEvent event, float x, float y) {
                    ItemStackDisplay displayFired = (ItemStackDisplay) event.getListenerActor();
               }
            });
            itemTable.add(display).expandX().fillX().left().pad(4).row();
        }
        itemTable.add().expand().fill();
        itemScrollPane.validate();
    }

    @Override
    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_INVENTORY_CHANGED:
                //Refresh inventory.
                this.inventory = (Inventory) message.getPayload()[0];
                refreshInventoryTable();
                break;
        }
    }
}
