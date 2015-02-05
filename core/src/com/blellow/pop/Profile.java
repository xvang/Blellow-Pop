package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Profile extends ScreenAdapter {

    BlellowPop blellowPop;

    public float sound;
    public float difficulty;

    private Stage stage;

    public Profile(BlellowPop b){

        blellowPop = b;
        sound = 1f;
        difficulty = 1f;
    }


    @Override
    public void show(){


        //retrieving and formating the values from the player object.
        String kc = String.valueOf(blellowPop.player.kills);
        String time = String.valueOf(round(blellowPop.player.time, 3));
        final Label killCounter = new Label("Total bubbles popped: " + kc, blellowPop.asset.bubbleUI, "white");
        final Label timePlayed = new Label("Total time played: " + time, blellowPop.asset.bubbleUI, "white");

        //bubbles per second (popped).
        float fbps;
        //to prevent division by zero.
        if(blellowPop.player.time > 0f)
            fbps = (float)blellowPop.player.kills/blellowPop.player.time;
        else
            fbps = 0f;
        String bb = String.valueOf(round(fbps, 3));
        final Label bps = new Label("Bubbles per second: " + bb, blellowPop.asset.bubbleUI, "white");

        final Table table = new Table();

        //table to format the labels.
        table.add(killCounter).row();
        table.add(timePlayed).row();
        table.add(bps).row();


        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);


        //button to return to menu.
        final TextButton menu = new TextButton("Return to Menu", blellowPop.asset.bubbleUI, "green");
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                blellowPop.splash.nextScreen = blellowPop.menu;
                blellowPop.setScreen(blellowPop.splash);
            }
        });

        menu.setSize(350f, 55f);
        menu.setPosition(Gdx.graphics.getWidth()/2 - menu.getWidth()/2, Gdx.graphics.getHeight()/3 - 55f);


        //creating stage.
        stage = new Stage();

        stage.addActor(table);
        stage.addActor(menu);




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



    //Not mine. some answer on Stackoverflow.
    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
