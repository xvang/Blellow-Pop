package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Menu extends ScreenAdapter {

    BlellowPop blellowPop;

    private Stage stage;

    public Menu(BlellowPop p){
        blellowPop = p;
    }


    @Override
    public void show(){

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();


        final TextButton startButton = new TextButton("Play", blellowPop.asset.bubbleUI, "green");
        final TextButton settingButton = new TextButton("Settings", blellowPop.asset.bubbleUI, "green");
        final TextButton profileButton = new TextButton("Profile", blellowPop.asset.bubbleUI, "green");


        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                blellowPop.splash.nextScreen = (blellowPop.gameScreen);
                blellowPop.setScreen(blellowPop.splash);
            }
        });

        settingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                blellowPop.splash.nextScreen = (blellowPop.setting);
                blellowPop.setScreen(blellowPop.splash);
            }
        });
        profileButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                blellowPop.splash.nextScreen = (blellowPop.profile);
                blellowPop.setScreen(blellowPop.splash);
            }
        });


        startButton.setSize(200f, 75f);
        settingButton.setSize(200f, 75f);
        profileButton.setSize(200f, 75f);

        startButton.setPosition(w/2 - startButton.getWidth()/2, h*3/4);
        settingButton.setPosition(w/2 - settingButton.getWidth()/2, h/2);
        profileButton.setPosition(w/2 - profileButton.getWidth()/2, h/4);




        stage = new Stage();

        stage.addActor(startButton);
        stage.addActor(settingButton);
        stage.addActor(profileButton);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }





    @Override
    public void dispose(){
        stage.dispose();
    }
}
