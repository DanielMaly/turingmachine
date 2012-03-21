package net.danielmaly.applications.turingmachine;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommandPanel extends JPanel {
	
	private JSlider speedSlider = new JSlider();
	private JLabel speedLabel = new JLabel("Speed:");
	
	private JButton start = new JButton("Start");
	private JButton halt = new JButton("Halt");
	
	private MachineDriver driver;
	
	public CommandPanel(MachineDriver d) {
		this.driver = d;
		this.setPreferredSize(new Dimension(0,0));
		this.setBorder(new TitledBorder("Machine options"));
		this.setLayout(new GridLayout(3,1,10,10));
		
		JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new FlowLayout());
		speedPanel.add(speedLabel);
		speedPanel.add(speedSlider);
		this.add(speedPanel);
		this.add(start);
		this.add(halt);
		
	}

}
