package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.components.GameBase;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.core.components.cameras.Camera;
import com.nukedbit.core.components.cameras.OrthographicCamera;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.ViewPort;
import com.nukedbit.core.physics.Environment;
import com.nukedbit.core.physics.RigidBody;
import com.nukedbit.core.utils.Randomize;

public class MainGame extends GameBase {
    public MainGame(Graphics graphics,
                    GL20 gl20,
                    SpriteBatch spriteBatch,
                    ShapeRenderer shapeRenderer) {
        super(graphics, gl20, spriteBatch, shapeRenderer);

        this.random = new Randomize();
        this.environment = new Environment(0.1f);
    }

    private final Randomize random;
    private final Environment environment;

    @Override
    public void initialize() {
        this.prepareComponents();

        super.initialize();
    }

    private Camera buildOrthographicCamera(ViewPort viewPort) {
        Vector2 position = new Vector2(viewPort.getWidth() / 2f,
                                       viewPort.getHeight() / 2f);
        return new OrthographicCamera(this, position);
    }

    private void prepareComponents(GameComponent... initialComponents) {
        for (GameComponent component : initialComponents) {
            this.getComponents().add(component);
        }

        this.setActiveCamera(buildOrthographicCamera(this.getViewPort()));
        this.getComponents().add(new StarLayer(this, 500, 50, this.random));
        this.getComponents().add(new StarLayer(this, 500, 36, this.random));
        KeyboardInput input = new KeyboardInput(this,
                                                Gdx.input,
                                                new int[] { Input.Keys.LEFT,
                                                            Input.Keys.RIGHT,
                                                            Input.Keys.UP,
                                                            Input.Keys.DOWN });
        Player player = new Player(this,
                                   "player.png",
                                   80,
                                   80,
                                   new Vector2(this.getViewPort().getWidth() / 2f - 40f,
                                               this.getViewPort().getHeight() / 4f - 40f),
                                   new RigidBody(new Vector2(0f, 0f),
                                                 new Vector2(0f, 0f),
                                                 0.1f,
                                                 0f,
                                                 this.environment));
        input.subscribe(player);
        this.getComponents().add(input);
        this.getComponents().add(player);
    }

    @Override
    public void render() {
        this.getGl20().glClearColor(0, 0, 0, 1);
        this.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}