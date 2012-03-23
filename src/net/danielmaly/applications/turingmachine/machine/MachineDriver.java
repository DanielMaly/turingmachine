package net.danielmaly.applications.turingmachine.machine;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.danielmaly.applications.turingmachine.editor.EditorFrame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class MachineDriver extends KeyAdapter implements ActionListener, ChangeListener {
	
	private TuringMachine machine = new TuringMachine();
	private EditorFrame editor;
	
	public final static String PROGRAM_NAME = "Turing Machine Simulator";
	public final static String FILE_EXTENSION = "tur";
	
	private MainFrame window = new MainFrame();
	private MachinePanel machinePanel;
	private LogTable log = new LogTable(machine);
	private ProgramPanel programPanel;
	private CommandPanel commandPanel;
	
	private int speed = 50;
	private final static int BASE_SPEED = 8000;
	
	/** Initializes the GUI.*/
	public void initialize() {
		
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		machinePanel = new MachinePanel(machine);
		programPanel = new ProgramPanel(this);
		commandPanel = new CommandPanel(this);
		editor = new EditorFrame(window, this);
		
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
		else if(e.getActionCommand().equals("Open editor")) {
			editor.setVisible(true);
		}
		else if(e.getActionCommand().equals("Load from file")) {
			this.load();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(commandPanel == null || programPanel == null) {
			return;
		}
		else if(e.getSource().equals(commandPanel.getSpeedSlider())) {
			this.speed = ((JSlider)e.getSource()).getValue();
		}
		else if(e.getSource().equals(commandPanel.getTapeSlider())) {
			this.machinePanel.setTapeSize(((JSlider)e.getSource()).getValue() / 100.0);
			machinePanel.repaint();
		}
		else if(e.getSource().equals(programPanel.getSpinner()) && machine.getMasterState() != MasterState.STARTED) {
			this.machine.setTapeIndex((Integer) ((JSpinner)e.getSource()).getValue());
			machinePanel.repaint();
		}
	}
	
	@Override 
	public void keyTyped(KeyEvent e) {
		if(machine.getMasterState() != MasterState.STARTED) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					initializeTape();
				}
			});
		}
	}
	
	public void load() {
		try {
			File file = MachineDriver.openFile();
			FileInputStream fos = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fos);
			in.readObject();

		
			try {
				this.loadIntoMachine((Program) in.readObject());
			} catch (EOFException e) {
				JOptionPane.showMessageDialog(null, "This file only contains an incomplete state table. Press OK to open the file in the editor");
				editor.setVisible(true);
				editor.openFile(file);
			}
			
		}
		catch(IOException ex) {
			JOptionPane.showMessageDialog(null, "There was an error opening the file.");
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "There was an error opening the file.");
			ex.printStackTrace();
		}
	}
	
	public void loadIntoMachine(Program p) {
		this.machine.setProgram(p);
		this.programPanel.setProgramName(p.getName());
		this.initializeTape();
	}
	
	public void initializeTape() {
		resetMachine();

		ArrayList<Character> newTape = new ArrayList<Character>();
		String initialTape = programPanel.getInitialTapeText();
		
		int boundary = Math.max(initialTape.length(), programPanel.getInitialTapeIndex());
		
		for(int i = 0; i < boundary; i++) {
			if( i < initialTape.length()) {
				newTape.add(new Character(initialTape.charAt(i)));
			}
			else newTape.add(new Character(' '));
		}
		
		machine.setTape(newTape);
		machine.setTapeIndex(programPanel.getInitialTapeIndex());
		machinePanel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					
				}
				(new MachineDriver()).initialize();
			}
		});
	
	}
	
	public static File openFile() {
		JFileChooser fc = new JFileChooser();

		fc.addChoosableFileFilter(new FileNameExtensionFilter(PROGRAM_NAME + " program (*." +FILE_EXTENSION+ ")", FILE_EXTENSION));
		fc.setAcceptAllFileFilterUsed(false);
		
		int retVal = fc.showOpenDialog(null);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File openFile = fc.getSelectedFile();
			return openFile;
		}
		else return null;
	}
	
	
	private class Animation implements Runnable {
		
		@Override
		public void run() {
			if(machine.getProgram() == null) {
				JOptionPane.showMessageDialog(null, "There is no program loaded into the machine!");
				return;
			}
			
			initializeTape();
			
			
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
				}
			}
			
			machinePanel.repaint();
			
		}
	}



}
