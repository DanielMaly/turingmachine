package net.danielmaly.applications.turingmachine.machine;

public class Instruction implements java.io.Serializable {

	public final static int RIGHT = 0;
	public final static int LEFT = 1;
	
	private String state;
	private char readSymbol;
	private char writeSymbol;
	private int move;
	private String nextState;
	
	public Instruction(String state, char readSymbol, char writeSymbol, int move, String nextState) {
		
		this.state = state;
		this.readSymbol = readSymbol;
		this.writeSymbol = writeSymbol;
		this.move = move;
		this.nextState = nextState;
		
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public char getReadSymbol() {
		return readSymbol;
	}

	public void setReadSymbol(char readSymbol) {
		this.readSymbol = readSymbol;
	}

	public char getWriteSymbol() {
		return writeSymbol;
	}

	public void setWriteSymbol(char writeSymbol) {
		this.writeSymbol = writeSymbol;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public String getNextState() {
		return nextState;
	}

	public void setNextState(String nextState) {
		this.nextState = nextState;
	}
}
