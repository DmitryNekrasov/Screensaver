package com.mygdx.screensaver;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
        
        final int n = 5;
        final int countCoefficient = 20;
        final int it = 1000000;
        final int xRes = 1440, yRes = 900;        
        
        ArrayList<Coefficient> cf;
        Pixel[][] pixels;
	
	@Override
	public void create() {
                sr = new ShapeRenderer();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
                cf = new ArrayList<Coefficient>();
                pixels = new Pixel[xRes][yRes];
                
                int cnt = 0;
                
                while (cnt < countCoefficient) {
                    float a = (float) Math.random() * 2 - 1;
                    float b = (float) Math.random() * 2 - 1;
                    float d = (float) Math.random() * 2 - 1;
                    float e = (float) Math.random() * 2 - 1;
                    
                    float c = (float) Math.random() * 2 - 1;
                    float f = (float) Math.random() * 2 - 1;
                    
                    float R = (float) Math.random();
                    float G = (float) Math.random();
                    float B = (float) Math.random();
                    
                    float q1 = a * a + d * d;
                    if (q1 < 1) {
                        float q2 = b * b + e * e;
                        if (q2 < 1) {
                            float q3 = 1 + (float) Math.pow(a * e - b * d, 2);
                            if (q1 + q2 < q3) {
                                cf.add(new Coefficient(a, b, c, d, e, f, R, G, B));
                                cnt++;
                            }
                        }
                    }
                }
                
            initPixels();
	}

	@Override
	public void render() {
                Gdx.gl.glClearColor(0.01f, 0.01f, 0.01f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                sr.setAutoShapeType(true);
                sr.begin();
                
                for (int i = 0; i < xRes; i++) {
                    for (int j = 0; j < yRes; j++) {
                        if (pixels[i][j] != null) {
                            sr.setColor(new Color(pixels[i][j].R, pixels[i][j].G, pixels[i][j].B, 1f));
                            sr.point(i, j, 0);
                        }
                    }
                }
                
                sr.end();
	}
        
        int cnt = 0;
        public void initPixels() {
            for (int num = 0; num < n; num++) {
                float newX = (float) Math.random() * 3 - 1.5f;
                float newY = (float) Math.random() * 2 - 1;
                for (int step = -20; step < it; step++) {
                    int i = generateRandom(0, countCoefficient - 1);
                    float x = cf.get(i).a * newX + cf.get(i).b * newY + cf.get(i).c;
                    float y = cf.get(i).d * newX + cf.get(i).e * newY + cf.get(i).f;
                    
//                    newX = (float) Math.sin(x);
//                    newY = (float) Math.sin(y);
                    
//                    newX = x;
//                    newY = y;
                    
                    Point point = Spiral(x, y);
                    newX = point.x;
                    newY = point.y;
                    
                    if (step >= 0) {

                        int x1 = (int) (xRes - Trunc(((XMAX - newX) / (XMAX - XMIN)) * xRes));
                        int y1 = (int) (Trunc(((YMAX - newY) / (YMAX - YMIN)) * yRes));
                        if(x1 < xRes && y1 < yRes && x1 >= 0 && y1 >= 0) {
//                                System.err.println("qqq " + cnt++);
                            if (pixels[x1][y1] == null) {
                                pixels[x1][y1] = new Pixel(cf.get(i).R, cf.get(i).G, cf.get(i).B);
                            } else {
                                pixels[x1][y1].R = (pixels[x1][y1].R + cf.get(i).R) / 2;
                                pixels[x1][y1].G = (pixels[x1][y1].G + cf.get(i).G) / 2;
                                pixels[x1][y1].B = (pixels[x1][y1].B + cf.get(i).B) / 2;
                            }
                            pixels[x1][y1].counter++;
                        } else {
                            --step;
                        }
                    }
                    
                }
            }
            
            correction();
        }
        
        public static Random random = new Random();
        public static int generateRandom(int left, int right) {
            return Math.abs(random.nextInt()) % (right - left + 1) + left;
        }
        
        public float Trunc(float a) {
            return a;
        }
        
        void correction() {
            float max = 0f;
            float gamma = 2.2f;
            
            for (int i = 0; i < xRes; i++)
                for (int j = 0; j < yRes; j++) {
                    if (pixels[i][j] != null) {
                        pixels[i][j].normal = (float) Math.log10(pixels[i][j].counter);
                        if (pixels[i][j].normal > max) {
                            max = pixels[i][j].normal;
                        }
                    }
                }
            
            for (int i = 0; i < xRes; i++)
                for (int j = 0; j < yRes; j++) {
                    if (pixels[i][j] != null) {
                        pixels[i][j].normal /= max;
                        pixels[i][j].R = (float) (pixels[i][j].R * Math.pow(pixels[i][j].normal, 1.0 / gamma));
                        pixels[i][j].G = (float) (pixels[i][j].G * Math.pow(pixels[i][j].normal, 1.0 / gamma));
                        pixels[i][j].B = (float) (pixels[i][j].B * Math.pow(pixels[i][j].normal, 1.0 / gamma));
                    }
                    
                }
            
        }
        
        Point Spiral(float x, float y) {
            float teta = (float) Math.atan(x / y);
            float r = (float) Math.sqrt(x * x + y * y);
            float newX = (float) (Math.cos(teta) + Math.sin(r)) / r;
            float newY = (float) (Math.sin(teta) - Math.cos(r)) / r;
            return new Point(newX, newY);
        }
        
}