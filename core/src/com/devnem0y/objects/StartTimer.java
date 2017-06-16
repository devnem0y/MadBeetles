package com.devnem0y.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StartTimer extends GameObjects{

    private TextureAtlas atlas;
    private Animation<TextureRegion> anim;
    private float animTimer = 0f;

    public StartTimer() {
        atlas = new TextureAtlas("image/timer.atlas");
        anim = new Animation<TextureRegion>(1f, atlas.findRegions("frame"), Animation.PlayMode.LOOP);
    }

    public float reset() {
        return animTimer = 0f;
    }

    @Override
    public void render(SpriteBatch batch, float dt) {
        batch.draw(anim.getKeyFrame(animTimer), 0, 70);
        animTimer += dt;
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
