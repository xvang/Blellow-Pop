package com.blellow.pop;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class BlellowPop extends Game {


    public Asset asset;
    public Splash splash;
    public Menu menu;
    public Profile profile;
    public Setting setting;
    public GameScreen gameScreen;
    public Player player;
    public LoadSave loadSave;


	@Override
	public void create () {
		asset = new Asset(this);
        splash = new Splash(this);
        menu = new Menu(this);
        profile = new Profile(this);
        setting = new Setting(this);
        gameScreen = new GameScreen(this);
        loadSave = new LoadSave();
        player = loadSave.loadPlayer();


        splash.nextScreen = menu;
        this.setScreen(splash);
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		super.render();
	}
}
