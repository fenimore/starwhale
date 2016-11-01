package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import sun.rmi.runtime.Log;


/**
 * Created by fen on 2/24/16.
 */



public class Whale extends Rectangle {

    // Looks like a whale, no?
    static private Texture whaleImage;

    // Health and Score
    private double Health = 100;
    private int Score = 0;
    private double Drain = 0.2;//0.0;//0.2;
    private int Velocity = 250;
    final private int levelOne = 270;
    final private int levelTwo = 300;
    final private int levelThree = 340;
    final private int levelFour = 400;


    private int direction = 0; // 0 is right
    private Animation swimAnimationRight;
    private Animation swimAnimationLeft;
    private Animation swimAnimationRight20; // 120 percent increase
    private Animation swimAnimationLeft20; // 120 percent increase
    private Animation swimAnimationRight40;
    private Animation swimAnimationLeft40;
    private Animation swimAnimationRight70;
    private Animation swimAnimationLeft70;
    private double whale_width_2 = 32 * 1.2;
    private double whale_width_3 = 32 * 1.4;
    private double whale_width_4 = 32 * 1.7;

    public Whale(float x, float y, float width, float height) { // Constructor!!!!
        super(x, y, width, height);
        this.setAnimations();
        // Set direction
        this.direction = 0;
        // Should automatically determine starting size/position
    }


    public void addHealth(double x){ // Heal by Stars
        if(Health + (1* x) <= 100){ // why by multiples of one? What?
            Health += 1 * x;
        } else Health = 100;
    }

    public void drainHealth(){ // Drain Health
        if((this.Health -= this.Drain) <= 0){
            this.Health = 0; // Game Over!!!!
        } else this.Health -= this.Drain; // TODO: Find the sweet Spot
    }

    public void addScore(int x) { // Add score
        Score += x; // why is it a multiplier by one? What was I thinking?
    }

    public void perish(){ // die of hunger make rectangle non-existant
        this.setWidth(0);
        this.setHeight(0);

    }

    public Animation render() {
        // should I pass back rather the animation?
        if(Score >= 50 && Score < 100) {
            if (direction == 0) {
                return this.swimAnimationRight20;
            } else {
                return this.swimAnimationLeft20;
            }
        } else if(Score >= 50 && Score < 100) {
            if (direction == 0) {
                return this.swimAnimationRight40;
            } else {
                return this.swimAnimationLeft40;
            }
        } else if(Score >= 100) { //&& Score < 200) {
            if (direction == 0) {
                return this.swimAnimationRight70;
            } else {
                return this.swimAnimationLeft70;
            }
        }
        if (direction == 0) {
            return this.swimAnimationRight;
        } else {
            return this.swimAnimationLeft;
        }
    }
    // TODO: BE SMARTER!
    public void refreshWhale(){
        // Log.d("Velocity of Screen/whale", Integer.toString(this.Velocity));
        //System.out.println(Integer.toString(this.Velocity) + "Log Velocity");
        if(Score < 50) {
            // Do nothing
        } else if(Score >= 50 && Score < 100) {
            this.setWidth(((float) whale_width_2));
            this.Velocity = levelOne;
        } else if(Score >= 100 && Score < 200) {
            this.Velocity = levelTwo;
            this.setWidth(((float) whale_width_3));
        } else if(Score >= 200) {
            this.Velocity = levelThree;
            this.setWidth(((float) whale_width_4));
            // Let Drain be a bit higher
            this.Drain = 0.29;
        }
        // RIGHT now, 200 is the top level...
    }

    /**
     * Getters and setters
     *
     */
    public Texture getWhaleImage() {
        return whaleImage;
    }

    public void setWhaleImage(Texture whaleImage) {
        this.whaleImage = whaleImage;
    }

    // Texture
    public int getScore() {
        return Score;
    }



    public double getDrain() {
        return Drain;
    }

    public void setDrain(double drain) {
        Drain = drain;
    }


    public int getHealth() {
        return ((int) Math.round(Health));
    }

    public void setHealth(double health) {
        Health = health;
    }


    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getVelocity() {
        return Velocity;
    }

    public void modifyVelocity(int amount) {
        this.Velocity += amount;
    }

    public void setVelocity(int velocity) {
        Velocity = velocity;
    }


    public void setAnimations() {
        /* Right Animation Sheets */
        Texture swimSheetRight = new Texture(Gdx.files.internal("sw_left_spritesheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(swimSheetRight, swimSheetRight.getWidth()/2, swimSheetRight.getHeight());
        int index = 0;
        TextureRegion[] swimFramesRight = new TextureRegion[2];
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesRight[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationRight = new Animation(0.2f, swimFramesRight);
        // 20 percent increase
        swimFramesRight = new TextureRegion[2];
        swimSheetRight = new Texture(Gdx.files.internal("sw_left_spritesheet_20.png"));
        tmp = TextureRegion.split(swimSheetRight, swimSheetRight.getWidth()/2, swimSheetRight.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesRight[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationRight20 = new Animation(0.2f, swimFramesRight);
        // 40 percent increase
        swimFramesRight = new TextureRegion[2];
        swimSheetRight = new Texture(Gdx.files.internal("sw_left_spritesheet_40.png"));
        tmp = TextureRegion.split(swimSheetRight, swimSheetRight.getWidth()/2, swimSheetRight.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesRight[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationRight40 = new Animation(0.2f, swimFramesRight);
        // 70 percent increase
        swimFramesRight = new TextureRegion[2];
        swimSheetRight = new Texture(Gdx.files.internal("sw_left_spritesheet_70.png"));
        tmp = TextureRegion.split(swimSheetRight, swimSheetRight.getWidth()/2, swimSheetRight.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesRight[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationRight70 = new Animation(0.2f, swimFramesRight);

        /* Left Animation Sheets */
        Texture swimSheetLeft = new Texture(Gdx.files.internal("sw_right_spritesheet.png"));
        tmp = TextureRegion.split(swimSheetLeft, swimSheetLeft.getWidth()/2, swimSheetLeft.getHeight());
        index = 0;
        TextureRegion[] swimFramesLeft = new TextureRegion[2]; // Col times rows
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesLeft[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationLeft = new Animation(0.2f, swimFramesLeft);
        // 20 Percent
        swimFramesLeft = new TextureRegion[2]; // Col times rows
        swimSheetLeft = new Texture(Gdx.files.internal("sw_right_spritesheet_20.png"));
        tmp = TextureRegion.split(swimSheetLeft, swimSheetLeft.getWidth()/2, swimSheetLeft.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesLeft[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationLeft20 = new Animation(0.2f, swimFramesLeft);
        // 40 Percent
        swimFramesLeft = new TextureRegion[2]; // Col times rows
        swimSheetLeft = new Texture(Gdx.files.internal("sw_right_spritesheet_40.png"));
        tmp = TextureRegion.split(swimSheetLeft, swimSheetLeft.getWidth()/2, swimSheetLeft.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesLeft[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationLeft40 = new Animation(0.2f, swimFramesLeft);
        // 70 Percent
        swimFramesLeft = new TextureRegion[2]; // Col times rows
        swimSheetLeft = new Texture(Gdx.files.internal("sw_right_spritesheet_70.png"));
        tmp = TextureRegion.split(swimSheetLeft, swimSheetLeft.getWidth()/2, swimSheetLeft.getHeight());
        index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFramesLeft[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimationLeft70 = new Animation(0.2f, swimFramesLeft);
    }
}
