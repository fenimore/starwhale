package com.everythingisreally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import com.everythingisreally.StarWhale;

/**
 * Created by fen on 2/24/16.
 */
// This is the Splash Screen,
public class MainMenuScreen implements Screen {

    final StarWhale game;

    OrthographicCamera camera;

    private Texture whaleImage;
    // Whale Placement
    ///private int RIGHT = 0;
    //private int LEFT = 1;
    //private int whaleDirection = 0; //lets say 0 is right? TODO: Moving in Welcome Screen?
    private float x_start = 800 / 2 - 32 / 2; // x origin
    private float y_start = 70; // y origin
    private float w_start = 32; // width
    private float h_start = 64; // height


    // Constructor takes the StarWhale Context as parameter
    // In fact, all screens take such a param.
    public MainMenuScreen(final StarWhale gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1150);

        whaleImage = new Texture(Gdx.files.internal("star_whale.png"));
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

        //https://github.com/libgdx/libgdx/wiki/Gdx-freetype
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Inconsolata.otf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font50 = generator.generateFont(parameter); // font size 50 something
        parameter.size = 30;
        BitmapFont font30 = generator.generateFont(parameter); // font size 30 something
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        // Load a TTF oh lord
        game.batch.begin();

        font30.draw(game.batch, "Lorem ipsum dolor sit amet, consectetur adipiscing \n" +
                "elit. Aenean nunc metus, iaculis quis blandit eget, vehicula et dui. \n" +
                "Sed lectus lacus, tincidunt at risus ac, pharetra pharetra ex, \n" +
                "ornare ligula, id placerat sem. Sed non lectus non libero suscipit. \n" +
                "Integer malesuada libero vestibulum venenatis semper. Mauris \n" +
                "condimentum orci sed blandit scelerisque. Donec ut diam porta, \n" +
                "euismod velit et, dignissim elit. Cras non mauris arcu. Nam \n" +
                "ipsum libero, posuere sed leo sit amet, ornare fringilla erat.\n" +
                " Pellentesque et faucibus nunc. Vestibulum mattis mi tincidunt\n" +
                " luctus pulvinar. Maecenas porta commodo nibh, eget sollicitudin mi.\n" +
                " Phasellus semper augue ac odio maximus, in varius sapien imperdiet. "
                , 50, 650);
        game.batch.draw(whaleImage, x_start, y_start);
        font50.draw(game.batch, "Star Whale ", 100, 850);
        //game.font.draw(game.batch, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nunc metus, iaculis quis blandit eget, vehicula et dui. Sed lectus lacus, tincidunt at risus ac, pharetra pharetra ex. Nullam at ornare ligula, id placerat sem. Sed non lectus non libero suscipit hendrerit. Integer malesuada libero vestibulum venenatis semper. Mauris condimentum orci sed blandit scelerisque. Donec ut diam porta, euismod velit et, dignissim elit. Cras non mauris arcu. Nam ipsum libero, posuere sed leo sit amet, ornare fringilla erat. Pellentesque et faucibus nunc. Vestibulum mattis mi tincidunt luctus pulvinar. Maecenas porta commodo nibh, eget sollicitudin mi. Phasellus semper augue ac odio maximus, in varius sapien imperdiet. "
        //        , 100, 650);
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
