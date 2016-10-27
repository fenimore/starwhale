package com.everythingisreally.helpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by fen on 10/27/16.
 */
public class Animator implements ApplicationListener {

    Texture swimSheet;
    TextureRegion[] swimFrames;
    Animation swimAnimation;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;

    float stateTime;

    @Override
    public void create() {
        swimSheet = new Texture(Gdx.files.internal("starwhale_sprite_animation.png"));
        // or divide by frame col and fram rows
        /// region is array of array with rows and columns
        TextureRegion[][] tmp = TextureRegion.split(swimSheet, swimSheet.getWidth()/2, swimSheet.getHeight());
        swimFrames = new TextureRegion[2 * 1]; // cols times rows
        int index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFrames[index++] = tmp[1][i]; // always one row
        }
        swimAnimation = new Animation(0.5f, swimFrames);
        spriteBatch = new SpriteBatch();
        stateTime= 0f;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = swimAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
