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
//Currently, there are 8 total paths.
public class BallManager {


    BlellowPop blellowPop;

    Array<Bezier<Vector2>> paths, reversePath;

    Array<Bubble> bubbleArray;

    //quit button is here instead of UIManager.java because
    //If it is here, then I don't have to add UIManager to the InputMultiplexer.
    //I don't even need a multiplexer.
    //The Gdx.input is just set to stage in here.
    final TextButton quitButton;
    public Stage stage;

    int size = 1000;
    public BallManager(BlellowPop p){
        blellowPop = p;
        paths = new Array<Bezier<Vector2>>();
        reversePath = new Array<Bezier<Vector2>>();
        bubbleArray = new Array<Bubble>();
        stage = new Stage();

        //creating the paths.
        float w = Gdx.graphics.getWidth()/10;
        float hTop = Gdx.graphics.getHeight() + 150f;
        float hBot = -150f;
        paths.add(new Bezier<Vector2>(new Vector2(w*1, hBot), new Vector2(w*1, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*2, hBot), new Vector2(w*2, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*3, hBot), new Vector2(w*3, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*4, hBot), new Vector2(w*4, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*0, hBot), new Vector2(w*0, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*5, hBot), new Vector2(w*5, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*6, hBot), new Vector2(w*6, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*7, hBot), new Vector2(w*7, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*8, hBot), new Vector2(w*8, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*9, hBot), new Vector2(w*9, hTop)));
        paths.add(new Bezier<Vector2>(new Vector2(w*10, hBot), new Vector2(w*10, hTop)));

        reversePath.add(new Bezier<Vector2>(new Vector2(w*1, hTop), new Vector2(w*1, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*2, hTop), new Vector2(w*2, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*3, hTop), new Vector2(w*3, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*4, hTop), new Vector2(w*4, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*0, hTop), new Vector2(w*0, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*5, hTop), new Vector2(w*5, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*6, hTop), new Vector2(w*6, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*7, hTop), new Vector2(w*7, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*8, hTop), new Vector2(w*8, hBot)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*9, hBot), new Vector2(w*9, hTop)));
        reversePath.add(new Bezier<Vector2>(new Vector2(w*10, hBot), new Vector2(w*10, hTop)));


        Array<String> balls = new Array<String>();
        balls.add("redball");
        balls.add("blueball");
        balls.add("orangeball");
        balls.add("yellowball");
        balls.add("purpleball");
        balls.add("greenball");
        balls.add("armyball");
        balls.add("pinkball");
        balls.add("violetball");

        //populating bubblepool.
        //Example: difficulty level is 50. So 250 objects are created.

