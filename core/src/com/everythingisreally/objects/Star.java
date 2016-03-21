package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by fen on 3/21/16.
 */
public class Star extends Rectangle {
    // Looks like a whale, no?
    private Texture starImage;

    // How much it heals
    private double Nutrients;

    // The constructor
    public Star(){
        super();
    }
    public Star(float x, float y, float width, float height, Texture starImage) {
        super(x, y, width, height);
        this.starImage = starImage;
    }

    public Texture getStarImage() {
        return starImage;
    }

    public void setStarImage(Texture starImage) {
        this.starImage = starImage;
    }

    public double getNutrients() {
        return Nutrients;
    }

    public void setNutrients(double nutrients) {
        Nutrients = nutrients;
    }
}
