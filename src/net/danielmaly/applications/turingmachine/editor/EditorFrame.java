package net.danielmaly.applications.turingmachine.editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;


import net.danielmaly.applications.turingmachine.machine.*;

public class EditorFrame extends JFrame implements ActionListener {
	
	private MachineDriver driver;
	private StateTable table = new StateTable();
	private String currentProgramName;
	
	public EditorFrame(MainFrame f, MachineDriver d) {
		this.driver = d;
		
		this.setLocation(new Point(f.getLocation().x + f.getWidth(), f.getLocation().y));
		this.setSize(400, f.getHeight());
		this.setTitle("Turing Machine Program Editor");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setJMenuBar(new MenuBar(this));
		
		this.add(new JScrollPane(table));
		
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Save")) {
			try {
				this.save(this.compile());
			}
			catch(IllegalProgramException ex) {
				JOptionPane.showMessageDialog(null, "Compilation failed: " + ex.getMessage() + ". Saving source only.");
				this.save(null);
			}
		}
		else if(e.getActionCommand().equals("New program")) {
			this.currentProgramName = null;
			this.table.clear();
		}
		else if(e.getActionCommand().equals("Open")) {
			this.openFile();
		}
		else if(e.getActionCommand().equals("Load into machine")) {
			try {
				Program p = this.compile();
				this.driver.loadIntoMachine(p);
			}
			catch(IllegalProgramException ex) {
				JOptionPane.showMessageDialog(null, "Compilation failed: " + ex.getMessage());
			}
		}
		
	}
	
	public Program compile() throws IllegalProgramException {
		ArrayList<StateEntry> entries = table.getEntries();
		String name = currentProgramName == null ? JOptionPane.showInputDialog(null, "Enter a name for your program") : currentProgramName;
		this.currentProgramName = name;
		Program p = new Program(name);
		
		for(int i = 0; i < entries.size(); i++) {
			StateEntry e = entries.get(i);
			if(e.getState() != null) {
				char r = e.getRead() == '\u0000' ? ' ' : e.getRead();
				char w = e.getWrite() == '\u0000' ? ' ' : e.getWrite();
				if(e.getNextState() == null || e.getMove() == null) {
					throw new IllegalProgramException("Blank fields in entry " + e.toString());
				}
				p.addInstruction(new Instruction(e.getState(), r, w, e.getMoveAsInt(), e.getNextState()));
			}
		}
		return p;
	}
	
	public void save(Program p) {
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter(MachineDriver.PROGRAM_NAME + " program (*." +
				MachineDriver.FILE_EXTENSION+ ")", MachineDriver.FILE_EXTENSION));
		int retVal = fc.showSaveDialog(null);


		if (retVal == JFileChooser.APPROVE_OPTION) {
			String filePath = fc.getSelectedFile().getAbsolutePath();
			if(filePath.contains(MachineDriver.FILE_EXTENSION)) {
				filePath = filePath.substring(0, filePath.length() - 4);
			}
			File saveFile = new File(filePath + "." + MachineDriver.FILE_EXTENSION);
			if (saveFile.exists()) {
				int choice = JOptionPane.showConfirmDialog(null,"This file already exists. Do you want to overwrite it?",
								"Warning!", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if(choice == JOptionPane.NO_OPTION) 
					return;
			}
			try {
				if (saveFile.isFile())
					saveFile.createNewFile();
				
				FileOutputStream fos = new FileOutputStream(saveFile);
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(((StateTableModel)table.getModel()).getEntries());
				if(p != null) out.writeObject(p);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void openFile(File file) {
		
		try {		
			FileInputStream fos = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fos);
			this.table.setEntries((ArrayList<StateEntry>) in.readObject());

		
			try {
				this.currentProgramName = ((Program) in.readObject()).getName();
			} catch (EOFException e) {
			
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
	
	public void openFile() {
		File file = MachineDriver.openFile();
		openFile(file);
	}
	
	
	private class MenuBar extends JMenuBar {
		private JMenu file = new JMenu("File");
		private int shortCutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		public MenuBar(ActionListener listener) {
			this.add(file);
			
			JMenuItem newp = new JMenuItem("New program");
			JMenuItem save = new JMenuItem("Save");
			JMenuItem open = new JMenuItem("Open");
			JMenuItem load = new JMenuItem("Load into machine");
			
			newp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortCutKey));
			save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortCutKey));
			open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortCutKey));
			load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, shortCutKey));
			
			file.add(newp);
			file.add(open);
			file.add(save);
			file.add(new JSeparator());
			file.add(load);
			
			for(Component item : file.getMenuComponents()) {
				if(item instanceof JMenuItem) {
					((JMenuItem)item).addActionListener(listener);
				}
			}
		}
	}
	
	private class StateTable extends JTable {
		
		private StateTableModel model;
		
		public StateTable() {
			this.model = new StateTableModel(this);
			this.setModel(model);
			this.setBackground(Color.LIGHT_GRAY);
			this.reset();
					
		}
		
		public ArrayList<StateEntry> getEntries() {
			return model.getEntries();
		}
		
		public void setEntries(ArrayList<StateEntry> entries) {
			this.model.clear();
			for(int i = 0; i < entries.size(); i++) {
				this.model.addEntry(entries.get(i));
			}
			this.revalidate();
		}
		
		public void clear() {
			this.model.clear();
			this.reset();
		}
		
		private void reset() {
			StateEntry startEntry = new StateEntry();
			startEntry.setState("Start");
			this.model.addEntry(startEntry);
			
			for(int i = 0; i < 50; i++) {
				this.model.addEntry(new StateEntry());
			}
			this.revalidate();
		}
	
	}
	
	private class StateTableModel extends AbstractTableModel {
		
		private final String[] META = {"State", "Read", "Write", "Move", "Next state"};
		private ArrayList<StateEntry> entries = new ArrayList<StateEntry> ();
		private StateTable parent;
		
		public StateTableModel(StateTable parent) {
			this.parent = parent;
		}
		
		public ArrayList<StateEntry> getEntries() {
			return entries;
		}
		
		public void clear() {
			entries.clear();
		}
		
		public void addEntry(StateEntry e) {
			entries.add(e);
			parent.revalidate();
		}
		
		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public int getRowCount() {
			return entries.size();
		}

		@Override
		public Object getValueAt(int x, int y) {
			StateEntry e = entries.get(x);
			
			switch(y) {
				case 0 : return e.getState();
				case 1 : return e.getRead() + "";
				case 2 : return e.getWrite() + "";
				case 3 : return e.getMove();
				case 4 : return e.getNextState();
				default : throw new IllegalArgumentException();
			}
		}
		
		@Override
		public boolean isCellEditable(int x, int y) {
			return x != 0 || y != 0 ? true : false;
		}
		
		@Override
		public String getColumnName(int i) {
			return META[i];
		}
		
		@Override
		public void setValueAt(Object a, int x, int y) {
			if(x == entries.size() - 1)
				this.addEntry(new StateEntry());
			StateEntry e = entries.get(x);
			switch(y) {
				case 0 : e.setState((String) a);
						 break;
				case 1 : try {
							String s = ((String) a).trim().equals("") ? " " : ((String) a).trim();
							e.setRead(s.charAt(0));
						 }
						 catch(Exception ex) {
							 
						 }
						 break;
				case 2 : try {
							String s = ((String) a).trim().equals("") ? " " : ((String) a).trim();
							e.setWrite(s.charAt(0));
						 }
						 catch(Exception ex) {
							 
						 }
						 break;
				case 3 : e.setMove((String) a);
						 break;
				case 4 : e.setNextState((String) a);
						 break;
				default : throw new IllegalArgumentException();
			}
			
		}
		
	}
}
	
 class StateEntry implements Serializable {

		private String state;
		private char read;
		private char write;
		private String move;
		private String nextState;
		
		public StateEntry () {	

		}
		
		public String getState() {
			return state;
		}
		
		public void setState(String s) {
			this.state = s;
		}

		public char getRead() {
			return read;
		}
		
		public void setRead(char ch) {
			this.read = ch;
		}

		public char getWrite() {
			return write;
		}
		
		public void setWrite(char ch) {
			this.write = ch;
		}
		
		public String getMove() {
			return this.move;
		}
		
		public void setMove(String m) {
			this.move = m;
		}

		public int getMoveAsInt() throws IllegalProgramException {
			String entered = this.move.toLowerCase();
			if(entered.equals("right") || entered.equals("r")) {
				return Instruction.RIGHT;
			}
			else if(entered.equals("left") || entered.equals("l")) {
				return Instruction.LEFT;
			}
			else throw new IllegalProgramException("Unrecognized move direction in entry " + this.toString());
		}

		public String getNextState() {
			return nextState;
		}
		
		public void setNextState(String s) {
			this.nextState = s;
		}
		
		public String toString() {
			return ("(" + this.state + ", " + this.read + ")");
		}
		
}


	

