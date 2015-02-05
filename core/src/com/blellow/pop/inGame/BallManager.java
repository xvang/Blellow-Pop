package com.blellow.pop.inGame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.blellow.pop.BlellowPop;
import com.blellow.pop.Bubble;


//this class controls the movements of the bubbles.
//Currently, there are 7 total paths.
public class BallManager {

    BlellowPop blellowPop;

    Array<Bezier<Vector2>> paths;

    Array<Bubble> bubbleArray;

    //quit button is here instead of UIManager.java because
    //If it is here, then I don't have to add UIManager to the InputMultiplexer.
    //I don't even need a multiplexer.
    //The Gdx.input is just set to stage in here.
    final TextButton quitButton;
    public Stage stage;

    public BallManager(BlellowPop p){
        blellowPop = p;
        paths = new Array<Bezier<Vector2>>();
        bubbleArray = new Array<Bubble>();
        stage = new Stage();

        //creating the paths.
        float w = Gdx.graphics.getWidth()/8;
        float hTop = Gdx.graphics.getHeight() + 50f;
        float hBot = -50f;
        paths.add(new Bezier<Vector2>(new Vector2(w*1, hBot), new Vector2(w*1, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*2, hBot), new Vector2(w*2, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*3, hBot), new Vector2(w*3, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*4, hBot), new Vector2(w*4, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*5, hBot), new Vector2(w*5, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*6, hBot), new Vector2(w*6, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*7, hBot), new Vector2(w*7, hTop)));


        //populating the drawables array.
        Array<Drawable> drawables= new Array<Drawable>();
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("redball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("blueball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("orangeball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("yellowball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("violetball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("purpleball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("greenball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("armyball"));
        drawables.add(blellowPop.asset.bubbleSkin.getDrawable("pinkball"));


        //populating bubblepool with 50 bubbles.
        for(int x = 0; x < 50; x++){

            //when we obtain bubble from pool,
            //all we want to change is the size and path and boolean 'alive'
            Bubble b = blellowPop.asset.bubblePool.obtain();


            //getting a random Drawable and setting it as
            //the picture for a bubble object.
            int rand = (int)(Math.random()*drawables.size);
            b.customInit(drawables.get(rand));

            bubbleArray.add(b);

            stage.addActor(b);
        }

        //freeing all the bubbles back into pool.
        blellowPop.asset.bubblePool.freeAll(bubbleArray);
        bubbleArray.clear();


        //button to quit the game and return to menu.
        //located at bottom right.
        quitButton = new TextButton("Quit", blellowPop.asset.bubbleUI, "red");
        quitButton.setSize(100f, 50f);
        quitButton.setPosition(Gdx.graphics.getWidth() - quitButton.getWidth() - 10f, 10f);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){


                //Once the screen changes, the hide() function in gameScreen.java is called.
                //everything is reset there.
                blellowPop.splash.nextScreen = blellowPop.menu;
                blellowPop.setScreen(blellowPop.splash);
            }
        });


        stage.addActor(quitButton);
    }

    public void init(){
        blellowPop.asset.bubblePool.freeAll(bubbleArray);
        bubbleArray.clear();
    }


    public void draw(SpriteBatch batch){

        if(bubbleArray.size < 50){
            spawn();
        }


        move();
        checkforPopped();

        for(int x = 0; x < bubbleArray.size; x++)
            bubbleArray.get(x).draw(batch, 1);

        quitButton.draw(batch, 1);
    }

    //moves the bubble
    public void move(){

        for(int x = 0; x < bubbleArray.size; x++){
            Bubble b = bubbleArray.get(x);
            Vector2 out = new Vector2();

            paths.get(b.chosenPath).valueAt(out, b.time);

            b.setPosition(out.x, out.y);

            b.time += b.rate;

            //reached end of path, restart.
            if(b.time >= 1f)
                b.time = 0f;


        }
    }


    //checks for popped bubbles and returns to pool.
    public void checkforPopped(){
        for(int x = 0; x < bubbleArray.size; x++){
            Bubble b = bubbleArray.get(x);
            if(!b.alive){

                bubbleArray.removeIndex(x);
                blellowPop.asset.bubblePool.free(b);

               //counters that keep track of popped balloons are updated.
                blellowPop.gameScreen.ui.currentKills++;
                blellowPop.player.kills++;

            }
        }
    }


    public void spawn(){
        int x = (int)(Math.random()*10);

        //80% chance of spawning.
        if(x < 8){
            for(int y = 0; y < 5; y++){
                Bubble b = blellowPop.asset.bubblePool.obtain();
                b.init();
                bubbleArray.add(b);
            }
        }
    }

}
