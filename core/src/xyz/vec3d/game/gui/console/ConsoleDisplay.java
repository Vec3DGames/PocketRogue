package xyz.vec3d.game.gui.console;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;

import xyz.vec3d.game.Settings;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.messages.MessageReceiver;
import xyz.vec3d.game.messages.MessageSender;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 8/14/2016.
 *
 * The actual components that make up the console. Sends messages to the
 * game screen indicating commands.
 */
public class ConsoleDisplay extends Table implements MessageSender {

    private ArrayList<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();
    private ScrollPane commandScroll;
    private TextField commandInput;
    private Table commandScrollTable;

    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<String> executedCommands = new ArrayList<String>();

    public ConsoleDisplay(String title, Skin skin) {
        //super(title, skin);
        //setSize(Settings.UI_WIDTH, 150);
        //setMovable(false);

        commandScrollTable = new Table(skin);
        commandScroll = new ScrollPane(commandScrollTable, skin);
        commandInput = new TextField("", skin);
        commandInput.setDisabled(true);
        commandInput.setTextFieldListener(new ConsoleListener());
        this.add(commandScroll).expand().fill().pad(4).row();
        this.add(commandInput).expandX().fillX().pad(4);
    }

    public void toggle() {
        commandInput.setDisabled(!commandInput.isDisabled());
    }

    @Override
    public void registerMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(MessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (MessageReceiver messageReceiver : messageReceivers) {
            messageReceiver.onMessageReceived(message);
        }
        refresh();
    }

    private void refresh() {
        ArrayList<String> entries = executedCommands;
        commandScrollTable.clear();
        commandScrollTable.add().expand().fill().row();
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            String le = entries.get(i);
            Label l;
            // recycle the labels so we don't create new ones every refresh
            if (labels.size() > i) {
                l = labels.get(i);
            } else {
                l = new Label("", commandScrollTable.getSkin());
                l.setWrap(true);
                labels.add(l);
            }
            l.setText(le);
            commandScrollTable.add(l).expandX().fillX().top().left().padLeft(4).row();
        }
        commandScroll.validate();
        commandScroll.setScrollPercentY(1);
    }

    private class ConsoleListener implements TextField.TextFieldListener {

        @Override
        public void keyTyped(TextField textField, char c) {
            if (c == '\r' || c == '\n') {
                if (commandInput.getText().equals("")) {
                    return;
                }
                //Fire command.
                String[] tokens = commandInput.getText().split(" ");
                if (tokens.length == 0) {
                    return;
                }
                Message commandMessage = new Message(Message.MessageType.COMMAND, tokens);
                executedCommands.add(commandInput.getText());
                notifyMessageReceivers(commandMessage);

                //Reset text box.
                commandInput.setText("");
            }
        }
    }
}
