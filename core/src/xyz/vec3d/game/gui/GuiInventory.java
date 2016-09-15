package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

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
    private ArrayList<ItemStackDisplay> itemStackDisplays = new ArrayList<>();

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
        attr1.setName("Attribute 1: ");
        attr2 = new Label("Attribute 2: ", skin);
        attr2.setName("Attribute 2: ");
        attr3 = new Label("Attribute 3: ", skin);
        attr3.setName("Attribute 3: ");
        attr4 = new Label("Attribute 4: ", skin);
        attr4.setName("Attribute 4: ");

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
        for (int i = 0; i < items.length; i++) {
            ItemStackDisplay display;
            if (itemStackDisplays.size() > i) {
                display = itemStackDisplays.get(i);
            } else {
                display = new ItemStackDisplay(items[i]);
                display.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //Update bonuses.
                        ItemStackDisplay displayFired = (ItemStackDisplay) event.getListenerActor();
                        int[] bonuses = displayFired.getItemStack().getItem().getBonuses();
                        attr1.setText(attr1.getName() + bonuses[0]);
                        attr2.setText(attr2.getName() + bonuses[1]);
                        attr3.setText(attr3.getName() + bonuses[2]);
                        attr4.setText(attr4.getName() + bonuses[3]);
                    }
                });
                itemStackDisplays.add(display);
            }
            itemTable.add(display).expandX().fillX().left().width(itemTable.getWidth()).pad(4).row();
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
