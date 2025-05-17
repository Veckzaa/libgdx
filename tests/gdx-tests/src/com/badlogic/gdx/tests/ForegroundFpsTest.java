package com.badlogic.gdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.tests.utils.GdxTest;
public class ForegroundFpsTest extends GdxTest {
    private SpriteBatch batch;
    private BitmapFont  font;
    private FPSLogger fpsLogger;
    private boolean capped = false;
    private long toggleTime;
    private float accumFps;
    private int samples;
    Stage stage;
    Texture texture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        fpsLogger = new FPSLogger();
        toggleTime = System.currentTimeMillis();
        Gdx.graphics.setForegroundFPS(0);
        Gdx.app.log("FpsCapTest", "UNCAPPED @∞");

        // Took the example from the Action test
        stage = new Stage();
        texture = new Texture(Gdx.files.internal("data/badlogic.jpg"), false);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        final Image img = new Image(new TextureRegion(texture));
        img.setSize(200, 200);
        img.setOrigin(50, 50);
        img.setPosition(100, 100);

        img.addAction(Actions.moveBy(2400, 500, 10));
        img.addAction(Actions.after(Actions.scaleTo(2, 2, 2)));

        stage.addActor(img);
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched()) {
            Gdx.app.log("FpsCapTest", "Cap activated");
            capped = !capped;
            if (capped) {
                Gdx.graphics.setForegroundFPS(20);
                Gdx.app.log("FpsCapTest", "CAPPED @20");
            } else {
                Gdx.graphics.setForegroundFPS(60);
                Gdx.app.log("FpsCapTest", "UNCAPPED @∞");
            }
            accumFps = 0;
            samples = 0;
            toggleTime = System.currentTimeMillis();
        }
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        font.draw(batch, capped ? "CAPPED @20" : "UNCAPPED @∞", 300, 300);
        font.draw(batch, "Press SPACE to toggle", 300, 300);
        batch.end();
        fpsLogger.log();
        float currentFps = Gdx.graphics.getFramesPerSecond();
        accumFps += currentFps;
        samples++;

        if (System.currentTimeMillis() - toggleTime >= 1000) {
            float avgFps = accumFps / samples;
            Gdx.app.log("FpsCapTest", "Avg FPS: " + avgFps);
            accumFps = 0;
            samples  = 0;
            toggleTime = System.currentTimeMillis();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        texture.dispose();
    }
}
