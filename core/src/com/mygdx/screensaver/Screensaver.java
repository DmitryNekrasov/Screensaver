package com.mygdx.screensaver;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Screensaver extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        
        final int countCoefficient = 20;
        ArrayList<Coefficient> cf;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
                cf = new ArrayList<Coefficient>();
                for (int i = 0; i < countCoefficient; i++) {
                    float a = (float) Math.random() * 2 - 1;
                    float b = (float) Math.random() * 2 - 1;
                    float d = (float) Math.random() * 2 - 1;
                    float e = (float) Math.random() * 2 - 1;
                    
                    float c = (float) Math.random() * 2 + 1;
                    float f = (float) Math.random() * 2 + 1;
                    
                    float R = (float) Math.random();
                    float G = (float) Math.random();
                    float B = (float) Math.random();
                    
                    cf.add(new Coefficient(a, b, c, d, e, f, R, G, B));
                    
                }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
