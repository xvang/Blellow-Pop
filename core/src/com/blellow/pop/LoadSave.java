package com.blellow.pop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Toog on 2/5/2015.
 */
public class LoadSave {

    public LoadSave(){

    }

    public static class Playa{

        float time = 0f;
        int kills = 0;
    }

    public static void writeFile(String fileName, String s){
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }


    //Saving the backup file. It is not encoded.
    public static void writeSimpleFile(String fileName, String s){
        FileHandle file = Gdx.files.local(fileName);

        file.writeString(s, false);
    }

    public static String readSimpleFile(String fileName){
        FileHandle file = Gdx.files.local(fileName);

        if(file != null && file.exists()){
            String s = file.readString();

            if(!s.isEmpty()){
                return s;
            }
        }
        return "";
    }

    public static String readFile(String fileName){
        FileHandle file = Gdx.files.local((fileName));

        if (file != null && file.exists()){
            String s = file.readString();

            if(!s.isEmpty()){
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }


    public static void savePlayer(Player p){

        Playa playa = new Playa();
        Json json = new Json();

        playa.kills = p.kills;
        playa.time = p.time;

        writeFile("game.sav", json.prettyPrint(playa));
        writeSimpleFile("backup.sav", json.prettyPrint(playa));
    }


    public void clearSavedProfile(){
        readFile("game.sav");
        writeFile("game.sav", "");
    }


    public static Player loadPlayer(){

        String save = readFile("game.sav");
        Player player = new Player();

        if(!save.isEmpty()){

            Json json = new Json();

            Playa playa = json.fromJson(Playa.class, save);
            player.kills = playa.kills;
            player.time = playa.time;
        }


        return player;
    }
}
