package view;

import model.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanelEdition extends JPanel{

    private Level level;						// Model du niveau
    private BufferedImage background;			// Save du background
    private BufferedImage animation;			// Buffer pour l'affichage des animations
    private JButton [] buttons;                 // Boutons de la menu bar, accessible par le controller par un getter

    public DrawPanelEdition(int id, JFrame frame) {
        this.level = new Level(id, true);
        updateFrameSize();

        frame.setTitle("Plumber - Level " + id + " - Edition Mode");

        // Construction de la menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JButton clearBtn = new JButton("Clear");
        menuBar.add(clearBtn);
        JButton saveBtn = new JButton("Save Copy");
        menuBar.add(saveBtn);

        buttons = new JButton[2];
        buttons[0] = clearBtn;
        buttons[1] = saveBtn;
    }

    // Change la taille de la fenêtre en fonction des changements faits au niveau (this.level)
    public void updateFrameSize() {
        int w = (level.column() + 4) * 120;
        int h = Math.max(level.line() + 2, 7) * 120;

        this.setPreferredSize(new Dimension(w, h));
        this.animation = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        updateBackground();
    }

    // Reconstruit le background après changement de la construction du niveau
    public void updateBackground() {
        int w = (level.column() + 4) * 120;
        int h = Math.max(level.line() + 2, 7) * 120;

        this.background = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = background.createGraphics();

        int x = w - 240;
        int y = 0;

        BufferedImage tile = Texture.getTextureTile(Special.DARKBROWN);
        g.setColor(java.awt.Color.black);
        g.fillRect(0, 0, w, h);

        // Dessin du fond des ressources
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
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
    }

    // Getters & Setters

    public Level getLevel() {
        return level;
    }

    public JButton[] getButtons() {
        return buttons;
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

        g.setColor(java.awt.Color.white);
        g.setFont(new Font("Monospaced", Font.PLAIN, 15));

        for (int i = 0; i < pipes.length + 2; i++) {
            if (i % 2 == 0) {
                x = (state[0].length) * 120;
            } else {
                x = (state[0].length + 1) * 120;
            }

            BufferedImage img = null;
            switch (i) {
                case 0: img = Texture.getTextureTile(Color.WHITE, PipeType.CROSS); break;
                case 1: {
                    img = Texture.getTextureTile(Color.WHITE, PipeType.OVER);
                    g.drawImage(Texture.getTextureTile(Color.WHITE, PipeType.LINE), x, y, null);
                    break;
                }
                case 2: img = Texture.getTextureTile(Color.WHITE, PipeType.LINE); break;
                case 3: img = Texture.getTextureTile(Color.WHITE, PipeType.LINE, Orientation.EAST); break;
                case 4: img = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.EAST); break;
                case 5: img = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.SOUTH); break;
                case 6: img = Texture.getTextureTile(Color.WHITE, PipeType.TURN); break;
                case 7: img = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.WEST); break;
                case 8: img = Texture.getTextureTile(Color.WHITE, PipeType.FORK); break;
                case 9: img = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.EAST); break;
                case 10: img = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.WEST); break;
                case 11: img = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.SOUTH); break;
                case 12: img = Texture.getTextureTile(Color.WHITE, PipeType.SOURCE); break;
                case 13: img = Texture.getTextureTile(Special.SCREWS); break;
            }
            g.drawImage(img, x, y, null);
            g.drawString("∞", x + 10, y + 110);

            if (i % 2 == 1) {
                y += 120;
            }
        }

        // Dessin des tuyaux du plateau (sans couleurs)
        for (int i = 1; i < state.length - 1; i++) {
            for (int j = 1; j < state[0].length - 1; j++) {
                if (!state[i][j].equals(".")) {
                    int chr = 0;
                    if (state[i][j].charAt(0) == '*') {
                        x = j * 120;
                        y = i * 120;
                        BufferedImage tile = Texture.getTextureTile(Special.SCREWS);
                        g.drawImage(tile, x, y, null);
                        chr++;
                    }
                    PipeType pipeType = null;
                    Orientation orientation = null;

                    switch (state[i][j].charAt(chr)) {
                        case 'L' : pipeType = PipeType.LINE; break;
                        case 'O' : {
                            pipeType = PipeType.OVER;
                            g.drawImage(Texture.getTextureTile(view.Color.WHITE, PipeType.LINE), j * 120, i * 120, null);
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

                    // TODO: Aller chercher la couleur dans Level.colorState
                    g.drawImage(Texture.getTextureTile(view.Color.WHITE, pipeType, orientation), j * 120, i * 120, null);
                }
            }
        }

    }

    // Dessin des animations de mouvement
    private void pipeMove(Graphics g) {
        g.drawImage(animation, 0, 0, null);
    }
}
