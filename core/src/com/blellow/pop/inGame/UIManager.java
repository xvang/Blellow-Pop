package com.blellow.pop.inGame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blellow.pop.BlellowPop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UIManager {

    BlellowPop blellowPop;

    public int currentKills = 0;
    public float time = 0f;
    public float increment = 0f;

    private Label killLabel;
    private Label timeLabel;
    public Stage stage;
    public UIManager(BlellowPop p){
        blellowPop = p;

        /*if(blellowPop == null)
            System.out.println("null1");
        else if(blellowPop.asset == null)
            System.out.println("null2");
        else if(blellowPop.asset.bubbleUI == null)
            System.out.println("null3");*/

        //displays the time and bubbles popped
        killLabel = new Label("PopCounter: " + String.valueOf(currentKills), blellowPop.asset.bubbleUI, "white");
        timeLabel = new Label("Time: " + String.valueOf(round(time, 1)), blellowPop.asset.bubbleUI, "white");


        final Table table = new Table();

        table.add(killLabel).width(100f).height(20f).pad(10f).row();
        table.add(timeLabel).width(100f).height(20f).row();

        table.setPosition(100f, Gdx.graphics.getHeight() - 40f);



        stage = new Stage();

        //add table to stage
        stage.addActor(table);
    }

    public void init(){
        currentKills = 0;
        time = 0f;
    }

    public void draw(){

        stage.draw();

        //the delta time is stored because I want to increase the game time and player total time
        //by the same amount.
        increment = Gdx.graphics.getDeltaTime();
        time += increment;

        update();
    }


    //updates the labels.
    public void update(){
        timeLabel.setText("Time: " + String.valueOf(round(time, 1)));
        killLabel.setText("Bubbles Popped: " + String.valueOf(currentKills));

        //updating the time counter in player.java
        //the kill counter in player.java is updated in ballManager.java
        blellowPop.player.time += increment;
    }

    //Not mine. some answer on Stackoverflow.
    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
