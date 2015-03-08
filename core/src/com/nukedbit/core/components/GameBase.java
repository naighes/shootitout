package com.nukedbit.core.components;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.core.components.cameras.Camera;
import com.nukedbit.core.graphics.Drawable;
import com.nukedbit.core.graphics.ViewPort;

import java.util.ArrayList;

public abstract class GameBase implements GameComponent, Drawable {
    private final Graphics graphics;
    private final GL20 gl20;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;

    public GameBase(Graphics graphics,
                    GL20 gl20,
                    SpriteBatch spriteBatch,
                    ShapeRenderer shapeRenderer) {
        this.graphics = graphics;
        this.gl20 = gl20;
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
    }

    private ArrayList<GameComponent> components = new ArrayList<>();

    public void render() {
        for (GameComponent component : this.getComponents()) {
            if (component instanceof Drawable) {
                ((Drawable) component).render();
            }
        }
    }

    public ViewPort getViewPort() {
        return new ViewPort(this.getGraphics().getWidth(), this.getGraphics().getHeight());
    }

    public void update(float delta) {
        this.getSpriteBatch().setProjectionMatrix(this.getActiveCamera().getViewProjection());

        for (GameComponent component : this.getComponents()) {
            component.update(delta);
        }
    }

    public void initialize() {
        for (GameComponent component : this.getComponents()) {
            component.initialize();
        }
    }

    public ArrayList<GameComponent> getComponents() {
        return this.components;
    }

    public GameBase getGame() {
        return this;
    }

    protected Graphics getGraphics() {
        return graphics;
    }

    protected GL20 getGl20() {
        return gl20;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public Camera getActiveCamera() {
        // TODO: pretty bad!
        for (GameComponent component : this.components) {
            if (component instanceof Camera) {
                return (Camera) component;
            }
        }

        return null;
    }

    public void setActiveCamera(Camera camera) {
        // TODO: pretty bad!
        Camera activeCamera = getActiveCamera();

        if (this.components.contains(activeCamera)) {
            this.components.remove(activeCamera);
        }

        this.components.add(0, camera);
    }
}