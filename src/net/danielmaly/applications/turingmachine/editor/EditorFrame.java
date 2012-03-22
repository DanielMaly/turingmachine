package net.danielmaly.applications.turingmachine.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class EditorFrame extends JFrame implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private class StateTable extends JTable {
		
	}
	
	private class StateTableModel extends AbstractTableModel {
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class StateEntry {
		
		private String state;
		private char read;
		private char write;
		private int move;
		private String nextState;
		
		
	}


	
}
