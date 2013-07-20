package com.gatedev.bobble.network;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Networking implements PacketLink {
	private static final int BUFFER_SIZE = 1024*5;
	private List<Packet> incoming = Collections.synchronizedList(new ArrayList<Packet>());
	private List<Packet> outgoing = Collections.synchronizedList(new ArrayList<Packet>());
	
	private DataInputStream input;
	private DataOutputStream output;
	
	private Thread readThread;
	private Thread writeThread;
	
	private Object writeLock = new Object();
	
	private boolean running = true;
	
	public PacketListener packetListener;
	
	public Networking(Socket socketR) throws IOException {
		Socket socket = socketR;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE));
		
		readThread = new Thread("Read thread") {
            public void run() {
                try {
                    while (running) {
                        while (read())
                            ;
                        try {
                        	sleep(2L);
                        } catch(Exception ex) {}
                    }
                } catch (Exception e) {
                }
            }
        };
		
		writeThread = new Thread() {
			public void run() {
				while(running) {
					while(write()) ;
					try {
						if(output != null) output.flush();
					} catch(Exception ex) {}
					try {
                        sleep(2L);
                    } catch (InterruptedException e) {
                    }
				}
			}
		};
		readThread.start();
		writeThread.start();
	}
	
	public void sendPacket(Packet packet) {
		synchronized(writeLock) {
			outgoing.add(packet);
		}
	}
	
	public void tick() {
		if(!incoming.isEmpty()) {
			Packet packet = null;
			packet = incoming.remove(0);
			if(packet!=null) packet.handle(packetListener);
		}
	}
	
	private boolean read() {
		boolean done = false;
		try {
			Packet packet = Packet.readPacket(input);
			if(packet != null) {
				incoming.add(packet);
				done = true;
			}
		} catch(Exception ex) {}
		return done;
	}
	
	private boolean write() {
		boolean done = false;
		try {
			if(!outgoing.isEmpty()) {
				Packet packet = null;
				synchronized(writeLock) {
					packet = outgoing.remove(0);
				}
				Packet.writePacket(packet, output);
				done = true;
			}
		} catch(Exception ex) {}
		return done;
	}

	@Override
	public void setPacketListener(PacketListener packetListener) {
        this.packetListener = packetListener;
    }
}
