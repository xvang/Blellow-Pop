package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

public class Asset {

    BlellowPop blellowPop;
    public TextureAtlas bubbleAtlas;
    public Skin bubbleUI, defaultUI, bubbleSkin;
    public Pool<Bubble> bubblePool;
    public Sound pop;


    public Asset(BlellowPop b){
        blellowPop = b;
        init();
    }


    public void init(){

        bubbleAtlas = new TextureAtlas(Gdx.files.internal("ball.pack"));
        bubbleUI = new Skin(Gdx.files.internal("bubbleUI.json"));
        defaultUI = new Skin(Gdx.files.internal("uiskin.json"));
        bubbleSkin = new Skin();

        bubbleSkin.addRegions(bubbleAtlas);

        bubblePool = new Pool<Bubble>() {
            @Override
            protected Bubble newObject() {
                return new Bubble();
            }
        };

        pop = Gdx.audio.newSound(Gdx.files.internal("pop.ogg"));

    }



    //not called.
    public void dispose(){

        bubbleAtlas.dispose();
        bubbleUI.dispose();
        defaultUI.dispose();
        bubbleSkin.dispose();
        bubblePool.clear();
        pop.dispose();



    }

}
