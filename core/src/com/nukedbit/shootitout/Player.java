package com.nukedbit.shootitout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;


public class Player {

    private Texture playerTexture;
    private float x;
    private float y;


    public Player(Texture texture)
    {
        playerTexture = texture;
    }

    public void render(Batch batch){
        batch.draw(playerTexture,x,y);
    }
}
