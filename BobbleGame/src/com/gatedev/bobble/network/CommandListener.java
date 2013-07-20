package com.gatedev.bobble.network;


public interface CommandListener {
    public void handle(int playerId, NetworkCommand packet);
}
