package com.everythingisreally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.everythingisreally.StarWhale;
import com.everythingisreally.objects.stars.BigStar;
import com.everythingisreally.objects.stars.SmallStar;
import com.everythingisreally.objects.Whale;
import com.everythingisreally.world.GameRenderer;
import com.everythingisreally.world.GameWorld;

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

    // TODO: Put this inside Whale Constuctor
    private float x_start = 800 / 2 - 32 / 2; // x origin
    private float y_start = 70; // y origin
    private float w_start = 32; // width
    private float h_start = 64; // height


    public GameScreen(final StarWhale gam) {
        this.game = gam;
        whale = new Whale(x_start, y_start, w_start, h_start);

        // World takes the Whale as argument
        world = new GameWorld(whale, game); // initialize world
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

        /** Doesn't work
         *         if(world.isLongDead()){
         *           game.setScreen(new MainMenuScreen(game));
         *           dispose();
         *         }
         */
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if(Gdx.input.justTouched()){
            resume();
        }

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
