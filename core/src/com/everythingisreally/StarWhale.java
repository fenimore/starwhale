package com.everythingisreally;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.everythingisreally.screens.MainMenuScreen;

//TODO: FIND SWEET SPOT
// FOR: health drain and star generation and whale speed
// TODO: Create STAR class
public class StarWhale extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	public boolean backpressed=false;

	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		//font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		//font.dispose();
	}

}