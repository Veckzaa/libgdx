package com.badlogic.gdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Manual visual test for BACKSPACE key behavior in both desktop and HTML5 backends.
 * Press and hold BACKSPACE to see keyDown (via first keyTyped), repeated PRESSED, and keyUp events asserted.
 */
public class BackSpaceTest extends GdxTest {
    private SpriteBatch batch;
    private BitmapFont font;
    private final Viewport viewport = new FitViewport(160, 90);

    private boolean sawDown;
    private boolean sawPressed;
    private boolean sawUp;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        Gdx.input.setCatchKey(Input.Keys.BACKSPACE, true);
        sawDown = false;
        sawPressed = false;
        sawUp = false;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (character == '\b') {
                    if (!sawDown) {
                        sawDown = true;
                        Gdx.app.log("BackSpaceTest", "DOWN via keyTyped");
                    }
                    sawPressed = true;
                    Gdx.app.log("BackSpaceTest", "PRESSED");
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.BACKSPACE) {
                    sawUp = true;
                    Gdx.app.log("BackSpaceTest", "UP");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);
        batch.begin();
        font.draw(batch, "DOWN: " + sawDown, 10, 70);
        font.draw(batch, "PRESSED: " + sawPressed, 10, 50);
        font.draw(batch, "UP: " + sawUp, 10, 30);
        font.draw(batch, "Hold and release BACKSPACE to test.", 10, 15);
        batch.end();
        // Only works in desktop app
        if (sawUp) {
            if (!sawDown) throw new AssertionError("BACKSPACE DOWN event not detected");
            if (!sawPressed) throw new AssertionError("BACKSPACE PRESSED events not detected");
            Gdx.app.log("BackSpaceTest", "All BACKSPACE events detected - test passed.");
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
