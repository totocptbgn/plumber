package view;

import controller.UndoManager;
import model.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawPanelLevel extends JPanel {

	private Level level;						// Model du niveau
	private BufferedImage background;			// Save du background
	private BufferedImage animation;			// Buffer pour l'affichage des animations
	private JButton [] buttons;        			// Boutons de la menu bar, accessible par le controller par un getter
	private UndoManager edits;					// Edit Manager, accessible

	public DrawPanelLevel(String filename, JFrame frame) {
		this.level = new Level(filename, false);
		int w = (level.column() + 4) * 120;
		int h = Math.max(level.line() + 2, 6) * 120;

		this.setPreferredSize(new Dimension(w, h));
		this.animation = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		frame.setTitle("Plumber - Level : " + filename);

		// Création de l'image de backgroud
		this.background = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = background.createGraphics();

		int x = w - 240;
		int y = 0;

		BufferedImage tile = Texture.getTextureTile(Special.DARKBROWN);
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);

		// Dessin du fond de la reserve
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				g.drawImage(tile, x, y, null);
				y += 120;
			}
			x += 120;
			y = 0;
		}

		// Dessin des coins
		x = 0;
		y = 0;
		tile = Texture.getTextureTile(Special.CORNER);
		g.drawImage(tile, x, y, null);

		x += (level.column() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.EAST);
		g.drawImage(tile, x, y, null);

		x = 0;
		y += (level.line() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.WEST);
		g.drawImage(tile, x, y, null);

		x += (level.column() + 1) * 120;
		tile = Texture.getTextureTile(Special.CORNER, Orientation.SOUTH);
		g.drawImage(tile, x, y, null);

		// Dessin des bordures
		x = 120;
		y = 0;
		tile = Texture.getTextureTile(Special.BORDER);
		for (int i = 0; i < level.column(); i++) {
			g.drawImage(tile, x, y, null);
			x += 120;
		}

		x = 120;
		y = level.line() * 120 + 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.SOUTH);
		for (int i = 0; i < level.column(); i++) {
			g.drawImage(tile, x, y, null);
			x += 120;
		}

		x = 0;
		y = 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.WEST);
		for (int i = 0; i < level.line(); i++) {
			g.drawImage(tile, x, y, null);
			y += 120;
		}

		x = 120 + (level.column() * 120);
		y = 120;
		tile = Texture.getTextureTile(Special.BORDER, Orientation.EAST);
		for (int i = 0; i < level.line(); i++) {
			g.drawImage(tile, x, y, null);
			y += 120;
		}

		// Dessin du plateau
		x = 120;
		y = 120;
		tile = Texture.getTextureTile(Special.DARKBROWN);
		for (int i = 0; i < level.line(); i++) {
			for (int j = 0; j < level.column(); j++) {
				g.drawImage(tile, x, y, null);
				x += 120;
			}
			x = 120;
			y += 120;
		}

		String[][] state = level.getCurrentState();

		// Dessin des sources
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < state[0].length; j++) {
				view.Color color = null;
				view.Orientation orientation = null;
				String s = state[i * (state.length - 1)][j];

				switch (s.charAt(0)) {
					case 'X' : continue;
					case 'R' : color = view.Color.RED; break;
					case 'G' : color = view.Color.GREEN; break;
					case 'B' : color = view.Color.BLUE; break;
					case 'Y' : color = view.Color.YELLOW; break;
				}

				switch (s.charAt(1)) {
					case '0' : orientation = Orientation.NORTH; break;
					case '2' : orientation = Orientation.SOUTH; break;
				}

				x = j * 120;
				y = i * (state.length - 1) * 120;
				tile = Texture.getTextureTile(color, PipeType.SOURCE, orientation);
				g.drawImage(tile, x, y, null);
			}
		}

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < 2; j++) {
				view.Color color = null;
				view.Orientation orientation = null;
				String s = state[i][j * (state[0].length - 1)];

				switch (s.charAt(0)) {
					case 'X' : continue;
					case 'R' : color = view.Color.RED; break;
					case 'G' : color = view.Color.GREEN; break;
					case 'B' : color = view.Color.BLUE; break;
					case 'Y' : color = view.Color.YELLOW; break;
				}

				switch (s.charAt(1)) {
					case '1' : orientation = Orientation.EAST; break;
					case '3' : orientation = Orientation.WEST; break;
				}

				x = j * (state[0].length - 1) * 120;
				y = i * 120;
				tile = Texture.getTextureTile(color, PipeType.SOURCE, orientation);
				g.drawImage(tile, x, y, null);
			}
		}

		// Dessin des visses
		for (int i = 1; i < state.length - 1; i++) {
			for (int j = 1; j < state[0].length - 1; j++) {
				if (state[i][j].charAt(0) == '*') {
					x = j * 120;
					y = i * 120;
					tile = Texture.getTextureTile(Special.SCREWS);
					g.drawImage(tile, x, y, null);
				}
			}
		}

		// Construction de la menu bar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JButton undoBtn = new JButton("Undo");
		BufferedImage buffimg = null;
		try {
			buffimg = ImageIO.read(new File("src/main/java/data/undo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		undoBtn.setIcon(new ImageIcon(buffimg));
		menuBar.add(undoBtn);

		JButton redoBtn = new JButton("Redo");
		try {
			buffimg = ImageIO.read(new File("src/main/java/data/redo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		redoBtn.setIcon(new ImageIcon(buffimg));
		menuBar.add(redoBtn);

		buttons = new JButton[2];
		buttons[0] = undoBtn;
		buttons[1] = redoBtn;

		// Undo / Redo Manager
		this.edits = new UndoManager(level);
	}

	// Getters & Setters

	public Level getLevel() {
		return level;
	}

	public BufferedImage getAnimation() {
		return animation;
	}

	public void setAnimation(BufferedImage animation) {
		this.animation = animation;
	}

	public JButton[] getButtons() {
		return buttons;
	}

	public UndoManager getEditManager() {
		return edits;
	}

	// Dessin sur le Graphics du JPanel
	@Override
	protected void paintComponent(Graphics g) {
		background(g);
		foreground(g);
		pipeMove(g);
	}

	// Dessin du background
	private void background(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

	// Dessins des pièces de puzzle en fonction de l'état courant
	private void foreground(Graphics g) {
		String[][] state = level.getCurrentState();
		int [] pipes = level.getRessources();

		// Dessin de la réserve
		int x;
		int y = 0;

		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.PLAIN, 15));

		for (int i = 0; i < pipes.length; i++) {
			if (i % 2 == 0) {
				x = (state[0].length) * 120;
			} else {
				x = (state[0].length + 1) * 120;
			}

			view.Color c;
			if (pipes[i] == 0) {
				c = view.Color.DARKGREY;
			} else {
				c = view.Color.WHITE;
			}

			BufferedImage img = null;
			switch (i) {
				case 0: img = Texture.getTextureTile(c, PipeType.CROSS); break;
				case 1: {
					img = Texture.getTextureTile(c, PipeType.OVER);
					g.drawImage(Texture.getTextureTile(c, PipeType.LINE), x, y, null);
					break;
				}
				case 2: img = Texture.getTextureTile(c, PipeType.LINE); break;
				case 3: img = Texture.getTextureTile(c, PipeType.LINE, Orientation.EAST); break;
				case 4: img = Texture.getTextureTile(c, PipeType.TURN, Orientation.EAST); break;
				case 5: img = Texture.getTextureTile(c, PipeType.TURN, Orientation.SOUTH); break;
				case 6: img = Texture.getTextureTile(c, PipeType.TURN); break;
				case 7: img = Texture.getTextureTile(c, PipeType.TURN, Orientation.WEST); break;
				case 8: img = Texture.getTextureTile(c, PipeType.FORK); break;
				case 9: img = Texture.getTextureTile(c, PipeType.FORK, Orientation.EAST); break;
				case 10: img = Texture.getTextureTile(c, PipeType.FORK, Orientation.WEST); break;
				case 11: img = Texture.getTextureTile(c, PipeType.FORK, Orientation.SOUTH); break;
			}
			g.drawImage(img, x, y, null);
			g.drawString(String.valueOf(pipes[i]), x + 10, y + 110);

			if (i % 2 == 1) {
				y += 120;
			}
		}

		// Dessin des tuyaux du plateau
		level.updateColor();
		for (int i = 1; i < state.length - 1; i++) {
			for (int j = 1; j < state[0].length - 1; j++) {
				if (!state[i][j].equals(".")) {
					int chr = 0;
					if (state[i][j].charAt(0) == '*') {
						chr++;
					}
					PipeType pipeType = null;
					Orientation orientation = null;
					view.Color color = null;


					switch (state[i][j].charAt(chr)) {
						case 'L' : pipeType = PipeType.LINE; break;
						case 'O' : {
							pipeType = PipeType.OVER;
							String[] split = level.getColorState()[i][j].split(" ");
							if (split.length >= 1) {
								switch (split[0]) {
									case "Red": color = view.Color.RED; break;
									case "Blue": color = view.Color.BLUE; break;
									case "Green": color = view.Color.GREEN; break;
									case "Yellow": color = view.Color.YELLOW; break;
									default: color = view.Color.WHITE; break;
								}
							}
							g.drawImage(Texture.getTextureTile(color, PipeType.LINE), j * 120, i * 120, null);
							break;
						}
						case 'T' : pipeType = PipeType.TURN; break;
						case 'F' : pipeType = PipeType.FORK; break;
						case 'C' : pipeType = PipeType.CROSS; break;
					}

					switch (state[i][j].charAt(chr + 1)) {
						case '0' : orientation = Orientation.NORTH; break;
						case '1' : orientation = Orientation.EAST; break;
						case '2' : orientation = Orientation.SOUTH; break;
						case '3' : orientation = Orientation.WEST; break;
					}

					if (state[i][j].charAt(chr) == 'O') {
						String[] split = level.getColorState()[i][j].split(" ");
						if (split.length >= 2) {
							switch (split[1]) {
								case "Red": color = view.Color.RED; break;
								case "Blue": color = view.Color.BLUE; break;
								case "Green": color = view.Color.GREEN; break;
								case "Yellow": color = view.Color.YELLOW; break;
								default: color = view.Color.WHITE; break;
							}
						}
					} else {
						switch (level.getColorState()[i][j]) {
							case "Red": color = view.Color.RED; break;
							case "Blue": color = view.Color.BLUE; break;
							case "Green": color = view.Color.GREEN; break;
							case "Yellow": color = view.Color.YELLOW; break;
							default: color = view.Color.WHITE; break;
						}
					}

					g.drawImage(Texture.getTextureTile(color, pipeType, orientation), j * 120, i * 120, null);
				}
			}
		}

	}

	// Dessin des animations de mouvement
	private void pipeMove(Graphics g) {
		g.drawImage(animation, 0, 0, null);
	}
}
