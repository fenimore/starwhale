package com.everythingisreally.objects.stars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

/**
 * Created by fen on 3/21/16.
 */
public class SmallStar extends Star {
    private int w = 19;
    private int h = 19;
    private int x_coord = MathUtils.random(0, 800); // what is the length??
    private int y_coord = 1150;
    private double nutrients = 1;
    private Texture image_1 = new Texture(Gdx.files.internal("small_blur.png"));
    private Texture image_2 = new Texture(Gdx.files.internal("small_sphere_blur.png"));
    private Texture image_3 = new Texture(Gdx.files.internal("blur_star_2.png"));

    public SmallStar(float x, float y, float width, float height, Texture starImage) {
        super(x, y, width, height, starImage);
    }
    public SmallStar(){
        this.x = x_coord;
        this.y = y_coord;
        this.width = w;
        this.height = h;
        Random r = new Random();
        int result = r.nextInt(3);
        if(result == 0) {
            this.setStarImage(image_1);
        } else if (result == 1 ){
            this.setStarImage(image_2);
        } else {
            this.setStarImage(image_3);
        }
        this.setNutrients(nutrients);
    }

}
