package com.devnem0y.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.devnem0y.handlers.GameLogic;
import com.devnem0y.screens.GameScreen;

import static com.devnem0y.handlers.GameLogic.score;

public class Enemy extends GameObjects{

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    //======================
    private Texture recMask;
    //======================

    private Rectangle bounds;
    private float posX, posY;
    private TextureAtlas atlasRunL, atlasRunR, atlasDeath, atlasDestruction;
    private Animation animRunL, animRunR, animDestruction;
    private boolean alive, destruction;
    private int direction;
    private int hp;
    private float speed;

    private float animTimer = 0f, animTimerDestruction, attackCountTimer = 0f;

    private Blood blood;

    public Enemy(float x, float y) {
        //======================
        recMask = new Texture("image/recEnemy.png");
        //======================
        this.posX = x;
        this.posY = y;
        create(posX, posY);
        initAnimations();
    }

    @Override
    public void update(float dt, Rectangle playerBounds) {
        if(!destruction) {
            float x = bounds.x + (bounds.getWidth() / 2);
            float y = bounds.y;
            float dx;
            if (playerBounds.getX() > bounds.x) {
                dx = playerBounds.getX();
            } else dx = playerBounds.getX() + playerBounds.getWidth();
            //float dx = playerBounds.getX() + (playerBounds.getWidth() / 2);
            float dy = playerBounds.getY();

            if (isAlive()) {
                if (x != dx) {
                    if (x > dx) bounds.x -= speed * dt;
                    else bounds.x += speed * dt;
                }
                if (y != dy) {
                    if (y > dy) bounds.y -= speed * dt;
                    else bounds.y += speed * dt;
                }
            }

            if (dx < x) direction = LEFT;
            else if (dx > x) direction = RIGHT;
        }
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        //======================
        if (GameScreen.maskShow) batch.draw(recMask, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        //======================

        if (alive && !destruction) {
            if (direction == LEFT) batch.draw((TextureRegion) animRunL.getKeyFrame(animTimer), bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
            if (direction == RIGHT) batch.draw((TextureRegion) animRunR.getKeyFrame(animTimer), bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        } else if (destruction) {
            batch.draw((TextureRegion) animDestruction.getKeyFrame(animTimer), bounds.getX(), bounds.getY(), bounds.getWidth() * 2, bounds.getHeight() * 2);
            animTimerDestruction += dt;
            if (animTimerDestruction > 0.699f) {
                setAlive(false);
                create(posX, posY);
                animTimerDestruction = 0f;
            }
        } else recreate();
        if (blood != null) batch.draw(blood.death, blood.bounds.x, blood.bounds.y, blood.bounds.width, blood.bounds.height);
        animTimer += dt;
    }

    private void create(float x, float y) {
        bounds = new Rectangle();
        bounds.width = 23;
        bounds.height = 18.917f;
        bounds.setPosition(x, y);
        speed = MathUtils.random(10.0f, 70.2f);
        hp = 100;
        alive = true;
        destruction = false;
    }

    public void recreate() {
        blood = new Blood(bounds.x, bounds.y);
        create(posX, posY);
    }

    public float attack(float dt) {
        attackCountTimer += dt;
        float attack = 0;
        if (attackCountTimer > 1f) {
            attack = MathUtils.random(2.5f, 5f);
            attackCountTimer = 0f;
        }
        return attack;
    }

    public void damage(int attackPlayer) {
        if (alive) {
            hp -= attackPlayer;
            if (hp <= 0) {
                setAlive(false);
                GameLogic.ammo.spawn(bounds.getX(), bounds.getY());
                GameLogic.health.spawn(bounds.getX(), bounds.getY());
                GameLogic.killAll.spawn(bounds.getX(), bounds.getY());
                if (GameLogic.player.getIdWeapon() != 1) {
                    GameLogic.shotgun.spawn(bounds.getX(), bounds.getY());
                }
            }
        }
    }

    private void initAnimations() {
        atlasRunL = new TextureAtlas("image/atlas/enemy/runL.atlas");
        animRunL = new Animation<TextureRegion>(1f/10f, atlasRunL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasRunR = new TextureAtlas("image/atlas/enemy/runR.atlas");
        animRunR = new Animation<TextureRegion>(1f/10f, atlasRunR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasDestruction = new TextureAtlas("image/atlas/enemy/destruction.atlas");
        animDestruction = new Animation<TextureRegion>(1f/7f, atlasDestruction.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasDeath = new TextureAtlas("image/atlas/enemy/death.atlas");
    }

    public boolean isAlive() {
        return alive;
    }

    private void setAlive(boolean alive) {
        this.alive = alive;
        score++;
    }

    public void setDestruction(boolean destruction) {
        this.destruction = destruction;
    }

    public float getAnimTimer() {
        return animTimer = 0f;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        if (atlasRunL != null) atlasRunL.dispose();
        if (atlasRunR != null) atlasRunR.dispose();
        if (atlasDestruction != null) atlasDestruction.dispose();
        if (atlasDeath != null) atlasDeath.dispose();
    }

    private class Blood {

        Rectangle bounds;
        Sprite death;

        Blood(float x, float y) {
            bounds = new Rectangle();
            if (direction == LEFT) death = new Sprite(atlasDeath.findRegion("frame", 0));
            if (direction == RIGHT) death = new Sprite(atlasDeath.findRegion("frame", 0));
            bounds.width = 23;
            bounds.height = 18.917f;
            bounds.setPosition(x, y);
        }
    }
}
