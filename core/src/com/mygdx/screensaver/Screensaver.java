package com.mygdx.screensaver;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Random;

public class Screensaver extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        ShapeRenderer sr;
        
        final float XMIN = -1.5f, XMAX = 1.5f;
        final float YMIN = -1f, YMAX = 1f;
        
        final int n = 20;
        final int countCoefficient = 20;
        final int it = 20;
        final int xRes = 256, yRes = 256;        
        
        ArrayList<Coefficient> cf;
	
	@Override
	public void create () {
                sr = new ShapeRenderer();
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
                Gdx.gl.glClearColor(0.01f, 0.01f, 0.01f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                sr.setAutoShapeType(true);
                sr.begin();
                
                for (int num = 0; num < n; num++) {
                    float newX = (float) Math.random() * 3 - 1.5f;
                    float newY = (float) Math.random() * 2 - 1;
                    for (int step = -20; step < it; step++) {
                        int i = generateRandom(0, countCoefficient - 1);
                        float x = cf.get(i).a * newX + cf.get(i).b * newY + cf.get(i).c;
                        float y = cf.get(i).d * newX + cf.get(i).e * newY + cf.get(i).f;
                        if (step >= 0) {
                            float x1 = xRes - Trunc(((XMAX - newX) / (XMAX - XMIN)) * xRes);
                            float y1 = yRes - Trunc(((YMAX - newY) / (YMAX - YMIN)) * yRes);
                            if(x1 < xRes && y1 < yRes) {
                                
                            }
                        }
                    }
                }
                
                sr.end();
	}
        
        public static Random random = new Random();
        public static int generateRandom(int left, int right) {
            return Math.abs(random.nextInt()) % (right - left + 1) + left;
        }
        
        public float Trunc(float a) {
            return a;
        }
        
}