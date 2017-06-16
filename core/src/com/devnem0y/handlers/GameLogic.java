package com.devnem0y.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.devnem0y.managers.AudioManager;
import com.devnem0y.objects.Bonus;
import com.devnem0y.objects.Bullet;
import com.devnem0y.objects.Enemy;
import com.devnem0y.objects.Player;
import com.devnem0y.screens.GameScreen;

import static com.devnem0y.screens.GameScreen.gameState;
import static com.devnem0y.utils.Constants.APP_HEIGHT;
import static com.devnem0y.utils.Constants.APP_WIDTH;

public class GameLogic {

    public static Player player;
    public static Bullet[] bullet;
    private static Enemy[] enemiesLeft, enemiesRight;
    public static Bonus ammo, health, killAll, shotgun;

    private BitmapFont font;
    public static int score;
    private static int level;
    private static float gameTimer, timerRestart = 0f;

    public static AudioManager audioManager;

    private boolean createMobs = true;

    public void create() {
        font = new BitmapFont(Gdx.files.internal("fonts/BradleyHandITC26.fnt"));
        font.setColor(Color.BLACK);
        audioManager = new AudioManager();
        level = 1;
        gameTimer = 30f;
        score = 0;

        player = new Player();
        bullet = new Bullet[1];
        for (int i = 0; i < bullet.length; i++) {
            bullet[i] = new Bullet();
        }
        enemiesLeft = new Enemy[3];
        for (int j = 0; j < enemiesLeft.length; j++) {
            enemiesLeft[j] = new Enemy(-50 - MathUtils.random(60), MathUtils.random(APP_HEIGHT));
        }
        enemiesRight = new Enemy[3];
        for (int k = 0; k < enemiesRight.length; k++) {
            enemiesRight[k] = new Enemy(APP_WIDTH + MathUtils.random(60), MathUtils.random(APP_HEIGHT));
        }
        ammo = new Bonus("image/ammo.atlas", 40, 40, 20, 1f/7f);
        health = new Bonus("image/medic.atlas", 40, 40, 20, 1f/7f);
        killAll = new Bonus("image/killAll.atlas", 40, 40, 25, 1f/7f);
        shotgun = new Bonus("image/shotgun.atlas", 70, 38, 10, 1f/8f);

        gameState = GameScreen.GameState.MENU;
    }

    public void updateMenu() {
        audioManager.settingsMusic();
        audioManager.settingsSound();
    }

    public void updateStart() {
        if (!audioManager.getMusic().isPlaying()) audioManager.getMusic().play();
        audioManager.getMusic().isLooping();
    }

