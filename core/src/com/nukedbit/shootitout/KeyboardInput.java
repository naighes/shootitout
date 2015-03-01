package com.nukedbit.shootitout;

import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class KeyboardInput implements SceneComponent, Observable<KeyPressed>  {
    private final Input input;
    private ArrayList<Observer<KeyPressed>> observers = new ArrayList<>();

    private final int[] lookup = new int[] {
            Input.Keys.LEFT,
            Input.Keys.RIGHT
    };

    public KeyboardInput(Input input) {
        this.input = input;
    }

    @Override
    public void update(float delta, GraphicsAdapter graphicsAdapter) {
        ArrayList<KeyPressed> stream = new ArrayList<>();

        for (int key : lookup) {
            if (input.isKeyPressed(key)) {
                stream.add(new KeyPressed(key));
            }
        }

        notify(stream);
    }

    private void notify(ArrayList<KeyPressed> stream) {
        for (KeyPressed key : stream) {
            for (Observer<KeyPressed> observer : observers) {
                observer.notify(key);
            }
        }
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {

    }

    @Override
    public void subscribe(Observer<KeyPressed> observer) {
        this.observers.add(observer);
    }
}