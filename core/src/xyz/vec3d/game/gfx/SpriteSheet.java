package xyz.vec3d.game.gfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.Locale;

import xyz.vec3d.game.PocketRogue;
import xyz.vec3d.game.utils.Logger;

/**
 * Created by Daron on 8/24/2016.
 *
 * Represents a sprite sheet.
 */
public class SpriteSheet {

    private Texture internalTexture;

    public static final int ICON_SIZE = 16;

    public SpriteSheet(String name) {
        internalTexture = PocketRogue.getAsset(name);
    }

    public TextureRegion getTextureFromSheet(int x, int y) {
        return getTextureFromSheet(x - 1, y - 1, ICON_SIZE, ICON_SIZE);
    }

    public TextureRegion getTextureFromSheet(int x, int y, int width, int height) {
        Logger.log(String.format(Locale.US, "Getting icon from coordinates %d,%d",
                x*ICON_SIZE, y*ICON_SIZE), SpriteSheet.class);
        return new TextureRegion(internalTexture, x * ICON_SIZE,
                y * ICON_SIZE, width, height);
    }
}
