package net.danielmaly.applications.turingmachine;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.util.ArrayList;

public class LogTable extends JTable {
	
	
	
	private LogTableModel model = new LogTableModel();
	private TuringMachine machine;
	
	public LogTable(TuringMachine machine) {
		this.setModel(model);
		this.machine = machine;
		
		
		//DEBUG
		this.setBackground(Color.LIGHT_GRAY);
		model.addEntry(new Entry(new String[]{"Blah", "158","1", "0", "L"}));
	}
	
	public void update() {
		
	}
	
	
	private class LogTableModel extends AbstractTableModel {
		
		private final String[] META = {"State", "Index", "Read", "Wrote", "Moved"};
		private ArrayList<Entry> entries = new ArrayList<Entry> ();

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
			return entries.get(x).getValues()[y];
		}
		
		@Override
		public String getColumnName(int index) {
			return META[index];
		}
		
		public void addEntry(Entry e) {
			entries.add(e);
		}
		
	}
	
	
	private class Entry {
		
		String state;
		String read;
		String written;
		String moved;
		String nextState;
		
		public Entry(String[] strings) {
			state = strings[0];
			read = strings[1];
			written = strings[2];
			moved = strings[3];
			nextState = strings[4];
		}
		
		public String[] getValues() {
			String[] values = {state, read, written, moved, nextState};
			return values;
		}
	}

}
