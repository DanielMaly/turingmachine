package net.danielmaly.applications.turingmachine;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommandPanel extends JPanel {
	
	public CommandPanel() {
		this.setPreferredSize(new Dimension(0,0));
		this.setBorder(new TitledBorder("Machine options"));
	}

}
