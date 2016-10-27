package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by fen on 2/24/16.
 */
public class Whale extends Rectangle {

    // Looks like a whale, no?
    private Texture whaleImage;

    // Health and Score
    private double Health = 100;
    private int Score = 0;
    private double Drain = 0.2;

    private int RIGHT = 0;
    private int LEFT = 1;
    private int direction = 0;
    // each Whale Image is incremented from the original, either
    // 20% increase, 40, 70, etcs
    private Texture whale_1 = new Texture(Gdx.files.internal("starwhale_right.png"));
    private Texture whale_2 = new Texture(Gdx.files.internal("star_whale_20.png"));
    private Texture whale_3 = new Texture(Gdx.files.internal("star_whale_40.png"));
    private Texture whale_4 = new Texture(Gdx.files.internal("star_whale_70.png"));
    private Texture whale_left = new Texture(Gdx.files.internal("starwhale_right.png"));
    private Texture whale_right = new Texture(Gdx.files.internal("starwhale_left.png"));
    // TODO:
    // Make For Right and Left
    private Texture swimSheet = new Texture(Gdx.files.internal("starwhale_sprite_animation.png"));
    private TextureRegion[] swimFrames = new TextureRegion[2 *1 ];
    private Animation swimAnimation;


    private double whale_w_2 = 32 * 1.2;
    private double whale_w_3 = 32 * 1.4;
    private double whale_w_4 = 32 * 1.7;

    public Whale(float x, float y, float width, float height) { // Constructor!!!!
        super(x, y, width, height);
        TextureRegion[][] tmp = TextureRegion.split(swimSheet, swimSheet.getWidth()/2, swimSheet.getHeight());
        int index = 0;
        for (int i = 0; i < 2; i++){ // loop through columns
            swimFrames[index++] = tmp[0][i]; // always one row
        }
        this.swimAnimation = new Animation(0.5f, swimFrames);
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
        Score += 1 * x; // why is it a multiplier by one? What was I thinking?
    }

    public void perish(){ // die of hunger make rectangle non-existant
        this.setWidth(0);
        this.setHeight(0);
    }

    public Animation render() {
        // should I pass back rather the animation?
        return this.swimAnimation;
    }
    // TODO: BE SMARTER!
    public void refreshWhale(){
        if (direction == 0) {
            setWhaleImage(whale_right);
        } else {
            setWhaleImage(whale_left);
        }

        if(Score < 50) {
            //setWhaleImage(whale_1);
        }
        if(Score >= 50 && Score < 100) {
            //setWhaleImage(whale_2);
            this.setWidth(((float) whale_w_2));
        }
        if(Score >= 100 && Score < 200) {
            //setWhaleImage(whale_3);
            this.setWidth(((float) whale_w_3));
        }
        if(Score >= 200) {
            //setWhaleImage(whale_4);
            this.setWidth(((float) whale_w_4));
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
}
