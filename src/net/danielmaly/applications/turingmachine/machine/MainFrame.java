package net.danielmaly.applications.turingmachine.machine;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	

	public MainFrame() {
		this.setTitle("Turing Machine Simulator");
		this.setSize(1024, 600);
		this.setMinimumSize(new java.awt.Dimension(1000,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
