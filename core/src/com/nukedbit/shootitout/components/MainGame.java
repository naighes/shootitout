package com.nukedbit.shootitout.components;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.cameras.OrthographicCamera;
import com.nukedbit.framework.components.input.KeyboardInput;
import com.nukedbit.framework.physics.Environment;
import com.nukedbit.framework.physics.RigidBody;
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

        this.addPlayerAnimation(this.getPlayer());
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    private Player getPlayer() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof Player) {
                return (Player) component;
            }
        }

        return null;
    }

    private void prepareComponents() {
        this.setActiveCamera(new OrthographicCamera(this, new Vector2(0f, 0f)));

        this.setUpSound();

        this.getComponents().add(new Background(this,"game_background.png"));
        this.getComponents().add(new StarLayer(this, 500, 50, this.random));
        this.getComponents().add(new StarLayer(this, 500, 36, this.random));
        KeyboardInput input = new KeyboardInput(this,
                                                Gdx.input,
                                                new int[] { Input.Keys.LEFT,
                                                            Input.Keys.RIGHT,
                                                            Input.Keys.UP,
                                                            Input.Keys.DOWN,
                                                            Input.Keys.SPACE });
        Player player = buildPlayer();
        input.subscribe(player);
        this.getComponents().add(input);
        this.getComponents().add(player);

        this.getComponents().add(new Enemy(this));
    }

    private void setUpSound() {
        final Sound sound = Gdx.audio.newSound(Gdx.files.getFileHandle("A Tale From Outer Space.mp3",
                                                                       Files.FileType.Absolute));
        sound.loop(0.5F);
        sound.play();
    }

    private Player buildPlayer() {
        Vector2 position = new Vector2(-558f / 2f,
                                       ((-368f / 2f) - this.getGame().getViewPort().getHeight()) / 4f);
        RigidBody body = new RigidBody(new Vector2(0f, 0f),
                                       new Vector2(0f, 0f),
                                       0.1f,
                                       0f,
                                       this.environment);

        Player component = new Player(this,
                                      "Player_With_Engine_Fire.png",
                                      position,
                                      body,
                                      .5f,
                                      420.0f);

        return component;
    }

    private void addPlayerAnimation(Player component) {
        Rectangle[] frames = new Rectangle[15];

        for (int i = 0; i < 15; i++) {
            frames[i] = new Rectangle(0, i * 281, 268, 281);
        }

        component.setCurrentAnimation(frames, 0.04f);
    }

    @Override
    public void render() {
        this.getGl20().glClearColor(0, 0, 0, 1);
        this.getGl20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }
}