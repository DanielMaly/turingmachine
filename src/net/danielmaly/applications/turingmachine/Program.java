package net.danielmaly.applications.turingmachine;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {

	private ArrayList<Instruction> stateTable = new ArrayList<Instruction> ();
	private String name;
	
	public Program(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addInstruction(Instruction i) {
		stateTable.add(i);
	}
	
	public Instruction fetch (String state, char read) throws IllegalProgramException {
		
		Instruction fetched = null;
		
		for(int i = 0; i < stateTable.size(); i++) {
			Instruction n = stateTable.get(i);
			if(state.equals(n.getState()) && read == n.getReadSymbol()) {
				
				if(fetched == null) 
					fetched = n;
				else throw new IllegalProgramException("Conflicting entries found in the state table");
			}
		}
		
		if(fetched == null) {
			throw new IllegalProgramException("Couldn't find matching entry in the state table");
		}
		else return fetched;
	}
	
	public static Program getDummyProgram() {
		Program p = new Program("Dummy");
		Instruction one = new Instruction("Start", '0', '0', Instruction.RIGHT, "Start");
		Instruction two = new Instruction("Start", '1', '1', Instruction.RIGHT, "Start");
		Instruction three = new Instruction("Start", ' ', ' ', Instruction.LEFT, "1");
		Instruction four = new Instruction("1", '0', '1', Instruction.RIGHT, "Start");
		Instruction five = new Instruction("1", '1', '0', Instruction.LEFT, "1");
		Instruction six = new Instruction("1", ' ', '1', Instruction.RIGHT, "Halt");
		
		p.addInstruction(one);
		p.addInstruction(two);
		p.addInstruction(three);
		p.addInstruction(four);
		p.addInstruction(five);
		p.addInstruction(six);
		
		return p;
	}
}
