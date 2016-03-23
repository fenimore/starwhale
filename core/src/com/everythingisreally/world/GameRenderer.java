package com.everythingisreally.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.everythingisreally.objects.stars.BigStar;
import com.everythingisreally.objects.stars.SmallStar;

/**
 * Created by fen on 3/22/16.
 */
public class GameRenderer {

    private GameWorld gameWorld;
    private OrthographicCamera camera; // Orthographic Makes 2d space out of 3D
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;

    // Health and Score Panels
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
        //Gdx.app.log("GameRenderer", "render");

        /** TODO: Make Pretty
         * Clear the screen with Dark Blue.
         * The arguments to glClearColor are the red, green
         * blue and alpha component in the range [0,1]
         * of the color to be used to clear the screen.
         */
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        //shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        batch.begin();
        // Check Whale Status and Draw Whale
        if (!gameWorld.isAlive()){
            gameWorld.getWhale().getWhaleImage().dispose(); // access whale through GameWorld Class
            scoreBitmap.draw(batch, "You've Died", 85, 400);
            gameWorld.getWhale().perish();
        } else if (gameWorld.isAlive()) {
            batch.draw(gameWorld.getWhale().getWhaleImage(),
                    gameWorld.getWhale().x, gameWorld.getWhale().y);
        }


        // Draw Score and Score TODO: Draw bar for health
        scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreBitmap.draw(batch, starScore, 25, 1000);
        healthBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        healthBitmap.draw(batch, whaleHealth, 25, 1030);

        // Draw Stars
        for(SmallStar smallStar: gameWorld.getSmallStars()) {
            batch.draw(smallStar.getStarImage(), smallStar.x, smallStar.y);
        }
        for(BigStar bigStar: gameWorld.getBigStars()) {
            batch.draw(bigStar.getStarImage(), bigStar.x, bigStar.y);
        }


        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Does this lead to performance issues?y
        shapeRenderer.setColor(135, 206, 235, 1);
        shapeRenderer.rect(20, 1075, gameWorld.getWhale().getHealth() * 3, 20); // max health is 300 px

        shapeRenderer.end();

        //Update Variables which populate Score and Health Bitmap
        whaleHealth = "Health " + gameWorld.getWhale().getHealth();
        starScore = "Score: " + gameWorld.getWhale().getScore();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
