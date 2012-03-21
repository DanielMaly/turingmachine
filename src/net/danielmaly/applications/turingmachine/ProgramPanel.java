package net.danielmaly.applications.turingmachine;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ProgramPanel extends JPanel {
	
	private JLabel loaded = new JLabel("No program loaded");
	private JLabel starting = new JLabel("Starting tape characters");
	private JLabel position = new JLabel("Starting tape position");
	
	private JTextField characters = new JTextField();
	private JSpinner positionSpin = new JSpinner(new SpinnerNumberModel() {
		
		public Comparable<Integer> getMinimum() {
			return 0;
		}
		
		public Comparable<Integer> getMaximum() {
			return Integer.MAX_VALUE;
		}
		
		/* Fix a bug in Java allowing the Spinner to exceed minimum / maximum by 1 and stop working*/
		public Object getPreviousValue() {
			if((Integer) super.getPreviousValue() < (Integer) (getMinimum()))
				return null;
			else return super.getPreviousValue();
		}
		
		public Object getNextValue() {
			if((Integer) super.getNextValue() > (Integer) (getMaximum()))
				return null;
			else return super.getNextValue();
		}
		
		
	});
	
	private JButton load = new JButton("Load from file");
	private JButton newp = new JButton("New program");
	
	public ProgramPanel() {
		this.setPreferredSize(new Dimension(0,0));
		this.setBorder(new TitledBorder("Program options"));
		this.setLayout(new GridBagLayout());
	
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.5;
		c.weightx = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.gridwidth = 2;
		this.add(loaded, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		this.add(starting, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0.5;
		this.add(characters, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 0;
		this.add(position, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0.5;
		this.add(positionSpin, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(20,0,0,0);
		this.add(load, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(20,0,0,0);
		this.add(newp, c);
		
		positionSpin.setValue(new Integer(0));
		
		
	}

}
