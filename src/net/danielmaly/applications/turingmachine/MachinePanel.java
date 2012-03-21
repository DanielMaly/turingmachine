package net.danielmaly.applications.turingmachine;

import java.util.*;
import java.awt.*;

import javax.swing.JPanel;


public class MachinePanel extends JPanel implements Observer {
	
	private TuringMachine machine;
	
	public MachinePanel(TuringMachine machine) {
		this.machine = machine;
		this.setBackground(Color.WHITE);
	}
	
	

	@Override
	public void update(Observable o, Object arg1) {
		

	}
	
	@Override
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		
		Graphics2D g = (Graphics2D) gg;
		
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		int leftIndicatorX = 20;
		int leftIndicatorY = (int) (height * 0.07);
		int indicatorDescX = leftIndicatorX + 20;
		
		FontMetrics fm = g.getFontMetrics();
		
		g.drawOval(leftIndicatorX, leftIndicatorY, 20, 20);
		if(machine.getMasterState() == MasterState.STARTED) {
			g.setColor(Color.GREEN);
			g.fillOval(leftIndicatorX + 1, leftIndicatorY + 1, 19, 19);
			g.setColor(Color.BLACK);
		}
		g.drawString("Started", leftIndicatorX + 35, leftIndicatorY + 6 + fm.getHeight() / 2);
		
		g.drawOval(leftIndicatorX, leftIndicatorY + 25, 20, 20);
		if(machine.getMasterState() == MasterState.HALTED) {
			g.fillOval(leftIndicatorX + 1, leftIndicatorY + 26, 19, 19);
		}
		g.drawString("Halted", leftIndicatorX + 35, leftIndicatorY + 32 + fm.getHeight() / 2);
		
		g.drawOval(leftIndicatorX, leftIndicatorY + 50, 20, 20);
		if(machine.getMasterState() == MasterState.ERROR) {
			g.setColor(Color.RED);
			g.fillOval(leftIndicatorX + 1, leftIndicatorY + 51, 19, 19);
			g.setColor(Color.BLACK);
		}
		g.drawString("ERROR", leftIndicatorX + 35, leftIndicatorY + 57 + fm.getHeight() / 2);
		
		int rightIndicatorX = width - 175;
		g.drawOval(rightIndicatorX, leftIndicatorY, 20, 20);
		if(machine.getOperation() == Operation.MOVING) {
			g.setColor(Color.BLUE);
			g.fillOval(rightIndicatorX + 1, leftIndicatorY + 1, 19, 19);
			g.setColor(Color.BLACK);
		}
		g.drawString("Moving Tape", rightIndicatorX + 35, leftIndicatorY + 6 + fm.getHeight() / 2);
		
		g.drawOval(rightIndicatorX, leftIndicatorY + 25, 20, 20);
		if(machine.getOperation() == Operation.READING) {
			g.setColor(Color.BLUE);
			g.fillOval(rightIndicatorX + 1, leftIndicatorY + 26, 19, 19);
			g.setColor(Color.BLACK);
		}
		g.drawString("Reading", rightIndicatorX + 35, leftIndicatorY + 32 + fm.getHeight() / 2);
		
		g.drawOval(rightIndicatorX, leftIndicatorY + 50, 20, 20);
		if(machine.getOperation() == Operation.WRITING) {
			g.setColor(Color.BLUE);
			g.fillOval(rightIndicatorX + 1, leftIndicatorY + 51, 19, 19);
			g.setColor(Color.BLACK);
		}
		g.drawString("Writing", rightIndicatorX + 35, leftIndicatorY + 57 + fm.getHeight() / 2);
		
	}

}
