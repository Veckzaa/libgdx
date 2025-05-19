package com.badlogic.gdx.tests.gwt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GwtWheelEventTest extends GdxTest {
    SpriteBatch batch;
    BitmapFont  font;
    ShapeRenderer shapes;
    float lastX, lastY;
    float blockX;

    @Override
    public void create() {
        batch   = new SpriteBatch();
        font    = new BitmapFont();
        shapes  = new ShapeRenderer();
        blockX  = Gdx.graphics.getWidth() / 2f;

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override public boolean scrolled(float dx, float dy) {
                lastX = dx;
                lastY = dy;
                blockX += dx / 10;
                blockX = Math.max(0, Math.min(blockX, Gdx.graphics.getWidth() - 50));
                return true;
            }
            @Override public boolean keyDown(int keycode) { return false; }
            @Override public boolean keyUp(int keycode) { return false; }
            @Override public boolean keyTyped(char c) { return false; }
            @Override public boolean touchDown(int x,int y,int p,int b) { return false; }
            @Override public boolean touchUp(int x,int y,int p,int b) { return false; }
            @Override public boolean touchDragged(int x,int y,int p) { return false; }
            @Override public boolean mouseMoved(int x,int y) { return false; }
            @Override public boolean touchCancelled(int x,int y,int p,int b) { return false; }
        });
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.DARK_GRAY);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.CORAL);
        shapes.rect(blockX, Gdx.graphics.getHeight()/2f - 25, 50, 50);
        shapes.end();

        batch.begin();
        font.draw(batch, "Block moves horizontally with scroll", 20, 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapes.dispose();
    }
}
