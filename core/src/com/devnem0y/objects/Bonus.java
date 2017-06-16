package com.devnem0y.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.devnem0y.screens.GameScreen;

public class Bonus extends GameObjects{

    //======================
    private Texture recMask;
    //======================

    private Rectangle bounds;
    private TextureAtlas atlas;
    private Animation anim;
    private float animTimer = 0f;
    private boolean spawn;

    private String bonusAtlas;
    private int randomBonus;
    private float frameDuration;
    private int texW, texH;

    private float timerDestroy;

    public Bonus(String bonusAtlas, int texW, int texH, int randomBonus, float frameDuration) {
        //======================
        recMask = new Texture("image/recBonus.png");
        //======================

        this.bonusAtlas = bonusAtlas;
        this. randomBonus = randomBonus;
        this.frameDuration = frameDuration;
        this.texW = texW;
        this.texH = texH;
        spawn = false;
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        if (spawn && atlas != null) {
            //======================
            if (GameScreen.maskShow) batch.draw(recMask, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            //======================
            batch.draw((TextureRegion) anim.getKeyFrame(animTimer), bounds.x - 10, bounds.y - 10, texW, texH);
            animTimer += dt;
        } else animTimer = 0f;
    }

    void spawn(float x, float y) {
        int rnd = MathUtils.random(randomBonus);
        if (rnd == randomBonus) {
            bounds = new Rectangle();
            atlas = new TextureAtlas(bonusAtlas);
            anim = new Animation<TextureRegion>(frameDuration, atlas.findRegions("frame"), Animation.PlayMode.LOOP);
            bounds.width = 20;
            bounds.height = 20;
            bounds.setPosition(x + 25, y - 45);
            spawn = true;
        }
    }

    @Override
    public void update(float dt) {
        timerDestroy += dt;
        if (spawn && timerDestroy > 10f) {
            destroy();
            timerDestroy = 0f;
        }
    }

    public void destroy() {
        spawn = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isSpawn() {
        return spawn;
    }

    @Override
    public void dispose () {
        if (atlas != null) atlas.dispose();
    }
}
