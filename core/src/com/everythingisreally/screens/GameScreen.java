package com.everythingisreally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.everythingisreally.StarWhale;
import com.everythingisreally.objects.BigStar;
import com.everythingisreally.objects.SmallStar;
import com.everythingisreally.objects.Whale;
import com.everythingisreally.world.GameRenderer;
import com.everythingisreally.world.GameWorld;

import java.util.Iterator;

/**
 * Created by fen on 2/24/16.
 */

//TODO: FIND SWEET SPOT
// FOR: health drain and star generation and whale speed
// TODO: Create STAR class
public class GameScreen implements Screen {
    final StarWhale game;

    private GameWorld world;
    private GameRenderer renderer;

    private Whale whale;

    private float x_start = 800 / 2 - 32 / 2; // x origin
    private float y_start = 70; // y origin
    private float w_start = 32; // width
    private float h_start = 64; // height


    public GameScreen(final StarWhale gam) {
        this.game = gam;
        whale = new Whale(x_start, y_start, w_start, h_start); // put this in default constructor

        // World takes the Whale as argument
        world = new GameWorld(whale); // initialize world
        renderer = new GameRenderer(world); // initialize renderer
    }

    @Override
    public void show() {
        // TODO: Play cool Musics

    }

    @Override
    public void render(float delta) {
        world.update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // TODO: Read about disposal
        // dispose of all the native resources
        world.getWhale().getWhaleImage().dispose();
        for (BigStar bigStar: world.getBigStars()){
            bigStar.getStarImage().dispose();
        }
        for (SmallStar smallStar: world.getSmallStars()){
            smallStar.getStarImage().dispose();
        }
        renderer.getBatch().dispose();
    }
}
