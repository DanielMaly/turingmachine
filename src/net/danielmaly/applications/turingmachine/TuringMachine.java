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
	
	private String[] currentTableEntry = new String[5];
	
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
		
		currentTableEntry[2] = "" + readChar;
		
		return readChar;
	}
	
	public void performWrite() {
		operation = Operation.WRITING;
		if (tapeIndex < tape.size()) {
			tape.remove(tapeIndex);
			tape.add(tapeIndex, this.currentInstruction.getWriteSymbol());
		}
		else tape.add(this.currentInstruction.getWriteSymbol());
		
		currentTableEntry[3] = "" + this.currentInstruction.getWriteSymbol();
		
		this.setChanged();
		this.notifyObservers("writeFinished");
	}
	
	public void performMove(int direction) {
		operation = Operation.MOVING;
		tapeIndex += direction == Instruction.LEFT? -1 : 1;
		if(tapeIndex < 0) {
			masterState = MasterState.ERROR;
			throw new IndexOutOfBoundsException("Ran off start end of tape");
		}
		
		currentTableEntry[4] = direction == Instruction.LEFT? "L" : "R";
		
		this.setChanged();
		this.notifyObservers("moveFinished");
	}
	
	public void transitionState(String newState) {
		this.state = newState;
		operation = null;
		
		this.setChanged();
		this.notifyObservers("stateChanged");
	}
	
	public void performRead() throws IllegalProgramException {
		
		currentTableEntry = new String[5];
		currentTableEntry[0] = this.state;
		currentTableEntry[1] = this.tapeIndex + "";
		
		if(this.state.equals("Halt")) {
			masterState = MasterState.HALTED;
			this.setChanged();
			this.notifyObservers("readFinished");
		}
		
		try {
			this.currentInstruction = program.fetch(state, readFromTape());
			
		}
		catch(IllegalProgramException ex) {
			masterState = MasterState.ERROR;
			throw ex;
		}
		
	}
	
	public void start() {
		this.state = "Start";
		masterState = MasterState.STARTED;
		this.setChanged();
		this.notifyObservers("started");
	}
	
}
