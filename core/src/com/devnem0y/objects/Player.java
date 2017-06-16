package com.devnem0y.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.devnem0y.handlers.GameLogic;
import com.devnem0y.screens.GameScreen;
import com.devnem0y.handlers.input.Controller;

import static com.devnem0y.utils.Constants.*;

public class Player extends GameObjects{

    private enum ANIM_STATE {
        IDLE_BIT_L,
        IDLE_SHOTGUN_L,
        RUN_BIT_L,
        RUN_SHOTGUN_L,
        ATTACK_IDLE_BIT_L,
        ATTACK_IDLE_SHOTGUN_L,
        ATTACK_RUN_BIT_L,
        ATTACK_RUN_SHOTGUN_L,
        DAMAGE_BIT_L,
        DAMAGE_SHOTGUN_L,
        DEATH_L,

        IDLE_BIT_R,
        IDLE_SHOTGUN_R,
        RUN_BIT_R,
        RUN_SHOTGUN_R,
        ATTACK_IDLE_BIT_R,
        ATTACK_IDLE_SHOTGUN_R,
        ATTACK_RUN_BIT_R,
        ATTACK_RUN_SHOTGUN_R,
        DAMAGE_BIT_R,
        DAMAGE_SHOTGUN_R,
        DEATH_R,
    }

    // characteristics MAX
    public static final float HP_MAX = 100f;
    public static final int AMMO_MAX = 30;
    private static final float SPEED = 150.7f;
    // direction
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    // id weapon
    private static final int BIT = 0;
    private static final int SHOTGUN = 1;

    //======================
    private BitmapFont fontD;

    private Texture recPlayer, recAttack;

    private boolean debugShow = false;
    //======================

    private Rectangle bounds, bitAttackBounds;
    private TextureAtlas atlasIdleBitL, atlasIdleBitR, atlasIdleShotgunL, atlasIdleShotgunR,
            atlasAttackBitStL, atlasAttackBitStR, atlasAttackBitRunL, atlasAttackBitRunR, atlasAttackShotguStL, atlasAttackShotguStR, atlasAttackShotguRunL, atlasAttackShotguRunR,
            atlasDeathL, atlasDeathR, atlasRunBitL, atlasRunBitR, atlasRunShotgunL, atlasRunShotgunR;
    private Sprite damageBitL, damageBitR, damageShotgunL, damageShotgunR, deathL, deathR;
    private Animation animIdleBitL, animIdleBitR, animIdleShotgunL, animIdleShotgunR,
            animAttackBitStL, animAttackBitStR, animAttackBitRunL, animAttackBitRunR, animAttackShotguStL, animAttackShotguStR, animAttackShotguRunL, animAttackShotguRunR,
            animRunBitL, animRunBitR, animRunShotgunL, animRunShotgunR;
    private float animTimer = 0f;

    private float hpActual;
    private int direction;
    private int idWeapon;
    private int ammoActual;
    private int fireCount;
    private boolean alive;
    private boolean attack;
    private boolean move;
    private boolean isDamage;

    private float timerBit = 0f, timerMove = 0f;

    private ANIM_STATE state;

    public Player() {
        //======================
        fontD = new BitmapFont();
        fontD.setColor(Color.GREEN);

        recPlayer = new Texture("image/recPlayer.png");
        recAttack = new Texture("image/recAttack.png");
        //======================

        bounds = new Rectangle();
        bounds.width = 30;
        bounds.height = 40;
        bounds.setPosition(APP_WIDTH / 2 - bounds.getWidth() / 2, APP_HEIGHT / 2 - bounds.getHeight() / 2);

        initAnimation();

        direction = RIGHT;
        hpActual = HP_MAX;
        idWeapon = BIT;
        ammoActual = 0;

        alive = true;
        attack = false;
        move = false;
        isDamage = false;

        state = ANIM_STATE.IDLE_BIT_R;
    }

