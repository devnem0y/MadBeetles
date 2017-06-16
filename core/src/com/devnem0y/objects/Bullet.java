package com.devnem0y.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.devnem0y.utils.Constants.*;

public class Bullet extends GameObjects{

    private Rectangle bounds;
    private Texture texture;
    private float speed;
    private boolean exist;

    public Bullet() {
        bounds = new Rectangle();
        if (texture == null) texture = new Texture("image/bullet.png");
        bounds.setPosition(0.0f, 0.0f);
        bounds.width = 8;
        bounds.height = 8;
        speed = 950.5f;
    }

    @Override
    public void update(float dt, Rectangle playerBounds) {
        if (bounds.x < playerBounds.x) bounds.x -= speed * dt;
        if (bounds.x > playerBounds.x) bounds.x += speed * dt;
        if (bounds.x + bounds.width < 0 | bounds.x > APP_WIDTH) {
            destroy();
        }
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        if (texture != null) batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    void setup(float x, float y) {
        exist = true;
        this.bounds.setPosition(x, y);
    }

    public void destroy() {
        exist = false;
    }

    public boolean isExist() {
        return exist;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
