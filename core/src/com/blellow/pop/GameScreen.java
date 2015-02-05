package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blellow.pop.inGame.BallManager;
import com.blellow.pop.inGame.UIManager;


//the main game screen.
public class GameScreen extends ScreenAdapter implements InputProcessor{

    public BlellowPop blellowPop;
    public SpriteBatch batch;

    public UIManager ui;
    public BallManager ball;


    private InputMultiplexer multi;

    public GameScreen(BlellowPop p){
        blellowPop = p;

        ui = new UIManager(blellowPop);
        ball = new BallManager(blellowPop);

        multi = new InputMultiplexer();

        multi.addProcessor(this);
        multi.addProcessor(ball.stage);

        Gdx.input.setInputProcessor(multi);
    }


    @Override
    public void show(){
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(multi);
        //Gdx.input.setInputProcessor(ball.stage);


        //resetting the managers.
        ui.init();
        ball.init();
    }

    private float saveInterval = 0f;
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        //draws the balls.
        ball.draw(batch);
        batch.end();

        ui.draw();

        saveInterval += Gdx.graphics.getDeltaTime();

        if(saveInterval > 5f){
            saveInterval = 0f;
            blellowPop.loadSave.savePlayer(blellowPop.player);
        }
    }

    @Override
    public void dispose(){
        batch.dispose();
    }


    @Override
    public void hide(){
        ball.init();
        ui.init();
    }









    @Override
    public boolean scrolled(int pointer){
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int z, int pointer){

        return false;
    }

    @Override
    public boolean keyUp(int pointer){
        return false;
    }
    @Override
    public boolean keyDown(int pointer){

        return false;
    }

    @Override
    public boolean keyTyped(char key){

     return false;
    }


    @Override
    public boolean touchDown(int x, int y, int z, int pointer){
        return false;
    }


    @Override
    public boolean touchDragged(int x, int y, int pointer){
        ball.dragPop(x,y);

        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y){
        return false;
    }
}
