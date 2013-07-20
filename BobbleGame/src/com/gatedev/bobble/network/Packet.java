package com.gatedev.bobble.network;

import com.gatedev.bobble.network.packet.ChangeKeyCommand;
import com.gatedev.bobble.network.packet.StartGamePacket;
import com.gatedev.bobble.network.packet.TurnPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {
	
	private static HashMap<Integer, Class<? extends Packet>> idToClass = new HashMap<Integer, Class<? extends Packet>>();
	private static Map<Class<? extends Packet>, Integer> classToId = new HashMap<Class<? extends Packet>, Integer>();
	
	private int getId() {
		return classToId.get(getClass());
	}
	
	static {
		setMap(10, StartGamePacket.class);
		setMap(11, TurnPacket.class);
		setMap(100, ChangeKeyCommand.class);
	}
	
	static void setMap(int id, Class<? extends Packet> cla) {
		if(idToClass.containsKey(id)) throw new IllegalArgumentException("Duplicate id "+id);
		if(classToId.containsKey(cla)) throw new IllegalArgumentException("Duplicate class");
		idToClass.put(id, cla);
		classToId.put(cla, id);
	}
	
	public static Packet readPacket(DataInputStream inputst) throws IOException {
		int id = 0;
        Packet packet = null;
        try {
        	id = inputst.read();
            if (id != 10 && id != 11 && id!=12 && id != 100) {
            	return null;
            }
            packet = getPacket(id);
            if (packet == null) throw new IOException("Wrong " + id);
            packet.read(inputst);
        } catch (EOFException e) {
            return null;
        }
        return packet;
	}
	
	public static void writePacket(Packet packet, DataOutputStream dos) throws IOException {
        dos.write(packet.getId());
        packet.write(dos);
    }
	
	public abstract void write(DataOutputStream output) throws IOException;
	
	public abstract void read(DataInputStream input) throws IOException;
	
	public void handle(PacketListener packetListener) {
		packetListener.handle(this);
	}
	
	public static Packet getPacket(int id) {
		try {
			Class<? extends Packet> clat = idToClass.get(id);
			if(clat == null) return null;
			return clat.newInstance();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
