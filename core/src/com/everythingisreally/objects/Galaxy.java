package com.everythingisreally.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

/**
 * Created by fen on 3/22/16.
 */
public class Galaxy extends Circle {

    private int[] spriteCorner;
    private Texture galaxyImage;

    public Galaxy(float x, float y, float radius, Texture image) {
        super(x, y, radius);
        this.galaxyImage = image;
        this.spriteCorner = generateSpriteCorners(x, y, radius);
    }

    // Circle objects go from the center
    // but when grafting a texture onto,
    // one must calculate where the botton
    // left corner of a square with the same center
    // might be with some pythagoras, voila or not even...
    public int[] generateSpriteCorners(float x, float y, float radius){ // put in center
        int[] _spriteCorner;
        _spriteCorner = new int[2]; // why does it bug when I do int[1]??
        _spriteCorner[0] = (int) x - (int) radius;
        _spriteCorner[1] = (int) y - (int) radius;
        return _spriteCorner;
    }

    public int[] getSpriteCorner() {
        return spriteCorner;
    }

    public boolean is_touched(float xx,float yy) {
        float x = this.x;
        float y = this.y;
        // http://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
        float inverted_y = 1150 - yy; // The input listener listens from the top corner, 1150 is the height of the screen
        return (xx - x) * (xx - x) + (inverted_y - y) * (inverted_y - y) < radius * radius;// (x - center_x)^2 + (y - center_y)^2 < radius^2
    }


    public Texture getGalaxyImage() {
        return galaxyImage;
    }

    public void setGalaxyImage(Texture galaxyImage) {
        this.galaxyImage = galaxyImage;
    }




}
