package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShootItOut extends ApplicationAdapter {
    SpriteBatch batch;
    StarLayerManager starLayerManager;
    private ShapesRendererImpl shapesRenderer;

    @Override
    public void create() {
        shapesRenderer = new ShapesRendererImpl();
        batch = new SpriteBatch();

        final int starsCount = 500;
        StarLayer layer1 = CreateStarLayer(starsCount, 50);

        StarLayer layer2 = CreateStarLayer(starsCount, 36);

        starLayerManager = new StarLayerManager(CreateStarLayerList(layer1, layer2));
    }

    private List<StarLayer> CreateStarLayerList(StarLayer... starLayers) {
        return Collections.unmodifiableList(Arrays.asList(starLayers));
    }

    private StarLayer CreateStarLayer(int starsCount, int starSpeed) {
        StarLayer layer = new StarLayer(shapesRenderer);
        layer.initialize(starsCount, starSpeed);
        return layer;
    }

    @Override
    public void render() {

        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f) * 2;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        starLayerManager.Render(dt);

    }

}

