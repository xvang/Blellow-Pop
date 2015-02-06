package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Setting extends ScreenAdapter {

    BlellowPop blellowPop;

    private Stage stage;


    Slider diffSlider;
    Slider soundSlider;

    private Label diffNumber;
    private Label soundNumber;

    public Setting(BlellowPop b){
        blellowPop = b;
    }

    @Override
    public void show(){

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        //slider
        diffSlider = new Slider(1,100,1,false,blellowPop.asset.defaultUI);
        soundSlider = new Slider(1,100,1,false,blellowPop.asset.defaultUI);


        //displays slider value. located right of slider.
        diffNumber = new Label("", blellowPop.asset.bubbleUI, "white");
        soundNumber = new Label("", blellowPop.asset.bubbleUI, "white");

        final Label message = new Label("To spawn more bubbles, increase \" More Bubbles! \"" +
                "But too many bubbles may slow down or crash the game, depending on your" +
                "phone or tablet.",
         blellowPop.asset.bubbleUI, "white");

        message.setPosition(Gdx.graphics.getWidth()/2 - message.getWidth()/2, Gdx.graphics.getHeight()*3/4);

        //displays slider name. located left of slider.
        final Label diffLabel = new Label("More Bubbles!", blellowPop.asset.bubbleUI, "white");
        final Label soundLabel = new Label("Sound", blellowPop.asset.bubbleUI, "white");


        //setting positions for slider.
        diffSlider.setPosition(w/2, h/2);
        soundSlider.setPosition(w/2, h/2 - 40f);

        //setting positions for name.
        diffLabel.setPosition(diffSlider.getX() - diffLabel.getWidth() - 15f, diffSlider.getY() - 10f);
        soundLabel.setPosition(soundSlider.getX() - soundLabel.getWidth() - 15f, soundSlider.getY() - 10f);

        //setting positions for number.
        diffNumber.setPosition(diffSlider.getX() + diffSlider.getWidth() + 10f, diffSlider.getY() + 10f);
        soundNumber.setPosition(soundSlider.getX() + soundSlider.getWidth() + 10f, soundSlider.getY()+ 10f);


        //button to return to menu.
        final TextButton menu = new TextButton("Return to Menu", blellowPop.asset.bubbleUI, "green");
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                blellowPop.splash.nextScreen = blellowPop.menu;
                blellowPop.setScreen(blellowPop.splash);
            }
        });

        menu.setSize(300f, 65f);
        menu.setPosition(w/2 - menu.getWidth()/2, soundSlider.getY() - 95f);



        stage = new Stage();


        //adding actors to the stage.
        //stage.addActor(diffSlider);
        stage.addActor(soundSlider);
        //stage.addActor(diffLabel);
        stage.addActor(soundLabel);
        //stage.addActor(diffNumber);
        stage.addActor(soundNumber);
        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        save();
    }


    public void save(){

        //getting the values from the sliders.
        float difficulty = diffSlider.getVisualValue();
        float sound = soundSlider.getVisualValue();

        //storing the values in profile.
        blellowPop.profile.sound = sound;

        //updating the display number to the right of sliders.
        diffNumber.setText(String.valueOf((int)difficulty));
        soundNumber.setText(String.valueOf((int)sound));
    }


    @Override
    public void dispose(){
        stage.dispose();
    }






}
