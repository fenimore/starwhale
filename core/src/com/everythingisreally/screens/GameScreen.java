package com.everythingisreally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.everythingisreally.objects.Whale;

import java.util.Iterator;

/**
 * Created by fen on 2/24/16.
 */

//TODO: FIND SWEET SPOT
// FOR: health drain and star generation and whale speed
// TODO: Create STAR class
public class GameScreen implements Screen {
    final StarWhale game;

    // The Variables
    private Texture smallStarImage;
    private Texture bigStarImage;
    private Texture whaleImage;
    private Sound plopSound;

    // Set Up the Camera and Sprite Batch so that image scales
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // The Programmable Shapes
    private Whale whale;
    private Array<Rectangle> smallStars;
    private Array<Rectangle> bigStars;

    // Intervals for Star spawn and Drain health
    private long lastBigStarTime;
    private long lastDrainTime;
    private long lastStarTime;

    // Whale Movement
    private int RIGHT = 0;
    private int LEFT = 1;
    private int whaleDirection = 0; //lets say 0 is right?
    private float x_start = 800 / 2 - 32 / 2; // x origin
    private float y_start = 70; // y origin
    private float w_start = 32; // width
    private float h_start = 64; // height

    // Health and Score
    private String starScore;
    BitmapFont scoreBitmap;
    private String whaleHealth;
    BitmapFont healthBitmap;
    private boolean alive;


    public GameScreen(final StarWhale gam) {
        this.game = gam;

        //Health and Score Initial to 100 and 0
        starScore = "score: 0";
        scoreBitmap = new BitmapFont();
        whaleHealth = "Health: 100";
        healthBitmap = new BitmapFont();
        alive = true;

        //Star Textures
        smallStarImage = new Texture(Gdx.files.internal("small_star.png"));
        bigStarImage = new Texture(Gdx.files.internal("big_star.png"));

        // Whale Textures?
        whaleImage = new Texture(Gdx.files.internal("star_whale.png"));

        // Sounds? TODO: Classy music
        plopSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        // Scale Textures ETC to same size
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1150);

        // Sprite Batch
        batch = new SpriteBatch();

        // create the Whale, extending Rectangle
        whale = new Whale(x_start, y_start, w_start, h_start, whaleImage);

        // create the raindrops array and spawn the first raindrop
        smallStars = new Array<Rectangle>();
        bigStars = new Array<Rectangle>();
        spawnSmallStar();
        spawnBigStar();

        // Start draining health,
        whale.drainHealth();
        lastDrainTime = TimeUtils.nanoTime();
    }


    private void spawnSmallStar() {
        Rectangle smallStar = new Rectangle();
        smallStar.x = MathUtils.random(0, 800 - 64);
        smallStar.y = 1150;
        smallStar.width = 19;
        smallStar.height = 19;
        smallStars.add(smallStar);
        lastStarTime = TimeUtils.nanoTime();
    }

    private void spawnBigStar() {
        Rectangle bigStar = new Rectangle();
        bigStar.x = MathUtils.random(0, 800 - 64);
        bigStar.y = 1150;
        bigStar.width = 29;
        bigStar.height = 29;
        bigStars.add(bigStar);
        lastBigStarTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        // TODO: Play cool Musics

    }

    @Override
    public void render(float delta) {

        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        // TODO: Make Pretty
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        // Check Whale size
        whale.refreshWhale();
        // Update stats
        whaleHealth = "Health " + whale.getHealth();
        starScore = "Score: " + whale.getScore();
        if (whale.getHealth() <= 0) {
            alive = false;
        }
        // Drain Health at interval
        if(TimeUtils.nanoTime() - lastDrainTime > 100500000){
            whale.drainHealth();
            lastDrainTime = TimeUtils.nanoTime();
        }

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the whale and
        // all stars and the Score and Health
        batch.begin();

        if (!alive){
            whaleImage.dispose();
            scoreBitmap.draw(batch, "You've Died", 85, 400);
            whale.perish();
        } else if (alive) {
            batch.draw(whale.getWhaleImage(), whale.x, whale.y);
        }
        // Score
        scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreBitmap.draw(batch, starScore, 25, 1000);
        // Health
        healthBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        healthBitmap.draw(batch, whaleHealth, 25, 1030); // TODO: Draw bar for health
        // Whale - GET THE appropriate Whale Sprie

        // Stars
        for(Rectangle smallStar: smallStars) {
            batch.draw(smallStarImage, smallStar.x, smallStar.y);
        }
        for(Rectangle bigStar: bigStars) {
            batch.draw(bigStarImage, bigStar.x, bigStar.y);
        }
        batch.end();

        // process user input
        // INPUT must be JUST touched, else its buggy
        if(Gdx.input.justTouched()) {
            if(whaleDirection == RIGHT) {
                whaleDirection = LEFT;
            } else if(whaleDirection == LEFT) {
                whaleDirection = RIGHT;
            }
        }

        // The movements!
        if(whaleDirection == RIGHT) whale.x -= 250 * Gdx.graphics.getDeltaTime();
        if(whaleDirection == LEFT) whale.x += 250 * Gdx.graphics.getDeltaTime();

        // make sure the whale stays within the screen bounds
        if(whale.x < 0) whale.x = 0;
        if(whale.x > 800 - 32) whale.x = 800 - 32;

        // check if we need to create a new star TODO: Find sweet Spot
        if(TimeUtils.nanoTime() - lastStarTime > 99919990) spawnSmallStar();
        // Spawn new big star at interval
        // THIS IS MOSTLY BROKEN TODO: Make this work better...
        if(TimeUtils.nanoTime() - lastBigStarTime > 1999999990){
            // flip a coin
            int toss = MathUtils.random(0, 1000);
            if(toss == 0){
                spawnBigStar();
            }else {
                lastBigStarTime = TimeUtils.nanoTime();
            }
            spawnBigStar();
        }

        // Falling Stars, and Collision Checking
        // Remove below screen and add health/score when collision
        Iterator<Rectangle> smallStarIter = smallStars.iterator();
        // the SMALL star updater
        while(smallStarIter.hasNext()){
            Rectangle smallStar = smallStarIter.next();
            smallStar.y -= 250 * Gdx.graphics.getDeltaTime();
            if(smallStar.y + 19 < 0) smallStarIter.remove();
            if(smallStar.overlaps(whale) && alive) { // PUT alive variable in Whale object?
                plopSound.play();
                smallStarIter.remove();
                whale.addScore(1);
                whale.addHealth(1);
            }
        }
        Iterator<Rectangle> bigStarIter = bigStars.iterator();
        // the BIG star updater
        while(bigStarIter.hasNext()){
            Rectangle bigstar =  bigStarIter.next();
            bigstar.y -= 250 * Gdx.graphics.getDeltaTime();
            if(bigstar.y + 29 < 0) bigStarIter.remove();
            if(bigstar.overlaps(whale) && alive) {
                plopSound.play();
                bigStarIter.remove();
                whale.addScore(5);
                whale.addHealth(5);
            }
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
        // TODO: Read about disposal
        // dispose of all the native resources
        smallStarImage.dispose();
        bigStarImage.dispose();
        whaleImage.dispose();
        plopSound.dispose();
        batch.dispose();
    }
}