    public void updatePlay(float delta) {
        if (!audioManager.getMusic().isPlaying()) audioManager.getMusic().play();
        audioManager.getMusic().isLooping();
        player.update(delta, GameScreen.controller);
        if (player.isAlive()) {
            if (ammo.isSpawn()) {
                player.getingAmmo(ammo.getBounds());
            }
            if (health.isSpawn()) {
                player.medic(health.getBounds());
            }
            if (shotgun.isSpawn()) {
                if (player.getBounds().overlaps(shotgun.getBounds())) {
                    player.usingShotgun();
                    shotgun.destroy();
                }
            }
            if (killAll.isSpawn()) {
                if (player.getBounds().overlaps(killAll.getBounds())) {
                    for (Enemy sr : enemiesRight) {
                        sr.getAnimTimer();
                        sr.setDestruction(true);
                    }
                    for (Enemy sl : enemiesLeft) {
                        sl.getAnimTimer();
                        sl.setDestruction(true);
                    }
                    audioManager.getKillAll().play(audioManager.settingsSound());
                    killAll.destroy();
                }
            }
            for (Bullet b : bullet) {
                if (b.isExist()) {
                    b.update(delta, player.getBounds());
                    for (Enemy sl : enemiesLeft) {
                        if (b.getBounds().overlaps(sl.getBounds())) {
                            sl.damage(100);
                            b.destroy();
                            break;
                        }
                    }
                    for (Enemy sr : enemiesRight) {
                        if (b.getBounds().overlaps(sr.getBounds())) {
                            sr.damage(100);
                            b.destroy();
                            break;
                        }
                    }
                }
            }
        } else {

            if (timerRestart > 2.9f) {
                GameScreen.gameState = GameScreen.GameState.PAUSE;
                gameTimer = 0;
                timerRestart = 0;
            }
            timerRestart += delta;
        }

        if (createMobs) {
            for (Enemy sl : enemiesLeft) {
                sl.update(delta, player.getBounds());
                if (sl.getBounds().overlaps(player.getBounds())) {
                    player.damage(sl.attack(delta));
                }
                if (player.getBitAttackBounds() != null) {
                    if (player.getBitAttackBounds().overlaps(sl.getBounds())) {
                        sl.damage(2);
                        break;
                    }
                }
            }
            for (Enemy sr : enemiesRight) {
                sr.update(delta, player.getBounds());
                if (sr.getBounds().overlaps(player.getBounds())) {
                    player.damage(sr.attack(delta));
                }
                if (player.getBitAttackBounds() != null) {
                    if (player.getBitAttackBounds().overlaps(sr.getBounds())) {
                        sr.damage(2);
                        break;
                    }
                }
            }
        }

        if (gameTimer < 5) audioManager.getSignalWinM().play();
        else if (gameTimer < 0.5f) audioManager.getSignalWinM().stop();

        if (ammo.isSpawn()) ammo.update(delta);
        if (health.isSpawn()) health.update(delta);
        if (killAll.isSpawn()) killAll.update(delta);
        if (shotgun.isSpawn()) shotgun.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            createMobs = !createMobs;
        }
    }

    public void updatePause() {
        audioManager.getSignalWinM().stop();
        audioManager.getGameOverM().stop();
    }

    public void renderPlay(SpriteBatch batch, float delta) {
        for (Bullet b : bullet) {
            if (b.isExist()) b.render(batch, delta);
        }
        if (createMobs) {
            for (Enemy sl: enemiesLeft) {
                sl.render(batch, delta);
            }
            for (Enemy sr: enemiesRight) {
                sr.render(batch, delta);
            }
        }
        ammo.render(batch, delta);
        health.render(batch, delta);
        killAll.render(batch, delta);
        shotgun.render(batch, delta);
        player.render(batch, delta);

        drawHUD(batch);
    }

    public void renderPause(SpriteBatch batch, float delta) {
        drawHUD(batch);
        for (Enemy sl: enemiesLeft) {
            if (!sl.isAlive()) sl.render(batch, delta);
        }
        for (Enemy sr: enemiesRight) {
            if (!sr.isAlive()) sr.render(batch, delta);
        }
        if (player.isAlive()) {
            player.render(batch, delta);
            player.setAttack(false);
            player.setMove(false);
            player.setDamage(false);
            player.initStateAnim();
        } else player.render(batch, delta);

    }

    public void drawHUD(SpriteBatch batch) {
        font.draw(batch, "LEVEL: " + level, APP_WIDTH / 2 - 20, APP_HEIGHT - 10);
        if ((int)gameTimer < 10) {
            font.draw(batch, "0" + (int)gameTimer + " sec", APP_WIDTH / 2 - 10, APP_HEIGHT - 30);
        } else font.draw(batch, (int)gameTimer + " sec", APP_WIDTH / 2 - 10, APP_HEIGHT - 30);

        font.draw(batch, "HP: " + (int)player.getHpActual(), + 10, APP_HEIGHT - 10);
        if (player.getIdWeapon() == 1) {
            font.draw(batch, "ammo: " + player.getAmmoActual(), + 120, APP_HEIGHT - 10);
        }
        font.draw(batch, "score: " + score, +10, APP_HEIGHT - 35);
    }

    private static void createLevel() {
        if (level == 1) gameTimer = 30f;
        else if (level == 2) gameTimer = 35f;
        else if (level == 3) gameTimer = 40f;
        else if (level == 4) gameTimer = 45f;
        else if (level == 5) gameTimer = 60f;
        else if (level == 6) gameTimer = 70f;
        else if (level == 7) gameTimer = 90f;
        else if (level >= 8) gameTimer = 90f + MathUtils.random(10f, 40f);
    }

    public void updateLevel(float delta) {
        if (gameState == GameScreen.GameState.PLAY) {
            gameTimer -= delta;
            if (gameTimer < 0) {
                level++;
                createLevel();
                gameState = GameScreen.GameState.PAUSE;
                System.out.println("press SPACE to continue");
            }
        }
    }

    public static void restartGame() {
        createLevel();
        player.getBounds().setPosition(APP_WIDTH / 2 - player.getBounds().width / 2, APP_HEIGHT / 2 - player.getBounds().height / 2);
        player.setHpActual(Player.HP_MAX);
        player.setAlive(true);
        if (player.getIdWeapon() == 1) player.setAmmoActual(Player.AMMO_MAX);
        for (Enemy sl: enemiesLeft) {
            if (sl.isAlive()) {
                sl.recreate();
                sl.recreate();
            }
        }
        for (Enemy sr: enemiesRight) {
            if (sr.isAlive()) {
                sr.recreate();
                sr.recreate();
            }
        }
        if (ammo.isSpawn()) ammo.destroy();
        if (health.isSpawn()) health.destroy();
        if (killAll.isSpawn()) killAll.destroy();
        if (shotgun.isSpawn()) shotgun.destroy();

        GameScreen.timer.reset();
        gameState = GameScreen.GameState.START;
    }

    public void dispose() {
        player.dispose();
        for (Bullet b : bullet) {
            if (b != null) b.dispose();
        }
        for (Enemy sl: enemiesLeft) {
            if (sl != null) sl.dispose();
        }
        for (Enemy sr: enemiesRight) {
            if (sr != null) sr.dispose();
        }
        if (ammo != null) ammo.dispose();
        if (health != null) health.dispose();
        if (killAll != null) killAll.dispose();
        if (shotgun != null) shotgun.dispose();
        audioManager.dispose();
    }
}
