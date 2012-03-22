package net.danielmaly.applications.turingmachine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;


public class MachineDriver implements ActionListener, ChangeListener {
	
	private TuringMachine machine = new TuringMachine();
	
	private JFrame window = new MainFrame();
	private MachinePanel machinePanel;
	private LogTable log = new LogTable(machine);
	private ProgramPanel programPanel;
	private CommandPanel commandPanel;
	
	private int speed = 50;
	private final static int BASE_SPEED = 4000;
	
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
	
	public void resetMachine() {
		log.removeAllEntries();
		machine.reset();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			new Thread(new Animation()).start();
		}
		else if(e.getActionCommand().equals("Halt")) {
			machine.setMasterState(MasterState.HALTED);
		}
		else if(e.getActionCommand().equals("New program")) {
			machine.setProgram(Program.getDummyProgram());
			programPanel.setProgramName(Program.getDummyProgram().getName());
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(commandPanel == null) {
			return;
		}
		else if(e.getSource().equals(commandPanel.getSpeedSlider())) {
			this.speed = ((JSlider)e.getSource()).getValue();
		}
		else if(e.getSource().equals(commandPanel.getTapeSlider())) {
			this.machinePanel.setTapeSize(((JSlider)e.getSource()).getValue() / 100.0);
			machinePanel.repaint();
		}
		
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new MachineDriver()).initialize();
				System.out.println("Application launched");
			}
		});
	
	}
	
	private class Animation implements Runnable {
		
		@Override
		public void run() {
			if(machine.getProgram() == null) {
				JOptionPane.showMessageDialog(null, "There is no program loaded into the machine!");
				return;
			}
			resetMachine();
			ArrayList<Character> newTape = new ArrayList<Character>();
			String initialTape = programPanel.getInitialTapeText();
			for(int i = 0; i < initialTape.length(); i++) {
				newTape.add(new Character(initialTape.charAt(i)));
			}
			
			machine.setTape(newTape);
			machine.setTapeIndex(programPanel.getInitialTapeIndex());
			machine.start();
			machinePanel.repaint();
			
			while(machine.getMasterState() == MasterState.STARTED) {
				try {
					machine.transitionState();
					machine.performRead();
					if(machine.getMasterState() == MasterState.HALTED)
						break;
					this.animateRead();
					
					machine.performWrite();
					this.animateWrite();
					
					machine.performMove();
					this.animateMove();
					
					log.update();
					log.repaint();
				}
				catch(IllegalProgramException ex) {
					machine.setMasterState(MasterState.ERROR);
					ex.printStackTrace();
				}
				catch(IndexOutOfBoundsException ex) {
					machine.setMasterState(MasterState.ERROR);
					ex.printStackTrace();
				}
				
				finally {
					machinePanel.repaint();
				}
				
			}
			if(machine.getMasterState() == MasterState.HALTED) {
				log.addHaltEntry();
			}
			else if (machine.getMasterState() == MasterState.ERROR) {
				log.addErrorEntry();
			}
			machinePanel.repaint();
		}
		
		public void animateRead() {
			int sleepFor = BASE_SPEED / speed / MachinePanel.READ_ANIMATION_STEPS;
			
			machinePanel.repaint();
			for (int i = 0; i < MachinePanel.READ_ANIMATION_STEPS; i++) {
				machinePanel.incrementAnimation(MachinePanel.READ_ANIMATION);
				machinePanel.repaint();
				try {
					Thread.sleep(sleepFor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void animateWrite() {
			int sleepFor = BASE_SPEED / speed / MachinePanel.WRITE_ANIMATION_STEPS;
			
			machinePanel.repaint();
			for (int i = 0; i < MachinePanel.WRITE_ANIMATION_STEPS; i++) {
				machinePanel.incrementAnimation(MachinePanel.WRITE_ANIMATION);
				machinePanel.repaint();
				try {
					Thread.sleep(sleepFor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void animateMove() {
			int sleepFor = BASE_SPEED / speed / MachinePanel.MOVE_ANIMATION_STEPS;
			
			machinePanel.repaint();
			for (int i = 0; i < MachinePanel.MOVE_ANIMATION_STEPS; i++) {
				machinePanel.incrementAnimation(MachinePanel.MOVE_ANIMATION);
				machinePanel.repaint();
				try {
					Thread.sleep(sleepFor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			machinePanel.repaint();
			
		}
	}



}
