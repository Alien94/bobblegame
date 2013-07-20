package com.gatedev.bobble.network;

import com.gatedev.bobble.network.packet.StartGamePacket;
import com.gatedev.bobble.network.packet.TurnPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Synchronizer {
	public static Random synchRandom = new Random();
	public static long startGameSeed;
	public static boolean seedSetted = false;
	private boolean isStarted;
	
	private final CommandListener commandListener;
	private TurnInfo[] turnInfo = new TurnInfo[TURN_QUEUE_LENGTH];
    private int commandSequence = TURN_QUEUE_LENGTH - 1;
    private int turnSequence = 0;
    private int currentTurnTickCount;
    public static final int TURN_QUEUE_LENGTH = 3;
    public static final int TICKS_PER_TURN = 3;

    private int currentTurnLength = TICKS_PER_TURN;

    private List<NetworkCommand> nextTurnCommands = new ArrayList<NetworkCommand>();
    private PlayerTurnCommands playerCommands;
    private final int numPlayers;
    private final PacketLink packetLink;
    private int localId;
	
	public Synchronizer(CommandListener commandListener, PacketLink packetLink, int localId, int numPlayers) {
		startGameSeed = synchRandom.nextLong();
		synchRandom.setSeed(startGameSeed);
		this.commandListener = commandListener;
        this.packetLink = packetLink;
        this.localId = localId;
        this.numPlayers = numPlayers;
        this.playerCommands = new PlayerTurnCommands(numPlayers);
        for (int i = 0; i < turnInfo.length; i++) {
            turnInfo[i] = new TurnInfo(i, numPlayers);
        }
        turnInfo[0].isDone = true;
        turnInfo[1].isDone = true;
	}
	
	public void setStartGameSeed(StartGamePacket packet) {
		setStarted(true);
		startGameSeed = packet.startGameSeed;
		synchRandom.setSeed(startGameSeed);
		seedSetted = true;
	}
	
	public synchronized boolean preTurn() {
        if (!isStarted) return false;
        int currentTurn = turnSequence % turnInfo.length;
        if (turnInfo[currentTurn].isDone || playerCommands.isAllDone(turnSequence)) {
            turnInfo[currentTurn].isDone = true;
            if (!turnInfo[currentTurn].isCommandsPopped) {
                turnInfo[currentTurn].isCommandsPopped = true;
                for (int i = 0; i < numPlayers; i++) {
                    List<NetworkCommand> commands = playerCommands.popPlayerCommands(i, turnSequence);
                    if (commands != null) {
                        for (NetworkCommand command : commands) {
                            commandListener.handle(i, command);
                        }
                    }
                }
            }
            return true;
        } else {
            //System.out.println("lag");
        }
        return false;
    }
	
	public synchronized void postTurn() {

        currentTurnTickCount++;
        if (currentTurnTickCount >= currentTurnLength) {
            int currentTurn = turnSequence % turnInfo.length;
            turnInfo[currentTurn].clearDone();
            turnInfo[currentTurn].turnNumber += TURN_QUEUE_LENGTH;
            turnSequence++;
            currentTurnTickCount = 0;
            playerCommands.addPlayerCommands(localId, commandSequence, nextTurnCommands);
            sendLocalTurn(turnInfo[commandSequence % turnInfo.length]);
            commandSequence++;
            nextTurnCommands = null;
        }
    }

    public synchronized void addCommand(NetworkCommand command) {
        if (nextTurnCommands == null) {
            nextTurnCommands = new ArrayList<NetworkCommand>();
        }
        nextTurnCommands.add(command);
    }

    private void sendLocalTurn(TurnInfo turnInfo) {
        if (packetLink != null) {
            packetLink.sendPacket(turnInfo.getLocalPacket(nextTurnCommands));
        }
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public synchronized void onTurnPacket(TurnPacket packet) {
    	playerCommands.addPlayerCommands(packet.playerId, packet.turnNumber, packet.list);
    }
	
	private class TurnInfo {
		
        public boolean isCommandsPopped;
        public boolean isDone;
        private int turnNumber;

        public TurnInfo(int turnNumber, int numPlayers) {
            this.turnNumber = turnNumber;
        }

        public void clearDone() {
            isDone = false;
            isCommandsPopped = false;
        }

        public TurnPacket getLocalPacket(List<NetworkCommand> localPlayerCommands) {
            return new TurnPacket(localId, turnNumber, localPlayerCommands);
        }
    }
}
