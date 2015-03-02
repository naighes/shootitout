package com.nukedbit.core;

import com.nukedbit.core.graphics.ViewPort;

public interface Game {
    ViewPort getViewPort();
    float getDeltaTime();
}