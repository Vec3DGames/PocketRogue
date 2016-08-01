package xyz.vec3d.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Created by Bobby on 8/1/2016.
 * Creation of ON-SCREEN Button
 */
public class OSButton {
    private BitmapFont font; //** same as that used in Tut 7 **//
    private TextureAtlas buttonsAtlas; //** image of buttons **//
    private Skin buttonSkin; //** images are used as skins of the button **//
    private TextButton button;
    public OSButton(String name){
        buttonsAtlas = new TextureAtlas("buttons.pack"); //** button atlas image **//
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        font = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),false); //** font **//
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("ButtonOff");
        style.down = buttonSkin.getDrawable("ButtonOn");
        style.font = font;

        button = new TextButton(name, style); //** Button text and style **//
        button.setPosition(100, 100); //** Button location **//
        button.setHeight(300); //** Button Height **//
        button.setWidth(600); //** Button Width **//
    }
    public void registerWith(ChangeListener cl){
        button.addListener(cl);
    }
    public TextButton getButton(){
        return button;
    }
}
