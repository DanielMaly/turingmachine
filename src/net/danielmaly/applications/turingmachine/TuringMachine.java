package net.danielmaly.applications.turingmachine;

public class TuringMachine {
	
	private MasterState masterState = MasterState.HALTED;
	private Operation operation;
	
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
	
	
}
