package net.danielmaly.applications.turingmachine;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;


public class MachineDriver implements Observer, ActionListener {
	
	private TuringMachine machine = new TuringMachine();
	
	private JFrame window = new MainFrame();
	private MachinePanel machinePanel;
	private LogTable log = new LogTable(machine);
	private JPanel programPanel;
	private JPanel commandPanel;
	
	/** Initializes the GUI.*/
	public void initialize() {
		
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		machinePanel = new MachinePanel(machine);
		programPanel = new ProgramPanel(this);
		commandPanel = new CommandPanel(this);
		
		JSeparator separator = new JSeparator();
		separator.setOpaque(false);
		
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
		logPane.getViewport().setBackground(programPanel.getBackground());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.25;
		c.weighty = 0.4;
		c.insets = new Insets(8,2,2,2);
		
		window.add(logPane, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 0;
		window.add(separator, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.3;
		c.weighty = 0.4;
		
		window.add(programPanel, c);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 0.4;
		
		window.add(commandPanel, c);
		
		window.validate();
	}
		


	@Override
	public void update(Observable o, Object arg) {
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
