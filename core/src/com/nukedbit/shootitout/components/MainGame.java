package com.nukedbit.shootitout.components;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.physics.Environment;
import com.nukedbit.framework.physics.RigidBody;
import com.nukedbit.framework.utils.Randomize;
import com.nukedbit.shootitout.components.cameras.GameCamera;

public class MainGame extends GameBase {
    public MainGame(Graphics graphics,
                    GL20 gl20,
                    SpriteBatch spriteBatch,
                    ShapeRenderer shapeRenderer) {
        super(graphics, gl20, spriteBatch, shapeRenderer);

        this.random = new Randomize();
    }

    private final Randomize random;
    private Environment environment;

    @Override
    public void initialize() {
        this.prepareComponents();

        super.initialize();
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    private void prepareComponents() {
        this.setActiveCamera(new GameCamera(this));
        this.buildEnvironment();
        this.setUpSound();

        this.getComponents().add(new Background(this, "game_background.png"));
        this.getComponents().add(new StarLayer(this, 500, 50, this.random));
        this.getComponents().add(new StarLayer(this, 500, 36, this.random));
        KeyboardInput input = new KeyboardInput(this,
                                                Gdx.input,
                                                new int[] { Input.Keys.LEFT,
                                                            Input.Keys.RIGHT,
                                                            Input.Keys.UP,
                                                            Input.Keys.DOWN,
                                                            Input.Keys.SPACE });
        this.getComponents().add(input);

        Player player = buildPlayer();
        input.subscribe(player);
        this.getComponents().add(player);
        this.getComponents().add(new Cube(this, 0.11f));
    }

    private Player buildPlayer() {
        Matrix4 initialRotation = new Matrix4().rotate(new Vector3(0f, 1f, 0f), 180f)
                                               .rotate(new Vector3(1f, 0f, 0f), -90f);
        Vector3 position = new Vector3(0f, 0f, 0f);
        float scale = 0.13f;
        float maxThrust = 1.2f;
        RigidBody body = new RigidBody(new Vector3(0f, 0f, 0f),
                                       new Vector3(0f, 0f, 0f),
                                       0.1f,
                                       0f,
                                       this.getEnvironment());
        return new Player(this,
                          initialRotation,
                          position,
                          scale,
                          maxThrust,
                          body);
    }

    private void buildEnvironment() {
        com.badlogic.gdx.graphics.g3d.Environment innerEnvironment = new com.badlogic.gdx.graphics.g3d.Environment();
        innerEnvironment.set(this.buildAmbientLight());
        innerEnvironment.add(this.buildDirectionalLight());
        this.environment = new Environment(0.1f, innerEnvironment);
    }

    private ColorAttribute buildAmbientLight() {
        Color color = new Color(0.4f, 0.4f, 0.4f, 1f);
        return new ColorAttribute(ColorAttribute.AmbientLight, color);
    }

    private DirectionalLight buildDirectionalLight() {
        Color color = new Color(0.8f, 0.8f, 0.8f, 1.0f);
        Vector3 direction = new Vector3(-1f, -0.8f, -0.2f);
        return new DirectionalLight().set(color, direction);
    }

    private void setUpSound() {
        final Sound sound = Gdx.audio.newSound(Gdx.files.getFileHandle("A Tale From Outer Space.mp3",
                                                                       Files.FileType.Absolute));
        sound.loop(0.5F);
        sound.play();
    }

    @Override
    public void render() {
        this.getGl20().glClearColor(0, 0, 0, 1);
        this.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}