package com.blellow.pop.inGame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
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
        paths.add(new Bezier<Vector2>(new Vector2(w*0, hBot), new Vector2(w*0, hTop)));


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
        quitButton.setPosition(Gdx.graphics.getWidth() - quitButton.getWidth() - 10f,
                Gdx.graphics.getHeight() - quitButton.getHeight() - 10f);
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

    float spawnPause = 2f;
    public void draw(SpriteBatch batch){

        if(bubbleArray.size < 25 && spawnPause >= 1.1f){
            spawn();
            spawnPause = 0f;
        }
        else{
            spawnPause += Gdx.graphics.getDeltaTime();
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


            if(b.wait > 0f){
                b.wait -= Gdx.graphics.getDeltaTime();
            }
            else{
                b.time += b.speed * Gdx.graphics.getDeltaTime();
                b.zt += b.zspeed * Gdx.graphics.getDeltaTime()/2;


                paths.get(b.chosenPath).valueAt(b.tmpV, b.time);
                paths.get(b.chosenPath).derivativeAt(b.tmpV2, b.time);

                b.tmpV2.nor();
                b.tmpV2.set(-b.tmpV2.y, b.tmpV2.x);
                b.tmpV2.scl((float)Math.sin(b.zt) * b.zigzag);
                b.tmpV.add(b.tmpV2);

                b.setPosition(b.tmpV.x, b.tmpV.y);


                if(b.time >= 1f){
                    b.init();//resets all the values.
                }

            }

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


    //Example: If user can pop 3 bubbles per second,
    //then spawn() should spawn 4 bubbles per second.
    public void spawn(){

        int x = (int)(Math.random()*10);

        //kills / time + 2
        float perSecond = ((float)blellowPop.gameScreen.ui.currentKills /
                blellowPop.gameScreen.ui.time) + 3;


        //90% chance of spawning.
        if(x < 8){
            for(int y = 0; y < (int)perSecond + 1; y++){
                Bubble b = blellowPop.asset.bubblePool.obtain();
                b.init();
                bubbleArray.add(b);
            }
        }
    }

    public void dragPop(int x, int y){

        //Rectangle located at [x,y] with dimensions 5x5.
        Rectangle dragRec = new Rectangle(x,y,5,5);

        //Checks every bubble if it intersects with 'dragRec'
        for(int s = 0; s < bubbleArray.size; s++){

            Bubble b = bubbleArray.get(s);

            Rectangle bRec = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());

            if(dragRec.overlaps(bRec)){
                b.alive = false;
            }
        }
    }

}
