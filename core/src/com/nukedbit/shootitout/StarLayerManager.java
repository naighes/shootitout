package com.nukedbit.shootitout;

import java.util.List;

public class StarLayerManager {
    private final List<StarLayer> starLayers;

    public StarLayerManager(List<StarLayer> starLayers) {
        this.starLayers = starLayers;
    }

    public void Render(float delta) {
        for (StarLayer starLayer : this.starLayers) {
            starLayer.render(delta);
        }
    }
}

