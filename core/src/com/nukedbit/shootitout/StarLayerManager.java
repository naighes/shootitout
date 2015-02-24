package com.nukedbit.shootitout;

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

