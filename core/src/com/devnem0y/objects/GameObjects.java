package com.devnem0y.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devnem0y.handlers.input.Controller;

abstract class GameObjects {
    public void update(float dt) {
        // Object
    }
    public void update(float dt, Rectangle playerBounds) {
        // Enemy
    }
    public void update(float dt, Controller stick) {
        // Player
    }
    public abstract void render(SpriteBatch batch, float dt);
    public abstract void dispose();

}
