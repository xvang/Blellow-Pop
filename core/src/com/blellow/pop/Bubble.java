package com.blellow.pop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;


public class Bubble extends Image implements Pool.Poolable{

    public float time;
    public float stray;
    public boolean alive;
    public int chosenPath;
    public float rate;
    public Bubble(){
        time = 0f;
        stray = 0f;
        rate = (float)Math.random() % 0.002f;

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                alive = false;
            }
        });
    }

    //called once only.
    public void customInit(Drawable d){
        this.setDrawable(d);
    }
    @Override
    public void reset(){
        alive = true;
    }

    public void init(){

        alive = true;
        float rand = (float)Math.random()*70f + 30f;
        this.setSize(rand, rand);
        chosenPath = (int)(Math.random()*7);

        this.setPosition(-150f, -150f);
        this.time = 0f;
        this.rate = (float)Math.random() % 0.002f + 0.001f;
    }



}
