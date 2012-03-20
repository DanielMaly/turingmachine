package net.danielmaly.applications.turingmachine;

import javax.swing.*;

public class MachineDriver {
	
	private JFrame window = new MainFrame();
	private JPanel machinePanel;
	private JTable log;
	private JPanel programPanel;
	private JPanel commandPanel;
	
	/** Initializes the GUI.*/
	public void initialize() {
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new MachineDriver()).initialize();
				System.out.println("Application launched");
			}
		});
	
	}

}
