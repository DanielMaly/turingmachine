package net.danielmaly.applications.turingmachine;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;


public class MachineDriver implements Observer {
	
	private TuringMachine machine = new TuringMachine();
	
	private JFrame window = new MainFrame();
	private MachinePanel machinePanel = new MachinePanel();
	private LogTable log = new LogTable(machine);
	private JPanel programPanel = new JPanel();
	private JPanel commandPanel = new JPanel();
	
	/** Initializes the GUI.*/
	public void initialize() {
		
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill= GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 0.6;
		window.add(machinePanel, c);
		
		JScrollPane logPane = new JScrollPane(log);
		logPane.setPreferredSize(new Dimension(0,0));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.4;
		c.insets = new Insets(8,2,2,0);
		
		window.add(logPane, c);
		
		programPanel.setBorder(new TitledBorder("Program options"));
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 0.4;
		
		window.add(programPanel, c);
		
		commandPanel.setBorder(new TitledBorder("Machine options"));
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 0.4;
		
		window.add(commandPanel, c);
		
		window.validate();
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new MachineDriver()).initialize();
				System.out.println("Application launched");
			}
		});
	
	}


	@Override
	public void update(Observable o, Object arg) {
		
		
	}

}
