package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.core.physics.Environment;
import com.nukedbit.core.physics.RigidBody;
import com.nukedbit.shootitout.components.Player;
import com.nukedbit.shootitout.components.StarLayer;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.graphics.ViewPort;
import com.nukedbit.core.utils.Randomize;

import java.util.ArrayList;

public class ShootItOut extends ApplicationAdapter implements Game, GameComponent, Drawable {
    private GraphicsAdapter graphicsAdapter;
    private Camera camera;
    private SpriteBatch spriteBatch;

    public ShootItOut() {
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public ViewPort getViewPort() {
        return new ViewPort(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void create() {
        this.camera = new OrthographicCamera(this.getViewPort().getWidth(),
                                             this.getViewPort().getHeight());
        this.camera.position.set(this.getViewPort().getWidth() / 2f,
                                 this.getViewPort().getHeight() / 2f,
                                 0f);
        this.camera.update();
        this.spriteBatch = new SpriteBatch();
        this.spriteBatch.setProjectionMatrix(camera.combined);
        this.graphicsAdapter = new GraphicsAdapter(Gdx.gl,
                                                   new ShapeRenderer(),
                                                   this.spriteBatch);
        prepareComponents();
        initialize(graphicsAdapter);
    }

    private void prepareComponents() {
        Randomize random = new Randomize();
        Environment environment = new Environment(0.1f);
        this.components.add(new StarLayer(this, 500, 50, random));
        this.components.add(new StarLayer(this, 500, 36, random));
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
                                   new Vector2(getGame().getViewPort().getWidth() / 2f - 40f,
                                               getGame().getViewPort().getHeight() / 4f - 40f),
                                   new RigidBody(new Vector2(0f, 0f),
                                                 new Vector2(0f, 0f),
                                                 0.1f,
                                                 0f,
                                                 environment));
        input.subscribe(player);
        this.components.add(input);
        this.components.add(player);
    }

    @Override
    public void render() {
        float dt = Math.min(getDeltaTime(), 1 / 60f) * 2;

        update(dt);

        graphicsAdapter.getGl20().glClearColor(0, 0, 0, 1);
        graphicsAdapter.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        render(graphicsAdapter);
    }

    public void update(float delta) {
        for (GameComponent component : this.components) {
            component.update(delta);
        }
    }

    @Override
    public void initialize(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.components) {
            component.initialize(graphicsAdapter);
        }
    }

    @Override
    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    @Override
    public Game getGame() {
        return this;
    }

    @Override
    public void render(GraphicsAdapter graphicsAdapter) {
        for (GameComponent component : this.components) {
            if (component instanceof Drawable) {
                ((Drawable)component).render(graphicsAdapter);
            }
        }
    }
}