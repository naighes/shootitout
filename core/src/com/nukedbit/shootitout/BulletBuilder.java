package com.nukedbit.shootitout;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.nukedbit.framework.components.GameBase;
import com.nukedbit.framework.components.GameComponent;
import com.nukedbit.framework.components.GameComponentBase;
import com.nukedbit.shootitout.components.Bullet;

public class BulletBuilder extends GameComponentBase {
    private final String soundAssetPath;

    public BulletBuilder(GameBase game, String soundAssetPath) {
        super(game);

        this.soundAssetPath = soundAssetPath;
    }

    private Sound sound;

    @Override
    public void initialize() {
        super.initialize();

        this.loadSound();
    }

    private void loadSound() {
        this.sound = Gdx.audio.newSound(Gdx.files.getFileHandle(this.soundAssetPath,
                                        Files.FileType.Absolute));
    }

    public void shootAt(Vector3 position) {
        GameComponent bullet = new Bullet(this.getGame(),
                new Vector3(position.x,
                            position.y,
                            position.z),
                0.018f);
        this.getGame().getComponents().add(bullet);
        this.sound.play();
    }
}