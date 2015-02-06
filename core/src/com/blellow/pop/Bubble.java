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


public class Bubble extends Sprite implements Pool.Poolable{



    public float time;
    public float zt;
    public float speed;
    public float zspeed;
    public float wait;
    public float zigzag;
    public Vector2 tmpV, tmpV2;
    public boolean alive, up;//if up == true, bubble goes up. if false, bubble goes down.
    public int chosenPath = (int)(Math.random()*11);
    private TextureRegion region;
    public Bubble(){
        time = 0f;
        zt = 0f;
        speed = (float)(Math.random()*0.1f + 0.03f);
        zspeed = (float)(Math.random()*1f + 1f);
        wait = (float)(Math.random()*3f);
        zigzag = Gdx.graphics.getDensity() * ((float)(Math.random()*32f)  + 32f);
        region = new TextureRegion();

        tmpV = new Vector2();
        tmpV2 = new Vector2();

        float rand = (float)Math.random()*80f + 70f;
        this.setSize(rand, rand);
    }

    //called once only.
    public void customInit(TextureRegion r){

        region.setRegion(r);

        this.setRegion(r);
        this.setBounds(this.getX(), this.getY(), r.getRegionWidth(), r.getRegionHeight());
        float rand = (float)Math.random()*70f + 60f;
        this.setSize(rand, rand);

    }
    @Override
    public void reset(){
        alive = true;

        if((int)(Math.random()*2) > 0)
            up = true;
        else
            up = false;

        this.setRegion(region);
    }

    public void init(){

        alive = true;
        this.setRegion(region);

        if((int)(Math.random()*2) > 0)
            up = true;
        else
            up = false;


        float rand = (float)Math.random()*70f + 60f;
        this.setSize(rand, rand);
        chosenPath = (int)(Math.random()*11);

        this.setPosition(-150f, -150f);
        this.time = 0f;

        time = 0f;

        zt = 0f;
        speed = (float)(Math.random()*0.1f + 0.02f);
        zspeed = (float)(Math.random()*1f + 0.3f);
        wait =  (float)(Math.random()*3f);
        zigzag = Gdx.graphics.getDensity() * ((float)(Math.random()*32f)  + 32f);


        tmpV.set(0f,0f);
        tmpV2.set(0f,0f);

    }



}
