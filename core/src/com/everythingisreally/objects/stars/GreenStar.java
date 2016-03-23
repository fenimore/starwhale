package com.everythingisreally.objects.stars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by fen on 3/22/16.
 */
public class GreenStar extends Star {
    private int w = 19;
    private int h = 19;
    private int x_coord = MathUtils.random(200, 600); // Green Stars only appear in the middle of screen
    private int y_coord = 1150;
    private double nutrients = 2;
    private Texture image = new Texture(Gdx.files.internal("small_star.png")); // change to green Star

    public GreenStar(float x, float y, float width, float height, Texture starImage) {
        super(x, y, width, height, starImage);
    }
    public GreenStar(){
        this.x = x_coord;
        this.y = y_coord;
        this.width = w;
        this.height = h;
        this.setStarImage(image);
        this.setNutrients(nutrients);
    }


}
