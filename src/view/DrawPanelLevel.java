package view;

import model.Level;

import javax.swing.*;
import java.awt.*;

public class DrawPanelLevel extends JPanel {

	private Level l;

	public DrawPanelLevel(Level l) {
		this.setPreferredSize(new Dimension(Math.max(l.line() + 2, 6) * 120, Math.max(l.column() + 2, 6) * 120));
		this.l = l;
	}

	@Override
	protected void paintComponent(Graphics g) {
		background(g);
		foreground(g);
	}


	private void background(Graphics g) {

	}

	private void foreground(Graphics g) {

	}

	private void pipeMove(Graphics g) {

	}
}
