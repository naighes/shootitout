package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
* Copyright 2015 Sebastian Faltoni
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
        StarLayer layer = new StarLayer(shapesRenderer, new RandomValue());
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

