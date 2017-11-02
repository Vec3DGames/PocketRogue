package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import xyz.vec3d.game.GameScreen;
import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.model.Inventory;
import xyz.vec3d.game.model.Item;
import xyz.vec3d.game.model.ItemStack;
import xyz.vec3d.game.model.combat.CombatSystem;
import xyz.vec3d.game.utils.Logger;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/16/2016.
 *
 * GUI overlay for the player {@link Inventory};
 */
class GuiInventory extends Gui {

    private Inventory inventory;
    private ScrollPane itemScrollPane;
    private Table itemTable;
    private List<ItemStackDisplay> itemStackDisplays = new ArrayList<>();
    private List<IMessageReceiver> messageReceivers = new ArrayList<>();

    private Label meleeDamage, magicDamage, rangeDamage, attackSpeed, meleeDef,
            magicDef, rangeDef;

    private Actor parentActor;

    public GuiInventory() {
        super();
    }

    @Override
    public void setup() {
        this.inventory = (Inventory) getParameters()[0];
        Skin skin = (Skin) getParameters()[1];
        CombatSystem combatSystem = (CombatSystem) getParameters()[2];
        this.messageReceivers.add(combatSystem);
        if (getParameters().length == 4) {
            parentActor = (Actor) getParameters()[3];
        }

        //Create and set up the window.
        Window window = new Window("Inventory", skin);
        window.setSize(400, 300);
        window.setResizable(false);
        window.setMovable(false);
        Utils.centerActor(window, getStage());

        //Set up root table.
        Table componentTable = new Table(skin);
        componentTable.pad(4);
        componentTable.padTop(22);
        componentTable.setFillParent(true);

        //Set up table for the ItemStackDisplays and the scroll pane.
        itemTable = new Table(skin);
        itemScrollPane = new ScrollPane(itemTable, skin);

        //Set up table for item stats.
        Table itemInfoTable = new Table(skin);
        meleeDamage = new Label("Melee Damage: ", skin);
        magicDamage = new Label("Magic Damage: ", skin);
        rangeDamage = new Label("Range Damage: ", skin);
        attackSpeed = new Label("Attack Speed: ", skin);
        meleeDef = new Label("Melee Defense: ", skin);
        magicDef = new Label("Magic Defense: ", skin);
        rangeDef = new Label("Range Defense: ", skin);
        Button equipButton = new TextButton("Equip", skin);
        Button dropButton = new TextButton("Drop", skin);
        equipButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                equipButtonClicked(getSelectedDisplay());
            }
        });
        dropButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ItemStackDisplay itemStackDisplay = getSelectedDisplay();
                if (itemStackDisplay == null) {
                    Logger.log(getClass(), Logger.LogLevel.WARNING, "Attempted to select a non-existent item display.");
                    return;
                }

                ItemStack itemToDrop = inventory.dropItem(itemStackDisplay.getItemStack().getItem());
                ((GameScreen) parentScreen).dropItem(itemToDrop);
                refreshInventoryTable();
            }
        });

        itemInfoTable.add(meleeDamage).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(magicDamage).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(rangeDamage).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(attackSpeed).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(meleeDef).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(magicDef).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(rangeDef).pad(4).fillX().expandX().height(20).row();
        itemInfoTable.add(equipButton).pad(4).fillX().expandX().height(30).row();
        itemInfoTable.add(dropButton).pad(4).fillX().expandX().height(30);
        itemInfoTable.add().fill().expand();

        //Add item display and stat display tables to root table.
        componentTable.add(itemScrollPane).expandY().fillY().width(250);
        componentTable.add(itemInfoTable).expandY().fillY().width(150);

        //Add root table to window and add the item displays.
        window.addActor(componentTable);
        refreshInventoryTable();

        //Add stuff to the stage and set scroll focus to item scroll pane.
        getStage().addActor(window);
        getStage().setScrollFocus(itemScrollPane);

        this.setHitbox(window.getX(), window.getY(), window.getWidth(), window.getHeight());
    }

    private void equipButtonClicked(ItemStackDisplay itemStackDisplay) {
        if (itemStackDisplay == null) {
            Logger.log(getClass(), Logger.LogLevel.WARNING, "Attempted to select a non-existent item display.");
            return;
        }

        ItemStack itemToEquip = itemStackDisplay.getItemStack();
        if (inventory.equipItem(itemToEquip)) {
            Message itemEquippedMessage = new Message(Message.MessageType.ITEM_EQUIPPED);
            notifyMessageReceivers(itemEquippedMessage);
            refreshInventoryTable();
            return;
        }
        String parentName = parentActor != null ? parentActor.getName() : "";
        if (parentName.contains("hot_bar")) {
            if (itemToEquip.getItem().getType() == Item.ItemType.GENERAL) {
                int hotBarSlot = Integer.parseInt(parentName.substring(parentName.length() - 1));
                inventory.setHotBarItem(hotBarSlot, itemToEquip);
                notifyMessageReceivers(new Message(Message.MessageType.PLAYER_INVENTORY_CHANGED));
                dispose();
                return;
            }
        }

        ((GameScreen) parentScreen).useHotBarItem(itemToEquip);
        refreshInventoryTable();
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
                        itemStackDisplayClicked(event, x, y);
                    }
                });
                itemStackDisplays.add(display);
            }
            if (display.getItemStack().getQuantity() <= 0) {
                itemStackDisplays.remove(i);
                continue;
            }
            itemTable.add(display).expandX().fillX().left().pad(4).row();
        }
        itemTable.add().expand().fill();
        itemScrollPane.validate();
    }

    private void itemStackDisplayClicked(InputEvent event, float x, float y) {
        Logger.log(getClass(), "Item Display clicked at: (" + x + "," + y + ").");

        ItemStackDisplay displayFired = (ItemStackDisplay) event.getListenerActor();
        int[] bonuses = displayFired.getItemStack().getItem().getBonuses();
        meleeDamage.setText(Utils.modifyDisplayValue(meleeDamage, bonuses[Item.ATTACK]));
        magicDamage.setText(Utils.modifyDisplayValue(magicDamage, bonuses[Item.MAGIC]));
        rangeDamage.setText(Utils.modifyDisplayValue(rangeDamage, bonuses[Item.RANGE]));
        attackSpeed.setText(Utils.modifyDisplayValue(attackSpeed, bonuses[Item.ATTACK_SPEED]));
        meleeDef.setText(Utils.modifyDisplayValue(meleeDef, bonuses[Item.MELEE_DEFENSE]));
        magicDef.setText(Utils.modifyDisplayValue(magicDef, bonuses[Item.MAGIC_DEFENSE]));
        rangeDef.setText(Utils.modifyDisplayValue(rangeDef, bonuses[Item.RANGE_DEFENSE]));
        itemStackDisplays.forEach(ItemStackDisplay::deselect);
        displayFired.select();
    }

    private ItemStackDisplay getSelectedDisplay() {
        for (ItemStackDisplay display : itemStackDisplays) {
            if (display.isSelected()) {
                return display;
            }
        }
        return null;
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

    @Override
    public void notifyMessageReceivers(Message message) {
        for (IMessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
    }

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        this.messageReceivers.add(messageReceiver);
    }
}
