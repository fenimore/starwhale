package com.everythingisreally.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.everythingisreally.objects.BigStar;
import com.everythingisreally.objects.SmallStar;

/**
 * Created by fen on 3/22/16.
 */
public class GameRenderer {

    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // Whale Movement
    // TODO: Move this into Whale OBject
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

    public GameRenderer(GameWorld world){
        gameWorld = world;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 1150);
        batch = new SpriteBatch();

        // TODO: Health Bar
        starScore = "score: 0";
        scoreBitmap = new BitmapFont();
        whaleHealth = "Health: 100";
        healthBitmap = new BitmapFont();

    }
    public void render(){
        Gdx.app.log("GameRenderer", "render");

        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        // TODO: Make Pretty
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        if (!gameWorld.isAlive()){
            gameWorld.getWhale().getWhaleImage().dispose(); // access whale through GameWorld Class
            scoreBitmap.draw(batch, "You've Died", 85, 400);
            gameWorld.getWhale().perish();
        } else if (gameWorld.isAlive()) {
            batch.draw(gameWorld.getWhale().getWhaleImage(),
                    gameWorld.getWhale().x, gameWorld.getWhale().y);
        }


        // Score
        scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreBitmap.draw(batch, starScore, 25, 1000);
        // Health
        healthBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        healthBitmap.draw(batch, whaleHealth, 25, 1030);

        // TODO: Draw bar for health

        // Stars
        for(SmallStar smallStar: gameWorld.getSmallStars()) {
            batch.draw(smallStar.getStarImage(), smallStar.x, smallStar.y);
        }
        for(BigStar bigStar: gameWorld.getBigStars()) {
            batch.draw(bigStar.getStarImage(), bigStar.x, bigStar.y);
        }

        batch.end();

        whaleHealth = "Health " + gameWorld.getWhale().getHealth();
        starScore = "Score: " + gameWorld.getWhale().getScore();
    }
}
