package xyz.vec3d.game.gui.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    private Console console;

    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<LogMessage> executedCommands = new ArrayList<LogMessage>();

    public ConsoleDisplay(String title, Skin skin, Console console) {
        //super(title, skin);
        //setSize(Settings.UI_WIDTH, 150);
        //setMovable(false);
        this.console = console;

        commandScrollTable = new Table(skin);
        commandScroll = new ScrollPane(commandScrollTable, skin);
        commandInput = new TextField("", skin);
        commandInput.setDisabled(true);
        commandInput.setTextFieldListener(new ConsoleListener());
        this.add(commandScroll).expand().fill().pad(4).row();
        this.add(commandInput).expandX().fillX().pad(4);
        this.addListener(new KeyListener());
    }

    public void toggle() {
        commandInput.setDisabled(!commandInput.isDisabled());
    }

    public void log(String message) {
        log(message, LogMessage.LogLevel.NORMAL);
    }

    public void log(String message, LogMessage.LogLevel level) {
        LogMessage logMessage = new LogMessage(message, level);
        executedCommands.add(logMessage);
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

    /**
     * Refreshes the table embedded in the scroll pane. This gets called whenever
     * a new {@link LogMessage message} is added to the display.
     */
    private void refresh() {
        commandScrollTable.clear();
        commandScrollTable.add().expand().fill().row();
        int size = executedCommands.size();
        for (int i = 0; i < size; i++) {
            LogMessage lm = executedCommands.get(i);
            Label l;
            // recycle the labels so we don't create new ones every refresh
            if (labels.size() > i) {
                l = labels.get(i);
            } else {
                l = new Label("", commandScrollTable.getSkin());
                l.setWrap(true);
                labels.add(l);
            }
            l.setText(lm.getMessage());
            l.setColor(lm.getColor());
            commandScrollTable.add(l).expandX().fillX().top().left().padLeft(4).row();
        }
        commandScroll.validate();
        commandScroll.setScrollPercentY(1);
    }

    public TextField getInput() {
        return commandInput;
    }

    private class ConsoleListener implements TextField.TextFieldListener {

        @Override
        public void keyTyped(TextField textField, char c) {
            if (("" + c).equalsIgnoreCase(Input.Keys.toString(Input.Keys.GRAVE))) {
                String s = textField.getText();
                textField.setText(s.substring(0, s.length() - 1));
                //console.toggle();
            }
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
                log(commandInput.getText());
                notifyMessageReceivers(commandMessage);

                //Reset text box.
                commandInput.setText("");
            }
        }
    }

    private class KeyListener extends InputListener {

        @Override
        public boolean keyDown(InputEvent event, int keyCode) {
            if (!console.isDisabled()) {
                return false;
            }
            if (keyCode == Input.Keys.GRAVE) {
                console.toggle();
                return true;
            }
            return false;
        }
    }

}
