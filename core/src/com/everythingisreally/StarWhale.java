package com.everythingisreally;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class StarWhale extends ApplicationAdapter {
	private Texture smallStarImage;
	private Texture bigStarImage;
	private Texture whaleImage;
	private Sound plopSound;


	// Set Up the Camera and Sprite Batch so that image scales
	private OrthographicCamera camera;
	private SpriteBatch batch;

	// The Programmable Shapes
	private Rectangle whale;
	private Array<Rectangle> smallStars;
	private Array<Rectangle> bigStars;

	// Itervals for Star spawn and Drain health
	private long lastBigStarTime;
	private long lastDrainTime;
	private long lastStarTime;

	// Whale Movement
	private int RIGHT = 0;
	private int LEFT = 1;
	private int whaleDirection = 0; //lets say 0 is right?
	private float x_start = 800 / 2 - 32 / 2; // x origin
	private float y_start = 70; // y origin
	private float w_start = 16; // width
	private float h_start = 32; // height

	// Health and Score
	private String starScore;
	BitmapFont scoreBitmap;
	private String whaleHealth;
	BitmapFont healthBitmap;



	@Override
	public void create () {
		//Star Textures
		smallStarImage = new Texture(Gdx.files.internal("small_star.png"));
		bigStarImage = new Texture(Gdx.files.internal("big_star.png"));

		// Whale Textures?
		whaleImage = new Texture(Gdx.files.internal("star_whale.png"));

		// Sounds? TODO: Classy music
		plopSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

		// Scale Textures ETC to same size
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Sprite Batch
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		// Replace by Whale Class extending Rectangle
		whale = new Rectangle();
		whale.x = 800 / 2 - 32 / 2; // center the whale horizontally
		whale.y = 20; // bottom left corner of the whale is 20 pixels above the bottom screen edge
		whale.width = w_start;
		whale.height = h_start;



		// create the raindrops array and spawn the first raindrop
		smallStars = new Array<Rectangle>();
		spawnStar();

	}

	private void spawnStar() {
		Rectangle smallStar = new Rectangle();
		smallStar.x = MathUtils.random(0, 800 - 64);
		smallStar.y = 480;
		smallStar.width = 19;
		smallStar.height = 19;
		smallStars.add(smallStar);
		lastStarTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the whale and
		// all stars
		batch.begin();
		batch.draw(whaleImage, whale.x, whale.y);
		for(Rectangle smallStar: smallStars) {
			batch.draw(smallStarImage, smallStar.x, smallStar.y);
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

		// check if we need to create a new star
		if(TimeUtils.nanoTime() - lastStarTime > 1000000000) spawnStar();

		// move the stars, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = smallStars.iterator();
		while(iter.hasNext()) {
			Rectangle smallStar = iter.next();
			smallStar.y -= 200 * Gdx.graphics.getDeltaTime();
			if(smallStar.y + 64 < 0) iter.remove();
			if(smallStar.overlaps(whale)) {
				plopSound.play();
				iter.remove();
			}
		}
	}

	// Clean Up!
	@Override
	public void dispose() {
		// dispose of all the native resources
		smallStarImage.dispose();
		bigStarImage.dispose();
		whaleImage.dispose();
		plopSound.dispose();
		batch.dispose();
	}
}