        for(int x = 0; x < size; x++){

            //when we obtain bubble from pool,
            //all we want to change is the size and path and boolean 'alive'
            int rand = (int)(Math.random() * balls.size);
            Bubble b = blellowPop.asset.bubblePool.obtain();

            TextureRegion r = blellowPop.asset.bubbleAtlas.findRegion(balls.get(rand));


            b.customInit(r);
            //getting a random Drawable and setting it as
            //the picture for a bubble object.
            //int rand = (int)(Math.random()*drawables.size);
           // b.customInit(drawables.get(rand));

            bubbleArray.add(b);

            //stage.addActor(b);
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

        //size is the amount of bubbles created.
        //30 is a safe number to allow for split spawning.
        //I don't want to pool to be creating new bubbles.
        //But I have checks in place so pool should never be zero.
        //This is like double making sure pool doesn't create new bubbles.
        if((bubbleArray.size < size - 30) && spawnPause >= 1f){
            spawn();
            spawnPause = 0f;
        }
        else{
            spawnPause += Gdx.graphics.getDeltaTime();
        }


        move();
        checkforPopped();


        for(int x = 0; x < bubbleArray.size; x++){
            bubbleArray.get(x).draw(batch);
        }
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

                b.time += Gdx.graphics.getDeltaTime()/10;
                b.zt += b.zspeed * Gdx.graphics.getDeltaTime();


                if(b.up){
                    paths.get(b.chosenPath).valueAt(b.tmpV, b.time);
                    paths.get(b.chosenPath).derivativeAt(b.tmpV2, b.time);
                }
                else{
                    reversePath.get(b.chosenPath).valueAt(b.tmpV, b.time);
                    reversePath.get(b.chosenPath).derivativeAt(b.tmpV2, b.time);
                }


                b.tmpV2.nor();
                b.tmpV2.set(-b.tmpV2.y, b.tmpV2.x);
                b.tmpV2.scl((float)Math.sin(b.zt) * b.zigzag);
                b.tmpV.add(b.tmpV2);

                b.setPosition(b.tmpV.x, b.tmpV.y);

                //if bubble is going up, time > 1f means it reached top
                //if bubble is going down, time < 0f means it reached bottom
                //if bubbleArray is not "full", bubble restarts path.
                //If bubbleArray is full, bubble is returned to pool.
                if(b.time >= 1f){

                    if(bubbleArray.size < 200){
                        b.init();//resets all the values.
                    }
                    else{
                        bubbleArray.removeIndex(x);
                        blellowPop.asset.bubblePool.free(b);
                    }
                }

            }

        }
    }


    //checks for popped bubbles and returns to pool.
    //if a bubble is bigger than 90f in diameter, it should
    //split into multiple other bubbles, instead of going away.
    public void checkforPopped(){
        for(int x = 0; x < bubbleArray.size; x++){
            Bubble b = bubbleArray.get(x);
            if(!b.alive && b.wait < 0f){


                if(goodToSpawn() && b.getWidth() > 80f)
                    splitSpawn(b);


                //plays the pop sound
                float f = blellowPop.profile.sound / 100f;
                blellowPop.asset.pop.play(f);


                bubbleArray.removeIndex(x);
                blellowPop.asset.bubblePool.free(b);


               //counters that keep track of popped balloons are updated.
                blellowPop.gameScreen.ui.currentKills++;
                blellowPop.player.kills++;

            }
            //if bubble was clicked before wait period is over,
            //it is not destroyed.
            else if(!b.alive && !(b.wait < 0f)){
                b.alive = true;
            }
        }
    }

    //takes in a bubble, and spawns a bunch of other bubbles that are smaller.
    public void splitSpawn(Bubble b){

        int amount = (int)(Math.random()*3) + 2;

        float dimensions;

        for(int x = 0; x < amount; x++){

            if(goodToSpawn()){

                Bubble bubble = blellowPop.asset.bubblePool.obtain();

                dimensions = (float)(Math.random()*(b.getWidth() - 10f) + 10f);

                bubble.setSize(dimensions, dimensions);

                bubble.setPosition(b.getX(), b.getY());
                bubble.alive = true;
                bubble.time = b.time;
                bubble.zigzag = b.zigzag;
                bubble.tmpV2 = b.tmpV2;
                bubble.tmpV = b.tmpV;
                bubble.chosenPath = b.chosenPath;
                bubble.up = b.up;
                bubble.zt = b.zt;
                bubble.speed = (float)(Math.random()*0.1f + 0.02f);
                bubble.zigzag = b.zigzag;
                bubble.wait = b.wait;
                bubble.wait = (float)(Math.random()*0.5f);
                bubbleArray.add(bubble);
            }

        }
    }
    //Example: If user can pop 3 bubbles per second,
    //then spawn() should spawn 4 bubbles per second.
    public void spawn(){

        if(goodToSpawn()){
            //*kills / time) * 2
            float perSecond = ((float)blellowPop.gameScreen.ui.currentKills /
                    blellowPop.gameScreen.ui.time) * 3;

            if(perSecond > 4)
                perSecond = 4;
            for(int y = 0; y < (int)perSecond + 5; y++){
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


    public boolean goodToSpawn(){
        return blellowPop.asset.bubblePool.getFree() > 0 &&
                bubbleArray.size < 250;
    }
}
