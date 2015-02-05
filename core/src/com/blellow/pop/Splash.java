package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;



//This is like a transition screen.
public class Splash extends ScreenAdapter {

    BlellowPop blellowPop;
    public Screen nextScreen;
    float timer = 0f;
    public Splash(BlellowPop b){
        blellowPop = b;
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.5f,0.4f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += Gdx.graphics.getDeltaTime();

        if(timer >= 3f){
            timer = 0f;
            blellowPop.setScreen(nextScreen);
        }
    }

    @Override
    public void dispose(){
        //No objects should be created in this class, only pointers.
    }
}
