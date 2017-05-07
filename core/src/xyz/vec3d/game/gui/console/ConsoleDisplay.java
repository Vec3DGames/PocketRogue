package xyz.vec3d.game.gui.console;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;

import xyz.vec3d.game.messages.IMessageReceiver;
import xyz.vec3d.game.messages.IMessageSender;
import xyz.vec3d.game.messages.Message;

/**
 * Created by Daron on 8/14/2016.
 *
 * The actual components that make up the console. Sends messages to the
 * game screen indicating commands.
 */
public class ConsoleDisplay extends Table implements IMessageSender {

    private ArrayList<IMessageReceiver> messageReceivers = new ArrayList<>();
    private ScrollPane commandScroll;
    private TextField commandInput;
    private Table commandScrollTable;
    private Console console;

    private ArrayList<Label> labels = new ArrayList<>();
    private ArrayList<LogMessage> executedCommands = new ArrayList<>();

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
        log(message, LogMessage.LogLevel.SUCCESS);
    }

    public void log(String message, LogMessage.LogLevel level) {
        LogMessage logMessage = new LogMessage(message, level);
        executedCommands.add(logMessage);
    }

    @Override
    public void registerMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.add(messageReceiver);
    }

    @Override
    public void deregisterMessageReceiver(IMessageReceiver messageReceiver) {
        messageReceivers.remove(messageReceiver);
    }

    @Override
    public void notifyMessageReceivers(Message message) {
        for (IMessageReceiver messageReceiver : messageReceivers) {
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
                Message commandMessage = new Message(Message.MessageType.COMMAND, (String[]) tokens);
                log(commandInput.getText(), LogMessage.LogLevel.NORMAL);
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
