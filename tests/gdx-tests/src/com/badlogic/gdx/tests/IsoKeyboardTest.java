
package com.badlogic.gdx.tests;

import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.tests.utils.GdxTest;

public class IsoKeyboardTest extends GdxTest {
	private boolean detected;

	@Override
	public void create () {
		Gdx.app.log("IntlBackslashTest", "Press the ISO backslash key (between Shift and Z) now");
		Gdx.input.setInputProcessor(this);
		detected = false;
	}

	@Override
	public void render () {
		if (detected) {
			Gdx.app.log("IntlBackslashTest", "Success: Keys.INTL_BACKSLASH was detected!");
			Gdx.app.exit();
		}
	}

	@Override
	public boolean keyDown (int keycode) {
		Gdx.app.log("IntlBackslashTest", "keyDown: " + keycode);
		if (keycode == Keys.INTL_BACKSLASH) {
			detected = true;
		}
		return false;
	}

	// All other methods are unused for this test:
	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (float amountX, float amountY) {
		return false;
	}
}
