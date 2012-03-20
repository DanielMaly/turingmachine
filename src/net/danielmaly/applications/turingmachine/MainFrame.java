package net.danielmaly.applications.turingmachine;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	

	public MainFrame() {
		this.setTitle("Turing Machine Simulator");
		this.setSize(1024, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
