package net.danielmaly.applications.turingmachine.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;


import net.danielmaly.applications.turingmachine.machine.*;

public class EditorFrame extends JFrame implements ActionListener {
	
	private MachineDriver driver;
	
	public EditorFrame(MainFrame f, MachineDriver d) {
		this.driver = d;
		
		this.setLocation(new Point(f.getLocation().x + f.getWidth(), f.getLocation().y));
		this.setSize(400, f.getHeight());
		this.setTitle("Turing Machine Program Editor");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setJMenuBar(new MenuBar(this));
		
		this.add(new JScrollPane(new StateTable()));
		
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Program compile() {
		return null;
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
			
			for(Component item : this.getComponents()) {
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
			
			StateEntry startEntry = new StateEntry();
			startEntry.setState("Start");
			this.model.addEntry(startEntry);
			for(int i = 0; i < 5; i++) {
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
				case 1 : e.setRead(((String) a).charAt(1));
						 break;
				case 2 : e.setWrite(((String) a).charAt(1));
						 break;
				case 3 : e.setMove((String) a);
						 break;
				case 4 : e.setNextState((String) a);
						 break;
				default : throw new IllegalArgumentException();
			}
			
		}
		
	}
	
	private class StateEntry {

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


	
}
