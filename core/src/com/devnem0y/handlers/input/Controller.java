package com.devnem0y.handlers.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.devnem0y.screens.GameScreen;
import com.devnem0y.utils.Constants;

public class Controller {

    public Button btnBit, btnShotgun;
    private Button.ButtonStyle btnBitStyle, btnShotgunStyle;
    public Group joyGroup;
    private Skin skin, touchpadSkin;
    private Touchpad touchpad;

    public Controller() {
        initJoy();
    }

    private void initJoy() {
        skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("image/btnAttack.atlas")));
    }

    private void createStick(Stage stage) {
        touchpadSkin = new Skin();
        touchpadSkin.addRegions(new TextureAtlas(Gdx.files.internal("image/joystick.atlas")));

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15, 15, 150, 150);

        stage.addActor(touchpad);
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void createJoy(Stage stage) {
        joyGroup = new Group();
        joyGroup.setPosition(0, -270);

        btnBitStyle = new Button.ButtonStyle();
        btnBitStyle.down = skin.getDrawable("bitDown");
        btnBitStyle.up = skin.getDrawable("bitUp");

        btnBit = new Button(btnBitStyle);
        btnBit.setSize(100, 100);
        btnBit.setPosition(Constants.APP_WIDTH - 150, 50);
        btnBit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                GameScreen.idButton = 2;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.idButton = 0;
                Gdx.app.log("my app", "Released");
            }
        });

        btnShotgunStyle = new Button.ButtonStyle();
        btnShotgunStyle.down = skin.getDrawable("shotgunDown");
        btnShotgunStyle.up = skin.getDrawable("shotgunUp");

        btnShotgun = new Button(btnShotgunStyle);
        btnShotgun.setSize(100, 100);
        btnShotgun.setPosition(Constants.APP_WIDTH + 450, 50);
        btnShotgun.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                GameScreen.idButton = 2;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.idButton = 0;
                Gdx.app.log("my app", "Released");
            }
        });

        createStick(stage);

        joyGroup.addActor(touchpad);
        joyGroup.addActor(btnBit);
        joyGroup.addActor(btnShotgun);
        stage.addActor(joyGroup);
    }
}
