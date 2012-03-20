package net.danielmaly.applications.turingmachine;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {

	private ArrayList<Instruction> stateTable = new ArrayList<Instruction> ();
	
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
}
