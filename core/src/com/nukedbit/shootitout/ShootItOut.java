package com.nukedbit.shootitout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.core.Game;
import com.nukedbit.core.components.GameComponent;
import com.nukedbit.core.components.cameras.Camera;
import com.nukedbit.core.components.cameras.OrthographicCamera;
import com.nukedbit.core.components.input.KeyboardInput;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.GraphicsAdapter;
import com.nukedbit.core.graphics.ViewPort;
import com.nukedbit.core.physics.Environment;
import com.nukedbit.core.physics.RigidBody;
import com.nukedbit.core.utils.Randomize;
import com.nukedbit.shootitout.components.Player;
import com.nukedbit.shootitout.components.StarLayer;

import java.util.ArrayList;

public class ShootItOut extends ApplicationAdapter implements Game, GameComponent, Drawable {
    private GraphicsAdapter graphicsAdapter;

    private final Randomize random;
    private final Environment environment;

    public ShootItOut() {
        this.random = new Randomize();
        this.environment = new Environment(0.1f);
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
        Camera camera = buildOrthographicCamera(this.getViewPort());

        this.prepareComponents(camera);
        this.initialize();

        this.graphicsAdapter = new GraphicsAdapter(Gdx.gl,
                                                   new ShapeRenderer(),
                                                   buildSpriteBatch(camera));

    }

    private SpriteBatch buildSpriteBatch(Camera camera) {
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.getViewProjection());
        return spriteBatch;
    }

    private Camera buildOrthographicCamera(ViewPort viewPort) {
        Vector2 position = new Vector2(viewPort.getWidth() / 2f,
                                       viewPort.getHeight() / 2f);
        return new OrthographicCamera(this, position);
    }

    private void prepareComponents(GameComponent... initialComponents) {
        for (GameComponent component : initialComponents) {
            this.components.add(component);
        }

        this.components.add(new StarLayer(this, 500, 50, this.random));
        this.components.add(new StarLayer(this, 500, 36, this.random));
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
                                                 this.environment));
        input.subscribe(player);
        this.components.add(input);
        this.components.add(player);
    }

    @Override
    public void render() {
        float dt = Math.min(getDeltaTime(), 1 / 60f) * 2;

        update(dt);

        this.graphicsAdapter.getGl20().glClearColor(0, 0, 0, 1);
        this.graphicsAdapter.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.render(graphicsAdapter);
    }

    public void update(float delta) {
        for (GameComponent component : this.components) {
            component.update(delta);
        }
    }

    @Override
    public void initialize() {
        for (GameComponent component : this.components) {
            component.initialize();
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
                ((Drawable) component).render(graphicsAdapter);
            }
        }
    }
}