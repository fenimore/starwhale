package com.everythingisreally.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.everythingisreally.objects.BigStar;
import com.everythingisreally.objects.SmallStar;
import com.everythingisreally.objects.Whale;

import java.util.Iterator;

/**
 * Created by fen on 3/22/16.
 */
public class GameWorld {
    private Whale whale;
    private Array<BigStar> bigStars;
    private Array<SmallStar> smallStars;

    // Intervals for Star spawn and Drain health
    private long lastBigStarTime;
    private long lastDrainTime;
    private long lastStarTime;
    // Overall Time played
    private long overallTime;
    private long firstTime;
    private boolean openingPause; // This is a peculiar check


    private boolean alive;

    // Whale Movement
    // TODO: Move this into Whale OBject
    private int RIGHT = 0;
    private int LEFT = 1;
    private int whaleDirection = 0; //lets say 0 is right?
    private float x_start = 800 / 2 - 32 / 2; // x origin
    private float y_start = 70; // y origin
    private float w_start = 32; // width
    private float h_start = 64; // height

    public GameWorld(Whale whale) {

        this.whale = whale;

        firstTime = TimeUtils.millis();
        openingPause = true;
        alive = true;

        whale.drainHealth();
        lastDrainTime = TimeUtils.nanoTime();


        bigStars = new Array<BigStar>();
        smallStars = new Array<SmallStar>();

    }


    public void update(){
        Gdx.app.log("this", "update");

        // what is current time?
        // 1000 milliseconds in a second
        if (openingPause){
            overallTime = TimeUtils.millis() -firstTime;
            System.out.println(Long.toString(overallTime));
            if (overallTime > 1500) openingPause = false;
        }

        // Check Whale size
        whale.refreshWhale();
        spawnSmallStar();
        spawnBigStar();

        if (whale.getHealth() <= 0) {
            alive = false;
        }
        // Drain Health at interval
        if(TimeUtils.nanoTime() - lastDrainTime > 100500000){
            whale.drainHealth();
            lastDrainTime = TimeUtils.nanoTime();
        }

        // process user input
        // INPUT must be JUST touched, else its buggy
        if(Gdx.input.justTouched()) {
            if(whaleDirection == RIGHT) {
                whaleDirection = LEFT;
            } else if(whaleDirection == LEFT) {
                whaleDirection = RIGHT;
            }

            if(openingPause){
                openingPause = false;
            }
        }

        // The movements!
        if(!openingPause){
            if(whaleDirection == RIGHT) whale.x -= 250 * Gdx.graphics.getDeltaTime();
            if(whaleDirection == LEFT) whale.x += 250 * Gdx.graphics.getDeltaTime();
        }

        // make sure the whale stays within the screen bounds
        if(whale.x < 0) whale.x = 0;
        if(whale.x > 800 - 32) whale.x = 800 - 32;

        // check if we need to create a new star TODO: Find sweet Spot
        if(TimeUtils.millis() - TimeUtils.nanosToMillis(lastStarTime) > 50000000) spawnSmallStar();
        // Spawn new big star at interval
        // THIS IS MOSTLY BROKEN TODO: Make this work better...
        if(TimeUtils.millis() - lastBigStarTime > 999999990){
            // flip a coin
            int toss = MathUtils.random(0, 1000);
            if(toss == 0){
                spawnBigStar();
                lastBigStarTime = TimeUtils.nanoTime();
            }
        }


        // TODO: PUT THESE INTO SEPERATE STAR UPDATING FUNCTION
        // Falling Stars, and Collision Checking
        Iterator<SmallStar> smallStarIter = smallStars.iterator();
        // the SMALL star updater
        while(smallStarIter.hasNext()){
            SmallStar smallStar = smallStarIter.next();
            smallStar.y -= 250 * Gdx.graphics.getDeltaTime();
            if(smallStar.y + 19 < 0) smallStarIter.remove();
            if(smallStar.overlaps(whale) && alive) { // PUT alive variable in Whale object?
                //plopSound.play(); // More annoying than good
                smallStarIter.remove();
                whale.addScore(1); //1
                whale.addHealth(smallStar.getNutrients()); // Calories/Nutrients are the health
            }
        }
        Iterator<BigStar> bigStarIter = bigStars.iterator();
        // the BIG star updater
        while(bigStarIter.hasNext()){
            BigStar bigStar =  bigStarIter.next();
            bigStar.y -= 250 * Gdx.graphics.getDeltaTime();
            if(bigStar.y + 29 < 0) bigStarIter.remove();
            if(bigStar.overlaps(whale) && alive) {
                //plopSound.play();
                bigStarIter.remove();
                whale.addScore(5); //5
                whale.addHealth(bigStar.getNutrients()); //5
            }
        }


    }

    private void spawnSmallStar() {
        SmallStar smallStar = new SmallStar();
        smallStars.add(smallStar);
        lastStarTime = TimeUtils.nanoTime();
    }

    private void spawnBigStar() {
        BigStar bigStar = new BigStar();
        bigStars.add(bigStar);
        lastBigStarTime = TimeUtils.nanoTime();
    }

    public Whale getWhale(){
        return whale;
    }

    public Array<SmallStar> getSmallStars() {
        return smallStars;
    }

    public Array<BigStar> getBigStars() {
        return bigStars;
    }

    public boolean isAlive() {
        return alive;
    }
}
