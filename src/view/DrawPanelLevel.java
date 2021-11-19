package view;

import model.Level;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class DrawPanelLevel extends JPanel {

	private Level l;
	private BufferedImage background;

	public DrawPanelLevel(Level l) {
		int w = (l.column() + 4) * 120;
		int h = Math.max(l.line() + 2, 6) * 120;

		this.setPreferredSize(new Dimension(w, h));
		this.l = l;

		// Création de l'image de fond
		this.background = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = background.createGraphics();

		int xpipes = w - 240;
		int ypipes = 0;

		BufferedImage tile = Texture.getTextureTile(Special.DARKBROWN);
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);

		// Dessin du fond la barre à tuyaux
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				g.drawImage(tile, xpipes, ypipes, null);
				ypipes += 120;
			}
			xpipes += 120;
			ypipes = 0;
		}

		// Dessin des coins
		xpipes = 0;
		ypipes = 0;
		tile = Texture.getTextureTile(Special.CORNER);
		g.drawImage(tile, xpipes, ypipes, null);

		xpipes += (l.column() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.EAST);
		g.drawImage(tile, xpipes, ypipes, null);

		xpipes = 0;
		ypipes += (l.line() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.WEST);
		g.drawImage(tile, xpipes, ypipes, null);

		xpipes += (l.column() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.SOUTH);
		g.drawImage(tile, xpipes, ypipes, null);

		// Dessin des bordures
		xpipes = 120;
		ypipes = 0;
		tile = Texture.getTextureTile(Special.BORDER);
		for (int i = 0; i < l.column(); i++) {
			g.drawImage(tile, xpipes, ypipes, null);
			xpipes += 120;
		}

		xpipes = 120;
		ypipes = l.line() * 120 + 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.SOUTH);
		for (int i = 0; i < l.column(); i++) {
			g.drawImage(tile, xpipes, ypipes, null);
			xpipes += 120;
		}

		xpipes = 0;
		ypipes = 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.WEST);
		for (int i = 0; i < l.line(); i++) {
			g.drawImage(tile, xpipes, ypipes, null);
			ypipes += 120;
		}

		xpipes = 120 + (l.column() * 120);
		ypipes = 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.EAST);
		for (int i = 0; i < l.line(); i++) {
			g.drawImage(tile, xpipes, ypipes, null);
			ypipes += 120;
		}

		// Dessin interieur puzzle
		xpipes = 120;
		ypipes = 120;
		tile = Texture.getTextureTile(Special.DARKBROWN);
		for (int i = 0; i < l.line(); i++) {
			for (int j = 0; j < l.column(); j++) {
				g.drawImage(tile, xpipes, ypipes, null);
				xpipes += 120;
			}
			xpipes = 120;
			ypipes += 120;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		background(g);
		foreground(g);
		pipeMove(g);
	}


	private void background(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

	private void foreground(Graphics g) {

	}

	private void pipeMove(Graphics g) {

	}
}
