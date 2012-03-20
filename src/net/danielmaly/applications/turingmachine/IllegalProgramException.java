package net.danielmaly.applications.turingmachine;

public class IllegalProgramException extends Exception {

	public IllegalProgramException() {
		this("Illegal program");
	}

	public IllegalProgramException(String arg0) {
		super(arg0);
	}



}
