package net.danielmaly.applications.turingmachine;

import java.util.*;

public class TuringMachine {
	
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
	public String[] getEntry() {
		return currentTableEntry;
	}
	
	public int getTapeIndex() {
		return tapeIndex;
	}
	public void setTapeIndex(int tapeIndex) {
		this.tapeIndex = tapeIndex;
	}
	private char readFromTape() {
		operation = Operation.READING;

		if(tapeIndex >= tape.size() || tape.get(tapeIndex) == null) {
			tape.add(tapeIndex, ' ');
		}
		
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
	}
	
	public void performMove() {
		int direction = this.currentInstruction.getMove();
		operation = Operation.MOVING;
		tapeIndex += direction == Instruction.LEFT? -1 : 1;
		if(tapeIndex < 0) {
			masterState = MasterState.ERROR;
			throw new IndexOutOfBoundsException("Ran off start end of tape");
		}
		
		currentTableEntry[4] = direction == Instruction.LEFT? "L" : "R";
	}
	
	public void transitionState() {
		if(this.state == null) {
			this.state = "Start";
		}
		else {
			this.state = this.currentInstruction.getNextState();
		}
		operation = null;
	}
	
	public void performRead() throws IllegalProgramException {
		
		currentTableEntry = new String[5];
		currentTableEntry[0] = this.state;
		currentTableEntry[1] = this.tapeIndex + "";
		
		if(this.state.equals("Halt")) {
			this.halt();
			return;
		}
		
		try {
			this.currentInstruction = program.fetch(state, readFromTape());
			
		}
		catch(IllegalProgramException ex) {
			masterState = MasterState.ERROR;
			throw ex;
		}
		
	}
	
	public void halt() {
		this.setMasterState(MasterState.HALTED); 
		this.state = "Halt";
		this.currentTableEntry = new String[] {"Halt", "", "", "", ""};
	}
	
	public void start() {
		masterState = MasterState.STARTED;
	}
	
}
