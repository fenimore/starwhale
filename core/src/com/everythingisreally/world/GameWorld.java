package com.everythingisreally.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.everythingisreally.objects.stars.BigStar;
import com.everythingisreally.objects.stars.SmallStar;
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

    // in case they need modifying.. these concern Stars:
    private long TIMER_SMALL = 99919990; // nano seconds
    private long TIMER_BIG = 1999999990; // nano seconds
    private int PROBABILITY = 100;      // one out of ... for big stars

    // Overall Time played
    private long overallTime;
    private long firstTime;
    private long timeOfDeath; // Reset Game after a bity

    // Checking Booleans
    private boolean longDead = false;
    private boolean alive;
    private boolean openingPause; // This is a peculiar check


    // Whale Movement
    // TODO: Move this into Whale OBject
    private int RIGHT = 0;
    private int LEFT = 1;
    private int whaleDirection = 0; //lets say 0 is right?

    public GameWorld(Whale whale) {

        this.whale = whale; // Our hero

        firstTime = TimeUtils.millis(); // Game Starts

        openingPause = true; // We pause movement for one second
        alive = true; // Whale is alive

        whale.drainHealth(); // Immediately start draining
        lastDrainTime = TimeUtils.nanoTime(); // start the timer till next drain

        bigStars = new Array<BigStar>(); // The arrays of game objects
        smallStars = new Array<SmallStar>(); // Deal with them as arrays
    }


    public void update(){
        //Gdx.app.log("GameWorld", "update");

        // 1000 milliseconds in a second
        if (openingPause){
            overallTime = TimeUtils.millis() -firstTime;
            System.out.println(Long.toString(overallTime));
            if (overallTime > 1500) openingPause = false; // Fly straight until 1.5 seconds
        }

        // Update Whale size
        whale.refreshWhale();

        if (alive && whale.getHealth() <= 0){
            timeOfDeath = TimeUtils.millis();
            alive = false;
        }
        // For reseting game after Death
        //if (!alive && TimeUtils.millis() - timeOfDeath > 4000){
        //    longDead = true;
        //}

        // Drain Health at interval
        if(TimeUtils.nanoTime() - lastDrainTime > 100500000){
            whale.drainHealth();
            lastDrainTime = TimeUtils.nanoTime();
        }

        // User Input
        if(Gdx.input.justTouched()) { // Input must be JUST touched, else bugsss
            if(whaleDirection == RIGHT) {
                whaleDirection = LEFT;
            } else if(whaleDirection == LEFT) {
                whaleDirection = RIGHT;
            }
            if(openingPause){
                openingPause = false;
            }
        }

        // Whale Movement
        if(!openingPause){
            if(whaleDirection == RIGHT) whale.x -= 250 * Gdx.graphics.getDeltaTime();
            if(whaleDirection == LEFT) whale.x += 250 * Gdx.graphics.getDeltaTime();
        }

        // Whale and Screen Limits
        if(whale.x < 0) whale.x = 0;
        if(whale.x > 800 - 32) whale.x = 800 - 32; //32??

        // Generate Stars at Interval TODO: Find sweet Spot
        if(TimeUtils.nanoTime() - lastStarTime > 99919990) spawnSmallStar();
        if(TimeUtils.nanoTime() - lastBigStarTime > 1999999990){
            // flip a coin
            int toss = MathUtils.random(0, 100);// this is the probability
            if(toss == 0){                      // Makes this interesting...
                spawnBigStar();
                lastBigStarTime = TimeUtils.nanoTime();
            }
        }

        // Falling Stars, and Collision Checking
        moveSmallStars(smallStars);
        moveBigStars(bigStars);

        bonusPoints(whale.getScore());
    }

    private void bonusPoints(int score) {
        if(score == 200) scoreBig(); PROBABILITY = 50; TIMER_BIG = 1499999990;
        if(score == 100) scoreBig();
        //whale.addScore(1); // without addScore 1, the prize will repeat every frame
                           // thus multiplying the bonus for ever frame you remain
                           // with just that amount... this is actually quite interesting though
                           // so I'm going to leave it in
    }


    /**
     * Spawn a few stars and add to the Array of Stars
     * Then Check for the Collision as they Move
     */
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

    private void moveSmallStars(Array<SmallStar> stars){ // Can I get it to take in any stars?
        Iterator<SmallStar> starIter = stars.iterator();
        while(starIter.hasNext()){
            SmallStar star = starIter.next();
            star.y -= 250 * Gdx.graphics.getDeltaTime();
            if(star.y + star.getHeight() < 0) starIter.remove();
            if(star.overlaps(whale) && alive) { // is it necessary to keep alive boolean?
                // play sound
                starIter.remove();
                whale.addScore((int) star.getNutrients());
                whale.addHealth(star.getNutrients());
            }
        }
    }

    private void moveBigStars(Array<BigStar> stars){
        Iterator<BigStar> starIter = stars.iterator();
        while(starIter.hasNext()){
            BigStar star = starIter.next();
            star.y -= 250 * Gdx.graphics.getDeltaTime();
            if(star.y + star.getHeight() < 0) starIter.remove();
            if(star.overlaps(whale) && alive) { // is it necessary to keep alive boolean?
                // play sound
                starIter.remove();
                whale.addScore((int) star.getNutrients());
                whale.addHealth(star.getNutrients());
            }
        }
    }

    private void scoreBig(){ // release a bunch of starsy
        for(int i=1; i<3; i++){
            BigStar bigStar = new BigStar();
            bigStars.add(bigStar);
        }
        lastBigStarTime = TimeUtils.nanoTime();
        if(whale.getHealth() < 70) whale.addHealth(30);
        if(whale.getHealth() > 70) whale.setHealth(100);

    }
    /**
     * These functions are for the GameRenderer, to get the sprites
     * and for checking some boolean logics
     * and for the GameScreen for disposing the textures
     * @return the GameWorld important informationy
     */
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

    public boolean isLongDead() { // Not doing much right now...
        return longDead;
    }
}
