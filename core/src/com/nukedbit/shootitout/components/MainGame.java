package com.nukedbit.shootitout.components;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.physics.Environment;
import com.nukedbit.framework.utils.Randomize;
import com.nukedbit.shootitout.components.cameras.GameCamera;

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

    private void prepareComponents() {
        this.setActiveCamera(new GameCamera(this));

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

        Player enemy = new Player(this);
        input.subscribe(enemy);
        this.getComponents().add(enemy);
        this.getComponents().add(new Cube(this));
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