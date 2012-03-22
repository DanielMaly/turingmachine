package net.danielmaly.applications.turingmachine.machine;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CommandPanel extends JPanel {
	
	private JSlider speedSlider = new JSlider();
	private JLabel speedLabel = new JLabel("Speed:");
	
	private JSlider tapeSlider = new JSlider();
	private JLabel tapeLabel = new JLabel("Zoom: ");
	
	private JButton start = new JButton("Start");
	private JButton halt = new JButton("Halt");
	
	private MachineDriver driver;
	
	public CommandPanel(MachineDriver d) {
		this.driver = d;
		this.setPreferredSize(new Dimension(0,0));
		this.setBorder(new TitledBorder("Machine options"));
		this.setLayout(new GridLayout(4,1,10,10));
		
		start.addActionListener(driver);
		halt.addActionListener(driver);
		
		
		speedSlider.addChangeListener(driver);
		speedSlider.setMaximum(100);
		speedSlider.setMinimum(1);
		speedSlider.setValue(50);
		tapeSlider.addChangeListener(driver);
		tapeSlider.setMaximum(100);
		tapeSlider.setMinimum(30);
		tapeSlider.setValue(100);
		
		JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new FlowLayout());
		speedPanel.add(speedLabel);
		speedPanel.add(speedSlider);
		
		JPanel tapePanel = new JPanel();
		tapePanel.setLayout(new FlowLayout());
		tapePanel.add(tapeLabel);
		tapePanel.add(tapeSlider);
		
		this.add(speedPanel);
		this.add(tapePanel);
		this.add(start);
		this.add(halt);
		
	}
	
	public JSlider getSpeedSlider() {
		return speedSlider;
	}
	
	public JSlider getTapeSlider() {
		return tapeSlider;
	}

}
