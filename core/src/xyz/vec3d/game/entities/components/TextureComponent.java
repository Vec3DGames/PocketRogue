package xyz.vec3d.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by darakelian on 7/7/2016.
 * Copyright vec3d.xyz 2016
 * All rights reserved
 *
 * Texture component for drawable entities. Contains information about the
 * texture being drawn.
 */
public class TextureComponent implements Component {

    /**
     * The {@link Texture} being stored in the component.
     */
    private TextureRegion texture;

    /**
     * Creates a new {@link TextureComponent} from the specified Texture.
     *
     * @param texture The Texture to store in the component.
     */
    public TextureComponent(TextureRegion texture) {
        this.texture = texture;
    }

    /**
     * Returns the {@link Texture} backing this component.
     *
     * @return The Texture to be drawn.
     */
    public TextureRegion getTexture() {
        return texture;
    }
}
