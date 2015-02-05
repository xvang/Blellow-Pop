package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;


public class Bubble extends Image implements Pool.Poolable{



    public float time;
    public float zt;
    public float speed;
    public float zspeed;
    public float wait;
    public float zigzag;
    public Vector2 tmpV, tmpV2;
    public boolean alive;
    public int chosenPath;

    public Bubble(){
        time = 0f;

        zt = 0f;
        speed = (float)(Math.random()*0.02f + 0.001f);
        zspeed = (float)(Math.random()*1f + 1f);
        wait = (float)(Math.random()*3f);
        zigzag = Gdx.graphics.getDensity() * ((float)(Math.random()*32f)  + 32f);

       // rate = (float)Math.random() % 0.0005f;

        tmpV = new Vector2();
        tmpV2 = new Vector2();

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
        float rand = (float)Math.random()*70f + 50f;
        this.setSize(rand, rand);
    }
    @Override
    public void reset(){
        alive = true;
    }

    public void init(){

        alive = true;
        float rand = (float)Math.random()*70f + 50f;
        this.setSize(rand, rand);
        chosenPath = (int)(Math.random()*7);

        this.setPosition(-150f, -150f);
        this.time = 0f;

        time = 0f;

        zt = 0f;
        speed = (float)(Math.random()*0.05f + 0.02f);
        zspeed = (float)(Math.random()*1f + 1f);
        wait =  (float)(Math.random()*3f);
        zigzag = Gdx.graphics.getDensity() * ((float)(Math.random()*32f)  + 32f);


        tmpV.set(0f,0f);
        tmpV2.set(0f,0f);

    }



}
