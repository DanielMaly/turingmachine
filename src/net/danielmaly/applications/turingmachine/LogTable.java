package net.danielmaly.applications.turingmachine;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
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
		
	}
	
	public void update() {
		model.addEntry(new Entry(machine.getEntry()));
		this.revalidate();
		this.scrollDown();
	}
	
	public void addErrorEntry() {
		model.addEntry(new Entry(new String[] {"ERROR", "", "", "", ""}));
		this.revalidate();
		this.scrollDown();
	}
	
	public void addHaltEntry() {
		model.addEntry(new Entry(new String[] {"Halt", "", "", "", ""}));
		this.revalidate();
		this.scrollDown();
	}
	
	public void removeAllEntries() {
		model = new LogTableModel();
		this.setModel(model);
		this.revalidate();
	}
	
	public void scrollDown() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollRectToVisible(getCellRect(getRowCount()-1, 0, true));
			}
		});
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
		
		private String state;
		private String read;
		private String written;
		private String moved;
		private String nextState;
		
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
