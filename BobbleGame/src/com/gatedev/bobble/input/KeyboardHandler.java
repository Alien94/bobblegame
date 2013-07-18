package com.gatedev.bobble.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.gatedev.bobble.Renderer;
import com.gatedev.bobble.entity.Arrow;

import java.util.HashMap;
import java.util.Map;

public class KeyboardHandler implements InputProcessor {
	private Map<Integer, Keyboard.Key> mapper = new HashMap<Integer, Keyboard.Key>();
    private Arrow arrow;
	
	public KeyboardHandler(Keyboard keyboard, Arrow arrow) {
		mapper.put(Keys.D, keyboard.right);
		mapper.put(Keys.A, keyboard.left);
		mapper.put(Keys.SPACE, keyboard.shoot);
		mapper.put(Keys.P, keyboard.pause);
		mapper.put(Keys.O, keyboard.resume);
		mapper.put(Keys.E, keyboard.exit);
		mapper.put(Keys.N, keyboard.next);
		mapper.put(Keys.R, keyboard.retry);
        mapper.put(Keys.K, keyboard.options);
        mapper.put(4, keyboard.back);
        this.arrow = arrow;
	}
	
	@Override
	public boolean keyDown(int keycode) {
        Keyboard.Key key = mapper.get(keycode);
		if(key!=null) key.nextState = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		Keyboard.Key key = mapper.get(keycode);
		if(key!=null) key.nextState = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 vec = new Vector3(screenX, screenY, 0);
		Renderer.guiCamera.unproject(vec);
        int angle = (int)Math.abs(Math.toDegrees(Math.atan2(vec.y+288, vec.x)));
        arrow.setDegrees(angle);
        mapper.get(Keys.SPACE).nextState = true;
        //System.out.println("Touched x:"+vec.x+"  y:"+vec.y+"  degrees:"+angle+"  atan:"+Math.atan2(vec.y+288, vec.x));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mapper.get(Keys.SPACE).nextState = false;
		mapper.get(Keys.E).nextState = false;
		mapper.get(Keys.N).nextState = false;
        mapper.get(Keys.R).nextState = false;
        mapper.get(Keys.K).nextState = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
