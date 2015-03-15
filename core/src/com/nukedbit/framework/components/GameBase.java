package com.nukedbit.framework.components;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nukedbit.framework.components.cameras.Camera;
import com.nukedbit.framework.graphics.Drawable;
import com.nukedbit.framework.graphics.ViewPort;

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
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof Drawable) {
                ((Drawable) component).render();
            }
        }
    }

    public ViewPort getViewPort() {
        return new ViewPort(this.getGraphics().getWidth(), this.getGraphics().getHeight());
    }

    public void update(float delta) {
        for (int i = 0; i < this.getComponents().size(); i++) {
            this.getComponents().get(i).update(delta);
        }
    }

    public void initialize() {
        for (int i = 0; i < this.getComponents().size(); i++) {
            this.getComponents().get(i).initialize();
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
        for (int i = 0; i < this.getComponents().size(); i++) {
            GameComponent component = this.getComponents().get(i);

            if (component instanceof Camera) {
                return (Camera) component;
            }
        }

        return null;
    }

    public void setActiveCamera(Camera camera) {
        // TODO: pretty bad!
        Camera activeCamera = getActiveCamera();

        int index = this.getComponents().indexOf(activeCamera);

        if (index != -1) {
            this.getComponents().remove(index);
        }

        this.getComponents().add(0, camera);
    }

    public abstract com.nukedbit.framework.physics.Environment getEnvironment();
}