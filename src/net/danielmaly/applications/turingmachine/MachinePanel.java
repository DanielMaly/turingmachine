package net.danielmaly.applications.turingmachine;

import java.util.*;
import java.awt.*;

import javax.swing.JPanel;


public class MachinePanel extends JPanel {
	
	private TuringMachine machine;
	private int readAnimationState = 0;
	private int moveAnimationState = 0;
	private int writeAnimationState = 0;
	private double tapeSize = 1;
	
	public final static int READ_ANIMATION = 0;
	public final static int WRITE_ANIMATION = 1;
	public final static int MOVE_ANIMATION = 2;
	
	public MachinePanel(TuringMachine machine) {
		this.machine = machine;
		this.setBackground(Color.WHITE);
	}
	
	public void incrementAnimation(int animation) {
		if(animation == READ_ANIMATION) {
			readAnimationState = (readAnimationState + 1) % 24;
		}
		else if(animation == WRITE_ANIMATION) {
			writeAnimationState = (writeAnimationState + 1) % 24;
		}
		else if(animation == MOVE_ANIMATION) {
			moveAnimationState = (moveAnimationState + 1) % 24;
		}
		
	}
	
	public void setTapeSize(double d) {
		this.tapeSize = d;
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
	
		final int SQUARE_SIDE = (int) (0.445 * height * tapeSize);
		
		int leftIndicatorX = 20;
		int leftIndicatorY = (int) (height * 0.07);
		
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
		
		
		
		int remainingVerticalSpace = height - leftIndicatorY - 105;
		int centerOfTapeY = leftIndicatorY + 85 + (remainingVerticalSpace / 2);
		int topTapeY = centerOfTapeY - (SQUARE_SIDE / 2);
		int centerSquareX = (width / 2) - (SQUARE_SIDE / 2);
		int startTapeX = leftIndicatorX + 35;
		
		
		g.drawRoundRect(startTapeX, topTapeY, SQUARE_SIDE, SQUARE_SIDE, 10, 10);
		
		//DRAW SCANNER
		g.setStroke(new BasicStroke(4.0f));
		g.drawRoundRect(centerSquareX, topTapeY, SQUARE_SIDE, SQUARE_SIDE, 10, 10);
		
	}

}
