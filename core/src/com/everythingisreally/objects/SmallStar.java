package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by fen on 3/21/16.
 */
public class SmallStar extends Star {
    int width = 19;
    int height = 19;
    int x_coord = MathUtils.random(0, 800 - 64);
    int y_coord = 1150;

    Texture starImage = new Texture(Gdx.files.internal("star_whale.png"));

    public SmallStar(float x, float y, float width, float height, Texture starImage) {
        super(x, y, width, height, starImage);
    }
    public SmallStar(){
        this.x = x_coord;
        this.y = y_coord;
        this.width = width;
        this.height = height;
        this.starImage = starImage;
    }

}
