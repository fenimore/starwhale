package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by fen on 3/21/16.
 */
public class BigStar extends Star {
    private int w = 29;
    private int h = 29;
    private int x_coord = MathUtils.random(0, 800); // what is the lenght?
    private int y_coord = 1150;
    private double nutrients = 5;
    private Texture image = new Texture(Gdx.files.internal("big_star.png"));

    public BigStar(float x, float y, float width, float height, Texture starImage) {
        super(x, y, width, height, starImage);
    }
    public BigStar(){
        this.x = x_coord;
        this.y = y_coord;
        this.width = w;
        this.height = h;
        this.setStarImage(image);
        this.setNutrients(nutrients);
    }

}
