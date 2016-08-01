package xyz.vec3d.game.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import xyz.vec3d.game.PocketRogue;

/**
 * Created by Bobby on 8/1/2016.
 * Contains creation methods for creating the On-screen touchpad.
 *
 */
public class OSTouchpad {
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    public OSTouchpad(){
        //Create a touchpad skin
        touchpadSkin = PocketRogue.getAssetManager().get("uiskin.json");
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadSkin, "default");
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
    }
    public void registerWith(ChangeListener cl){
        touchpad.addListener(cl);
    }
    public Touchpad getTouchpad(){
        return touchpad;
    }
}
