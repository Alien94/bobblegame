package com.gatedev.bobble.network.packet;

import com.gatedev.bobble.network.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StartGamePacket extends Packet {
	
	public long startGameSeed;
	
	public StartGamePacket() {
	}
	
	public StartGamePacket(long gameSeed) {
		this.startGameSeed = gameSeed;
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		output.writeLong(startGameSeed);
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		startGameSeed = input.readLong();
	}
}