    @Override
    public void update(float dt, Controller stick) {
        initStateAnim();
        if (alive) {
            inputControl(dt, stick);
            if (bounds.y + bounds.getHeight() >= APP_HEIGHT) bounds.y = APP_HEIGHT - bounds.getHeight();
            else if (bounds.y <= 0) bounds.y = 0;
            if (bounds.x + 40 >= APP_WIDTH) bounds.x = APP_WIDTH - 40;
            else if (bounds.x <= 10) bounds.x = 10;
        } else {
            if (direction == LEFT) state = ANIM_STATE.DEATH_L;
            else if (direction == RIGHT) state = ANIM_STATE.DEATH_R;
            attack = false;
            GameLogic.audioManager.getMusic().stop();
            GameLogic.audioManager.getGameOverM().play();
        }
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        //======================
        if (GameScreen.maskShow) batch.draw(recPlayer, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        //======================

        switch (state) {
            case IDLE_BIT_L:
                batch.draw((TextureRegion) animIdleBitL.getKeyFrame(animTimer), bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case IDLE_BIT_R:
                batch.draw((TextureRegion) animIdleBitR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case IDLE_SHOTGUN_L:
                batch.draw((TextureRegion) animIdleShotgunL.getKeyFrame(animTimer), bounds.getX() - 30, bounds.getY(), 88, 88);
                break;
            case IDLE_SHOTGUN_R:
                batch.draw((TextureRegion) animIdleShotgunR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case RUN_BIT_L:
                batch.draw((TextureRegion) animRunBitL.getKeyFrame(animTimer), bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case RUN_BIT_R:
                batch.draw((TextureRegion) animRunBitR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case RUN_SHOTGUN_L:
                batch.draw((TextureRegion) animRunShotgunL.getKeyFrame(animTimer), bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case RUN_SHOTGUN_R:
                batch.draw((TextureRegion) animRunShotgunR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(),88, 88);
                break;
            case ATTACK_IDLE_BIT_L:
                if (GameScreen.maskShow) batch.draw(recAttack, bitAttackBounds.getX(), bitAttackBounds.getY(), bitAttackBounds.getWidth(), bitAttackBounds.getHeight());
                batch.draw((TextureRegion) animAttackBitStL.getKeyFrame(animTimer), bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case ATTACK_IDLE_BIT_R:
                if (GameScreen.maskShow) batch.draw(recAttack, bitAttackBounds.getX(), bitAttackBounds.getY(), bitAttackBounds.getWidth(), bitAttackBounds.getHeight());
                batch.draw((TextureRegion) animAttackBitStR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case ATTACK_IDLE_SHOTGUN_L:
                batch.draw((TextureRegion) animAttackShotguStL.getKeyFrame(animTimer), bounds.getX() - 30, bounds.getY(), 88, 88);
                break;
            case ATTACK_IDLE_SHOTGUN_R:
                batch.draw((TextureRegion) animAttackShotguStR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case ATTACK_RUN_BIT_L:
                if (GameScreen.maskShow) batch.draw(recAttack, bitAttackBounds.getX(), bitAttackBounds.getY(), bitAttackBounds.getWidth(), bitAttackBounds.getHeight());
                batch.draw((TextureRegion) animAttackBitRunL.getKeyFrame(animTimer), bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case ATTACK_RUN_BIT_R:
                if (GameScreen.maskShow) batch.draw(recAttack, bitAttackBounds.getX(), bitAttackBounds.getY(), bitAttackBounds.getWidth(), bitAttackBounds.getHeight());
                batch.draw((TextureRegion) animAttackBitRunR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case ATTACK_RUN_SHOTGUN_L:
                batch.draw((TextureRegion) animAttackShotguRunL.getKeyFrame(animTimer), bounds.getX() - 30, bounds.getY(), 88, 88);
                break;
            case ATTACK_RUN_SHOTGUN_R:
                batch.draw((TextureRegion) animAttackShotguRunR.getKeyFrame(animTimer), bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case DAMAGE_BIT_L:
                batch.draw(damageBitL, bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case DAMAGE_BIT_R:
                batch.draw(damageBitR, bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case DAMAGE_SHOTGUN_L:
                batch.draw(damageShotgunL, bounds.getX() - 30, bounds.getY(), 88, 88);
                break;
            case DAMAGE_SHOTGUN_R:
                batch.draw(damageShotgunR, bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            case DEATH_L:
                batch.draw(deathL, bounds.getX() - 35, bounds.getY(), 88, 88);
                break;
            case DEATH_R:
                batch.draw(deathR, bounds.getX() - 25, bounds.getY(), 88, 88);
                break;
            default:
                break;
        }
        animTimer += dt;

        //======================
        if (debugShow) {
            fontD.draw(batch, "HP = " + (int)hpActual, APP_WIDTH - 200, APP_HEIGHT - 10);
            fontD.draw(batch, "isAlive = " + alive, APP_WIDTH - 200, APP_HEIGHT - 25);
            fontD.draw(batch, "isMove = " + move, APP_WIDTH - 200, APP_HEIGHT - 40);
            fontD.draw(batch, "direction = " + direction, APP_WIDTH - 200, APP_HEIGHT - 55);
            fontD.draw(batch, "getX = " + bounds.x, APP_WIDTH - 200, APP_HEIGHT - 70);
            fontD.draw(batch, "getY = " + bounds.y, APP_WIDTH - 200, APP_HEIGHT - 85);
            fontD.draw(batch, "isAttack = " + attack, APP_WIDTH - 200, APP_HEIGHT - 100);
            fontD.draw(batch, "ID_WEAPON = " + getIdWeapon(), APP_WIDTH - 200, APP_HEIGHT - 115);
            fontD.draw(batch, "-----------------------", APP_WIDTH - 200, APP_HEIGHT - 127);
            fontD.draw(batch, "ANIM_STATE\n" + state.toString(), APP_WIDTH - 200, APP_HEIGHT - 140);
        }
        //======================
    }

    private void initAnimation() {
        atlasIdleBitL = new TextureAtlas("image/atlas/player/idleBitL.atlas");
        animIdleBitL = new Animation<TextureRegion>(1f/4f, atlasIdleBitL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasIdleBitR = new TextureAtlas("image/atlas/player/idleBitR.atlas");
        animIdleBitR = new Animation<TextureRegion>(1f/4f, atlasIdleBitR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasIdleShotgunL = new TextureAtlas("image/atlas/player/idleShotgunL.atlas");
        animIdleShotgunL = new Animation<TextureRegion>(1f/4f, atlasIdleShotgunL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasIdleShotgunR = new TextureAtlas("image/atlas/player/idleShotgunR.atlas");
        animIdleShotgunR = new Animation<TextureRegion>(1f/4f, atlasIdleShotgunR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasRunBitL = new TextureAtlas("image/atlas/player/runBitL.atlas");
        animRunBitL = new Animation<TextureRegion>(1f/7f, atlasRunBitL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasRunBitR = new TextureAtlas("image/atlas/player/runBitR.atlas");
        animRunBitR = new Animation<TextureRegion>(1f/7f, atlasRunBitR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasRunShotgunL = new TextureAtlas("image/atlas/player/runShotgunL.atlas");
        animRunShotgunL = new Animation<TextureRegion>(1f/7f, atlasRunShotgunL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasRunShotgunR = new TextureAtlas("image/atlas/player/runShotgunR.atlas");
        animRunShotgunR = new Animation<TextureRegion>(1f/7f, atlasRunShotgunR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackBitStL = new TextureAtlas("image/atlas/player/attackBitStL.atlas");
        animAttackBitStL = new Animation<TextureRegion>(1f/8f, atlasAttackBitStL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackBitStR = new TextureAtlas("image/atlas/player/attackBitStR.atlas");
        animAttackBitStR = new Animation<TextureRegion>(1f/8f, atlasAttackBitStR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackBitRunL = new TextureAtlas("image/atlas/player/attackBitRunL.atlas");
        animAttackBitRunL = new Animation<TextureRegion>(1f/8f, atlasAttackBitRunL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackBitRunR = new TextureAtlas("image/atlas/player/attackBitRunR.atlas");
        animAttackBitRunR = new Animation<TextureRegion>(1f/8f, atlasAttackBitRunR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackShotguStL = new TextureAtlas("image/atlas/player/attackShotgunStL.atlas");
        animAttackShotguStL = new Animation<TextureRegion>(1f/8f, atlasAttackShotguStL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackShotguStR = new TextureAtlas("image/atlas/player/attackShotgunStR.atlas");
        animAttackShotguStR = new Animation<TextureRegion>(1f/8f, atlasAttackShotguStR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackShotguRunL = new TextureAtlas("image/atlas/player/attackShotgunRunL.atlas");
        animAttackShotguRunL = new Animation<TextureRegion>(1f/8f, atlasAttackShotguRunL.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasAttackShotguRunR = new TextureAtlas("image/atlas/player/attackShotgunRunR.atlas");
        animAttackShotguRunR = new Animation<TextureRegion>(1f/8f, atlasAttackShotguRunR.findRegions("frame"), Animation.PlayMode.LOOP);
        atlasDeathL = new TextureAtlas("image/atlas/player/deathL.atlas");
        damageBitL = new Sprite(atlasDeathL.findRegion("frame", 0));
        damageShotgunL = new Sprite(atlasDeathL.findRegion("frame", 1));
        deathL = new Sprite(atlasDeathL.findRegion("frame", 2));
        atlasDeathR = new TextureAtlas("image/atlas/player/deathR.atlas");
        damageBitR = new Sprite(atlasDeathR.findRegion("frame", 0));
        damageShotgunR = new Sprite(atlasDeathR.findRegion("frame", 1));
        deathR = new Sprite(atlasDeathR.findRegion("frame", 2));
    }

    public void initStateAnim() {
        if (isDamage && !attack && !move) {
            if (getIdWeapon() == 0 && direction == LEFT) state = ANIM_STATE.DAMAGE_BIT_L;
            else if (getIdWeapon() == 0 && direction == RIGHT) state = ANIM_STATE.DAMAGE_BIT_R;
            else if (getIdWeapon() == 1 && direction == LEFT) state = ANIM_STATE.DAMAGE_SHOTGUN_L;
            else if (getIdWeapon() == 1 && direction == RIGHT) state = ANIM_STATE.DAMAGE_SHOTGUN_R;
        } else if (!attack) {
            if (!move && direction == RIGHT && getIdWeapon() == BIT) state = ANIM_STATE.IDLE_BIT_R;
            else if (!move && direction == LEFT && getIdWeapon() == BIT) state = ANIM_STATE.IDLE_BIT_L;
            else if (!move && direction == RIGHT && getIdWeapon() == SHOTGUN) state = ANIM_STATE.IDLE_SHOTGUN_R;
            else if (!move && direction == LEFT && getIdWeapon() == SHOTGUN) state = ANIM_STATE.IDLE_SHOTGUN_L;
            else if (direction == RIGHT && getIdWeapon() == BIT) state = ANIM_STATE.RUN_BIT_R;
            else if (direction == LEFT && getIdWeapon() == BIT) state = ANIM_STATE.RUN_BIT_L;
            else if (direction == RIGHT && getIdWeapon() == SHOTGUN) state = ANIM_STATE.RUN_SHOTGUN_R;
            else if (direction == LEFT && getIdWeapon() == SHOTGUN) state = ANIM_STATE.RUN_SHOTGUN_L;
        } else {
            if (!move && direction == RIGHT && getIdWeapon() == BIT && attack) state = ANIM_STATE.ATTACK_IDLE_BIT_R;
            else if (!move && direction == LEFT && getIdWeapon() == BIT && attack) state = ANIM_STATE.ATTACK_IDLE_BIT_L;
            else if (!move && direction == RIGHT && getIdWeapon() == SHOTGUN && attack) state = ANIM_STATE.ATTACK_IDLE_SHOTGUN_R;
            else if (!move && direction == LEFT && getIdWeapon() == SHOTGUN && attack) state = ANIM_STATE.ATTACK_IDLE_SHOTGUN_L;
            else if (direction == RIGHT && getIdWeapon() == BIT && attack) state = ANIM_STATE.ATTACK_RUN_BIT_R;
            else if (direction == LEFT && getIdWeapon() == BIT && attack) state = ANIM_STATE.ATTACK_RUN_BIT_L;
            else if (direction == RIGHT && getIdWeapon() == SHOTGUN && attack) state = ANIM_STATE.ATTACK_RUN_SHOTGUN_R;
            else if (direction == LEFT && getIdWeapon() == SHOTGUN && attack) state = ANIM_STATE.ATTACK_RUN_SHOTGUN_L;
        }
    }

    private void inputControl(float dt, Controller stick) {
        if (stick.getTouchpad().isTouched()) {
            bounds.setX(bounds.getX() + stick.getTouchpad().getKnobPercentX() * (SPEED * dt));
            bounds.setY(bounds.getY() + stick.getTouchpad().getKnobPercentY() * (SPEED * dt));
            if (stick.getTouchpad().getKnobX() > 75) direction = RIGHT;
            else direction = LEFT;
            move = true;
            GameLogic.audioManager.getMoveM().play();
            GameLogic.audioManager.getMoveM().isLooping();
        } else move = false;

        //======================
        // Смена оружия
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            idWeapon = BIT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            idWeapon = SHOTGUN;
            ammoActual = 15;
        }
		
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            damage(1);
        } else isDamage = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            debugShow = !debugShow;
        }
        //======================

        // Атака
        if (GameScreen.idButton == 2 | Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            attack = true;
            if (getIdWeapon() == 0) attackBit();
            else if (getIdWeapon() == 1) attackShotgun();
        } else attack = false;
    }

    public void usingShotgun() {
        idWeapon = SHOTGUN;
        ammoActual = 15;
        GameLogic.audioManager.getMedic().play(GameLogic.audioManager.settingsSound());
    }

    private void attackBit() {
        if (idWeapon == BIT) {
            bitAttackBounds = new Rectangle();
            bitAttackBounds.width = 4;
            bitAttackBounds.height = 30;
            if (direction == LEFT) bitAttackBounds.setPosition(bounds.getX() - 2, bounds.getY());
            if (direction == RIGHT) bitAttackBounds.setPosition(bounds.getX() + 30, bounds.getY());

            timerBit += Gdx.graphics.getDeltaTime();
            if (timerBit > 0.7f) {
                GameLogic.audioManager.getBit().play(GameLogic.audioManager.settingsSound());
                timerBit = 0f;
            }

        }
    }

    private void attackShotgun() {
          if (alive && idWeapon == SHOTGUN) {
            fireCount++;
            if (fireCount > 2) {
                fireCount = 0;
                for (int i = 0; i < GameLogic.bullet.length; i++) {
                    if (!GameLogic.bullet[i].isExist()) {
                        if (ammoActual != 0) {
                            ammoActual--;
                            if (direction == LEFT) GameLogic.bullet[i].setup(bounds.x - 19, bounds.y + 25);
                            if (direction == RIGHT) GameLogic.bullet[i].setup(bounds.x + 35, bounds.y + 25);
                            GameLogic.audioManager.getShotgun().play(GameLogic.audioManager.settingsSound());
                        } else idWeapon = BIT;
                    }
                }
            }
        }
    }

    public void medic(Rectangle healthB) {
        if (hpActual < HP_MAX) {
            if (bounds.overlaps(healthB)) {
                GameLogic.health.destroy();
                hpActual = getHpActual() + 50;
                GameLogic.audioManager.getMedic().play(GameLogic.audioManager.settingsSound());
                if (hpActual > HP_MAX) hpActual = HP_MAX;
            }
        }
    }

    public void getingAmmo(Rectangle ammoB) {
        if (idWeapon == SHOTGUN) {
            if (ammoActual < AMMO_MAX) {
                if (bounds.overlaps(ammoB)) {
                    GameLogic.ammo.destroy();
                    ammoActual = getAmmoActual() + 15;
                    GameLogic.audioManager.getAmmo().play(GameLogic.audioManager.settingsSound());
                    if (ammoActual > AMMO_MAX) ammoActual = AMMO_MAX;
                }
            }
        }
    }

    public void damage(float enemyAttack) {
        if (hpActual > 0) {
            hpActual -= enemyAttack;
            isDamage = true;
        } else if (hpActual < 0) {
            hpActual = 0;
        } else {
            alive = false;
            isDamage = false;
        }
    }

    public int getIdWeapon() {
        if (idWeapon == BIT) {
            GameScreen.controller.btnBit.setPosition(APP_WIDTH - 150, 50);
            GameScreen.controller.btnShotgun.setPosition(APP_WIDTH + 250, 50);
        } else if (idWeapon == SHOTGUN) {
            GameScreen.controller.btnBit.setPosition(APP_WIDTH + 250, 50);
            GameScreen.controller.btnShotgun.setPosition(APP_WIDTH - 150, 50);
        }
        return idWeapon;
    }

    public float getHpActual() {
        return hpActual;
    }

    public void setHpActual(float hpActual) {
        this.hpActual = hpActual;
    }

    public int getAmmoActual() {
        return ammoActual;
    }

    public void setAmmoActual(int ammoActual) {
        this.ammoActual = ammoActual;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setDamage(boolean damage) {
        isDamage = damage;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getBitAttackBounds() {
        return bitAttackBounds;
    }

    @Override
    public void dispose() {
        if (atlasIdleBitL != null) atlasIdleBitL.dispose();
        if (atlasIdleBitR != null) atlasIdleBitR.dispose();
        if (atlasIdleShotgunL != null) atlasIdleShotgunL.dispose();
        if (atlasIdleShotgunR != null) atlasIdleShotgunR.dispose();
        if (atlasAttackBitStL != null) atlasAttackBitStL.dispose();
        if (atlasAttackBitStR != null) atlasAttackBitStR.dispose();
        if (atlasAttackBitRunL != null) atlasAttackBitRunL.dispose();
        if (atlasAttackBitRunR != null) atlasAttackBitRunR.dispose();
        if (atlasAttackShotguStL != null) atlasAttackShotguStL.dispose();
        if (atlasAttackShotguStR != null) atlasAttackShotguStR.dispose();
        if (atlasAttackShotguRunL != null) atlasAttackShotguRunL.dispose();
        if (atlasAttackShotguRunR != null) atlasAttackShotguRunR.dispose();
        if (atlasDeathL != null) atlasDeathL.dispose();
        if (atlasDeathR != null) atlasDeathR.dispose();
        if (atlasRunBitL != null) atlasRunBitL.dispose();
        if (atlasRunBitR != null) atlasRunBitR.dispose();
        if (atlasRunShotgunL != null) atlasRunShotgunL.dispose();
        if (atlasRunShotgunR != null) atlasRunShotgunR.dispose();
    }
}
