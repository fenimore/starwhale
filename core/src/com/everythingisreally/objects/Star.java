package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by fen on 3/21/16.
 */
public class Star extends Rectangle { // Circles have no collision system build in
    // Looks like a whale, no?
    private Texture starImage;
    // How much it heals
    private double Nutrients;

    /**
     * Constructor Methods
     */
    public Star(){
        super();
    }
    // basic constructor
    public Star(float x, float y, float width, float height, Texture starImag) {
        super(x, y, width, height);
        this.starImage = starImag;
    }
    // detailed constructor
    public Star(float x, float y, float width, float height, Texture starImag, double nutrient) {
        super(x, y, width, height);
        this.starImage = starImag;
        this.Nutrients =  nutrient;
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
