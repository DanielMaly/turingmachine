package net.danielmaly.applications.turingmachine;

import java.util.*;

public class TuringMachine extends Observable {
	
	private MasterState masterState = MasterState.HALTED;
	private Operation operation;
	private String state;
	private Instruction currentInstruction;
	
	private int tapeIndex = 0;
	private ArrayList<Character> tape = new ArrayList<Character> ();
	private Program program;
	
	public MasterState getMasterState() {
		return masterState;
	}
	public void setMasterState(MasterState masterState) {
		this.masterState = masterState;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public ArrayList<Character> getTape() {
		return tape;
	}
	public void setTape(ArrayList<Character> tape) {
		this.tape = tape;
	}
	
	private char readFromTape() {
		operation = Operation.READING;
		this.setChanged();
		this.notifyObservers();
		char readChar = tape.get(tapeIndex);
		
		return readChar;
	}
	
	public void performWrite() {
		operation = Operation.WRITING;
		tape.remove(tapeIndex);
		tape.add(tapeIndex, this.currentInstruction.getWriteSymbol());
		this.setChanged();
		this.notifyObservers();
	}
	
	public void performMove(int direction) {
		operation = Operation.MOVING;
		tapeIndex += direction == Instruction.LEFT? -1 : 1;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void transitionState(String newState) {
		this.state = newState;
		operation = null;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void performRead() throws IllegalProgramException {
		
		this.currentInstruction = program.fetch(state, readFromTape());
		
	}
	
}
