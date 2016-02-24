package com.everythingisreally;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by fen on 2/24/16.
 */
// This is the Splash Screen,
public class MainMenuScreen implements Screen {

    final StarWhale game;

    OrthographicCamera camera;

    // Constructor takes the StarWhale Context as parameter
    // In fact, all screens take such a param.
    public MainMenuScreen(final StarWhale gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "StarWhale ", 100, 850);
        game.font.draw(game.batch, "Tap anywhere to begin", 800, 100);
        game.font.draw(game.batch, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nunc metus, iaculis quis blandit eget, vehicula et dui. Sed lectus lacus, tincidunt at risus ac, pharetra pharetra ex. Nullam at ornare ligula, id placerat sem. Sed non lectus non libero suscipit hendrerit. Integer malesuada libero vestibulum venenatis semper. Mauris condimentum orci sed blandit scelerisque. Donec ut diam porta, euismod velit et, dignissim elit. Cras non mauris arcu. Nam ipsum libero, posuere sed leo sit amet, ornare fringilla erat. Pellentesque et faucibus nunc. Vestibulum mattis mi tincidunt luctus pulvinar. Maecenas porta commodo nibh, eget sollicitudin mi. Phasellus semper augue ac odio maximus, in varius sapien imperdiet. "
                , 100, 650);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    }
}
