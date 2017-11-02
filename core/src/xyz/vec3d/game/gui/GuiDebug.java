package xyz.vec3d.game.gui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import xyz.vec3d.game.entities.Player;
import xyz.vec3d.game.entities.components.VelocityComponent;
import xyz.vec3d.game.messages.Message;
import xyz.vec3d.game.utils.Utils;

/**
 * Created by Daron on 2/2/2017.
 *
 * Basic GUI that will show various debug stats.
 */

public class GuiDebug extends Gui {

    private Engine engine;
    private Player player;

    //Info labels
    private Label playerPosLabel;
    private Label playerDirectionLabel;
    private Label playerDirectionAngleLabel;
    private Label playerVelocityLabel;

    public GuiDebug(Engine engine, Player player) {
        this.engine = engine;
        this.player = player;
    }

    @Override
    public void setup() {
        //Set up basics
        Skin skin = (Skin) getParameters()[0];
        Table table = new Table();
        table.setFillParent(true);

        //Set up labels
        playerPosLabel = new Label("Player Position: ", skin);
        playerDirectionLabel = new Label("Player Direction: ", skin);
        playerDirectionAngleLabel = new Label("Player Direction (Angle): ", skin);
        playerVelocityLabel = new Label("Player Velocity: ", skin);

        //Add labels
        table.add(playerPosLabel).row();
        table.add(playerDirectionLabel).row();
        table.add(playerDirectionAngleLabel).row();
        table.add(playerVelocityLabel).row();
        table.setDebug(true);
        table.bottom().left();
        getStage().addActor(table);
    }

    /**
     * Updates the debug GUI text labels as Java is dumb w/ references and stuff
     */
    @Override
    public void draw() {
        super.draw();
        Vector2 position = player.getPosition();
        Vector2 direction = player.getDirection();
        Vector2 velocity = player.getComponent(VelocityComponent.class).getVelocity();

        playerPosLabel.setText(Utils.modifyDisplayValue(playerPosLabel,
                position));
        playerDirectionLabel.setText(Utils.modifyDisplayValue(playerDirectionLabel,
                direction));
        playerDirectionAngleLabel.setText(Utils.modifyDisplayValue(playerDirectionAngleLabel,
                direction.angle() + " cos: " + Math.cos(direction.angleRad()) + " sin: "
                        + Math.sin(direction.angleRad())));
        playerVelocityLabel.setText(Utils.modifyDisplayValue(playerVelocityLabel,
                velocity));
    }

    @Override
    public void onMessageReceived(Message message) {

    }
}
