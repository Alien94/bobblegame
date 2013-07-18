package com.gatedev.bobble.input;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
	public class Key {
        public int id;
		public boolean isPressed;
		public boolean wasPressed;
		public boolean nextState;
		
		public Key() {
            id = allKeys.size();
			allKeys.add(this);
		}
		
		public void tick() {
			wasPressed = isPressed;
			isPressed = nextState;
		}
	}
	
	private List<Key> allKeys = new ArrayList<Key>();
	public Key right = new Key();
	public Key left = new Key();
	public Key shoot = new Key();
	public Key pause = new Key();
	public Key resume = new Key();
	public Key exit = new Key();
	public Key next = new Key();
	public Key retry = new Key();
    public Key options = new Key();
    public Key back = new Key();

    public int keyBackTime = 0;
	
	public void tick() {
        if(keyBackTime>0) keyBackTime--;
		for(Key key : allKeys) {
            key.tick();
        }
	}
	
	public List<Key> getAll() {
        return allKeys;
    }
}
