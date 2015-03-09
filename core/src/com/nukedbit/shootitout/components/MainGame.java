package com.nukedbit.shootitout.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.cameras.OrthographicCamera;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.physics.Environment;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.physics.WorldObject;
import com.nukedbit.framework.utils.Randomize;

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

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    // NOTE: added for experimenting with camera.
    private WorldObject getPlayer() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof Player) {
                return (WorldObject) component;
            }
        }

        return null;
    }

    private void prepareComponents() {
        this.setActiveCamera(new OrthographicCamera(this, new Vector2(0f, 0f)));
        this.getComponents().add(new StarLayer(this, 500, 50, this.random));
        this.getComponents().add(new StarLayer(this, 500, 36, this.random));
        KeyboardInput input = new KeyboardInput(this,
                                                Gdx.input,
                                                new int[] { Input.Keys.LEFT,
                                                            Input.Keys.RIGHT,
                                                            Input.Keys.UP,
                                                            Input.Keys.DOWN,
                                                            Input.Keys.SPACE });
        Player player = new Player(this,
                                   "player.png",
                                   new Vector2(-558f / 2f,
                                               ((-368f / 2f) - this.getGame().getViewPort().getHeight()) / 4f),
                                   new RigidBody(new Vector2(0f, 0f),
                                                 new Vector2(0f, 0f),
                                                 0.1f,
                                                 0f,
                                                 this.environment),
                                   0.15f,
                                   420.0f);
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