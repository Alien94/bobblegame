package com.gatedev.bobble.network.packet;

import com.gatedev.bobble.network.NetworkCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChangeKeyCommand extends NetworkCommand {

    public boolean nextState;
    public int key;

    public ChangeKeyCommand() {
    }

    public ChangeKeyCommand(int key, boolean nextState) {
        this.key = key;
        this.nextState = nextState;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        key = dis.readInt();
        nextState = dis.readBoolean();
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(key);
        dos.writeBoolean(nextState);
    }
}
