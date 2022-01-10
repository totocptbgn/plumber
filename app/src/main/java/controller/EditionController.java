package controller;

import model.Level;
import view.Color;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

/**
 *  Classe controller pour DrawPanelEdition, voir LevelController.java
 */

public class EditionController {

    private Level level;                // Model du niveau actuel

    private BufferedImage dragImg;      // Buffer du tuyau qui se fait drag
    private int [] dragOffset;          // Décalage par rapport au coin haut/gauche du tuyau
    private String dragSave;            // Variable représentant l'objet déplacé en cas d'annulation

    private JButton [] buttons;         // Undo & Redo buttons

    public EditionController(DrawPanelEdition panel) {

        this.level = panel.getLevel();
        this.dragOffset = new int[2];

        // Configuration des boutons de menu
        this.buttons = panel.getButtons();

        // Back button
        buttons[0].addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null,
                    "Do you want to Exit ? Your level won't be saved.",
                    "Exit Confirmation : ",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                // TODO : Revenir au menu
            }
        });
        buttons[0].setToolTipText("Come back to the menu.");

        // Clear button
        buttons[1].addActionListener(e -> {
            // On remplit le level.currentState de points
            for (String[] row : level.getCurrentState()) {
                Arrays.fill(row, ".");
            }
            // Puis on remplit les bordures avec des X.
            Arrays.fill(level.getCurrentState()[0], "X");
            Arrays.fill(level.getCurrentState()[level.getCurrentState().length - 1], "X");
            for (int i = 0; i < level.getCurrentState().length; i++) {
                level.getCurrentState()[i][0] = "X";
                level.getCurrentState()[i][level.getCurrentState()[0].length - 1] = "X";
            }
            panel.updateBackground();
            panel.repaint();
        });
        buttons[1].setToolTipText("Remove all the tiles from the board.");

        // Resize button
        buttons[2].addActionListener(e -> {
            // On créer un panel pour la dialog box
            JTextField heightField = new JTextField(5);
            JTextField widthField = new JTextField(5);

            JPanel dialogPanel = new JPanel();
            dialogPanel.add(new JLabel("Height : "));
            dialogPanel.add(heightField);
            dialogPanel.add(Box.createHorizontalStrut(15));
            dialogPanel.add(new JLabel("Width : "));
            dialogPanel.add(widthField);

            int result = JOptionPane.showConfirmDialog(panel.getFrame(), dialogPanel,"Pick new values from 2 to 9.", JOptionPane.OK_CANCEL_OPTION);

            // On traite les entrées
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int h = Integer.parseInt(heightField.getText());
                    int w = Integer.parseInt(widthField.getText());

                    // Si l'utilisateur rentre des chifres par entre 2 et 9
                    if (h < 2 || h > 9 || w < 2 || w > 9) {
                        JOptionPane.showMessageDialog(panel.getFrame(),
                                "Board size was not changed because the values must be between 2 and 9 included.",
                                "Wrong Values",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    level.changeLevelSize(h, w);
                    panel.updateFrameSize();
                    panel.repaint();
                } catch (NumberFormatException nfe)  {
                    // Si l'utilisateur rentre autre chose que des chiffres, on affiche un message d'erreur
                    JOptionPane.showMessageDialog(panel.getFrame(),
                            "Board size was not changed because the values must be numbers.",
                            "Wrong Values",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        buttons[2].setToolTipText("Clear the board and resize the it.");

        // Save button
        buttons[3].addActionListener(e -> {

            // On vérifie que le niveau est complet
            boolean win = level.isCompleted();

            // Si il l'est on construit un fichier .p pour sauvegarder le niveau
            if (win) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(level.column() + 2).append(" ").append(level.line() + 2).append("\n");
                for (String[] row : level.getCurrentState()) {
                    for (String s : row) {
                        stringBuilder.append(s);
                        for (int i = 0; i < 4 - s.length(); i++) {
                            stringBuilder.append(" ");
                        }
                    }
                    stringBuilder.append("\n");
                }

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("./src/main/java/data"));
                int retrival = chooser.showSaveDialog(panel);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filename = chooser.getSelectedFile().toString();
                        // On ajoute l'extension .p si l'utilisateur ne l'a pas fait
                        if (!(filename.endsWith(".p"))) {
                            filename += ".p";
                        }
                        FileWriter fw = new FileWriter(filename);
                        fw.write(stringBuilder.toString());
                        fw.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel.getFrame(),
                        "The level must be complete (or in other words, in a winning setup) to be saved.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        buttons[3].setToolTipText("Save into a file the complete level.");

        panel.addMouseListener(new MouseListener() {

            int xSource;
            int ySource;

            @Override
            public void mousePressed(MouseEvent e) {
                xSource = e.getX() / 120;
                ySource = e.getY() / 120;

                // Cas où on clique sur la reserve
                if (xSource > level.column() + 1 && ySource <= 6) {
                    int id = ySource * 2 + (xSource - level.column() - 2);
                    switch (id) {
                        case 0: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.CROSS); break;
                        case 1: {
                            dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.LINE);
                            dragImg.createGraphics().drawImage(Texture.getTextureTile(view.Color.WHITE, PipeType.OVER), 0, 0, null);
                            break;
                        }
                        case 2: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.LINE); break;
                        case 3: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.LINE, Orientation.EAST); break;
                        case 4: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.TURN, Orientation.EAST); break;
                        case 5: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.TURN, Orientation.SOUTH); break;
                        case 6: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.TURN); break;
                        case 7: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.TURN, Orientation.WEST); break;
                        case 8: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.FORK); break;
                        case 9: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.FORK, Orientation.EAST); break;
                        case 10: dragImg = Texture.getTextureTile(view.Color.WHITE, PipeType.FORK, Orientation.WEST); break;
                        case 11: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.SOUTH); break;
                        case 12: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.SOURCE); break;
                        case 13: dragImg = Texture.getTextureTile(Special.SCREWS); break;

                    }
                    dragOffset[0] = e.getX() - (120 * xSource);
                    dragOffset[1] = e.getY() - (120 * ySource);



                    // Cas où on clique sur une casse du plateau non vide
                } else if (xSource < level.column() + 2 && ySource < level.line() + 2 && !level.getCurrentState()[ySource][xSource].equals(".") && !level.getCurrentState()[ySource][xSource].equals("X")) {

                    // Construction de l'image de drag
                    PipeType pipeType = null;
                    Orientation orientation = null;
                    Color color = Color.WHITE;
                    boolean source = false;

                    dragImg = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);

                    int index = 0;
                    if (level.getCurrentState()[ySource][xSource].charAt(0) == '*') {
                        index++;
                    }

                    switch (level.getCurrentState()[ySource][xSource].charAt(index)) {
                        case 'L' : pipeType = PipeType.LINE; break;
                        case 'O' : {
                            pipeType = PipeType.OVER;
                            dragImg.createGraphics().drawImage(Texture.getTextureTile(Color.WHITE, PipeType.LINE), 0, 0, null);
                            break;
                        }
                        case 'T' : pipeType = PipeType.TURN; break;
                        case 'F' : pipeType = PipeType.FORK; break;
                        case 'C' : pipeType = PipeType.CROSS; break;
                        case 'R' : pipeType = PipeType.SOURCE; color = Color.RED; source = true; break;
                        case 'G' : pipeType = PipeType.SOURCE; color = Color.GREEN; source = true; break;
                        case 'B' : pipeType = PipeType.SOURCE; color = Color.BLUE; source = true; break;
                        case 'Y' : pipeType = PipeType.SOURCE; color = Color.YELLOW; source = true; break;
                    }
                    index++;

                    switch (level.getCurrentState()[ySource][xSource].charAt(index)) {
                        case '0' : orientation = Orientation.NORTH; break;
                        case '1' : orientation = Orientation.EAST; break;
                        case '2' : orientation = Orientation.SOUTH; break;
                        case '3' : orientation = Orientation.WEST; break;
                    }

                    dragImg.createGraphics().drawImage(Texture.getTextureTile(color, pipeType, orientation), 0, 0, null);
                    dragOffset[0] = e.getX() - (120 * xSource);
                    dragOffset[1] = e.getY() - (120 * ySource);

                    // Sauvegarde de la pièce dans dragSave au cas où l'action est annulée
                    dragSave = level.getCurrentState()[ySource][xSource];
                    if (dragSave.charAt(0) == '*') {
                        dragSave = dragSave.substring(1);
                    }

                    // On vide la case d'origine pendant le drag
                    if (source) {
                        level.getCurrentState()[ySource][xSource] = "X";
                    } else {
                        level.getCurrentState()[ySource][xSource] = ".";
                    }
                }
                panel.updateBackground();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int xTarget = e.getX() / 120;
                int yTarget = e.getY() / 120;

                // On efface l'image de drag et on refait un nouveau panel.animation vierge
                dragImg = null;
                panel.setAnimation(new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB));
                panel.repaint();


                // Cas où le joueur clique depuis la reserve
                if (xSource > level.column() + 1 && ySource <= 6) {

                    int id = ySource * 2 + (xSource - level.column() - 2);

                    // Cas où le joueur lache sur une case du plateau
                    if (xTarget < level.column() + 2 && yTarget < level.line() + 2) {

                        // La case est une case de la bordure
                        if (yTarget == 0 || yTarget == level.line() + 1 || xTarget == 0 || xTarget == level.column() + 1) {
                            if (id == 12) {
                                if (xTarget == 0 && yTarget != 0 && yTarget != level.line() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "R" + 1;
                                } else if (xTarget == level.column() + 1 && yTarget != 0 && yTarget != level.line() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "R" + 3;
                                } else if (yTarget == 0 && xTarget != 0 && xTarget != level.column() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "R" + 2;
                                } else if (yTarget == level.line() + 1 && xTarget != 0 && xTarget != level.column() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "R" + 0;
                                }

                            }
                            panel.updateBackground();

                        // La case est une case intérieure
                        } else {
                            switch (id) {
                                case 0 : level.getCurrentState()[yTarget][xTarget] = "C0"; break;
                                case 1 : level.getCurrentState()[yTarget][xTarget] = "O0"; break;
                                case 2 : level.getCurrentState()[yTarget][xTarget] = "L0"; break;
                                case 3 : level.getCurrentState()[yTarget][xTarget] = "L1"; break;
                                case 4 : level.getCurrentState()[yTarget][xTarget] = "T1"; break;
                                case 5 : level.getCurrentState()[yTarget][xTarget] = "T2"; break;
                                case 6 : level.getCurrentState()[yTarget][xTarget] = "T0"; break;
                                case 7 : level.getCurrentState()[yTarget][xTarget] = "T3"; break;
                                case 8 : level.getCurrentState()[yTarget][xTarget] = "F0"; break;
                                case 9 : level.getCurrentState()[yTarget][xTarget] = "F1"; break;
                                case 10 : level.getCurrentState()[yTarget][xTarget] = "F3"; break;
                                case 11 : level.getCurrentState()[yTarget][xTarget] = "F2"; break;
                                case 13 : {
                                    if (level.getCurrentState()[yTarget][xTarget].equals(".")) {
                                        level.getCurrentState()[yTarget][xTarget] = "*";
                                    } else if (level.getCurrentState()[yTarget][xTarget].charAt(0) != '*') {
                                        level.getCurrentState()[yTarget][xTarget] = '*' + level.getCurrentState()[yTarget][xTarget];
                                    }
                                }
                            }
                        }
                    }

                }

                // Cas où on déplace depuis la bordure du plateau
                else if ((xSource == 0 || xSource == level.column() + 1 || ySource == 0 || ySource == level.line() + 1) && xSource < level.column() + 2 && ySource < level.line() + 2) {
                    if (dragSave != null) {
                        // Cas où on glisse vers l'interieur du plateau
                        if (xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1) {
                            level.getCurrentState()[ySource][xSource] = dragSave;
                        }
                        // Cas où on glisse jusqu'à une case de la bordure (la case est donc une source)
                        else if ((xTarget == 0 || xTarget == level.column() + 1 || yTarget == 0 || yTarget == level.line() + 1) && xTarget < level.column() + 2 && yTarget < level.line() + 2) {

                            // Si c'est la même case, alors on change la couleur de la source
                            if (xSource == xTarget && ySource == yTarget) {
                                switch (dragSave.charAt(0)) {
                                    case 'R': level.getCurrentState()[ySource][xSource] = "G" + dragSave.charAt(1);
                                    case 'G': level.getCurrentState()[ySource][xSource] = "B" + dragSave.charAt(1);
                                    case 'B': level.getCurrentState()[ySource][xSource] = "Y" + dragSave.charAt(1);
                                    case 'Y': level.getCurrentState()[ySource][xSource] = "R" + dragSave.charAt(1);
                                }
                            } else {
                                char color = dragSave.charAt(0);
                                if (xTarget == 0 && yTarget != 0 && yTarget != level.line() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "" + color + 1;
                                } else if (xTarget == level.column() + 1 && yTarget != 0 && yTarget != level.line() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "" + color + 3;
                                } else if (yTarget == 0 && xTarget != 0 && xTarget != level.column() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "" + color + 2;
                                } else if (yTarget == level.line() + 1 && xTarget != 0 && xTarget != level.column() + 1) {
                                    level.getCurrentState()[yTarget][xTarget] = "" + color + 0;
                                }
                            }
                        }
                        dragSave = null;
                        panel.updateBackground();
                    }
                }

                // Cas où on déplace depuis l'intérieur du plateau
                else if (xSource > 0 && ySource > 0 && xSource < level.column() + 1 && ySource < level.line() + 1) {
                    // Vérification de la variable de sauvegarde
                    if (dragSave != null) {
                        // Cas où le joueur a drag vers une case de la reserve ou en dehors du plateau (vers une case autre que le plateau)
                        if (!(xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1)) {
                            // On ne fait, rien : on vide juste la valeur de drag, la pièce est supprimée
                            dragSave = null;
                        }

                        // Cas où le joueur a drag vers une autre case du plateau
                        else if (xTarget < level.column() + 1 && yTarget < level.line() + 1) {
                            level.getCurrentState()[yTarget][xTarget] = dragSave;
                            dragSave = null;
                        }
                    }

                }
            }

            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // On déplace dragImg à l'endroit où le pointeur se situe pendant le drag
                if (dragImg != null) {
                    panel.setAnimation(new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB));
                    Graphics g = panel.getAnimation().createGraphics();
                    g.drawImage(dragImg, e.getX() - dragOffset[0], e.getY() - dragOffset[1], null);
                }
                panel.repaint();
            }
            public void mouseMoved(MouseEvent e) {}
        });

        // Confirmation de la fermeture de la fenêtre
        panel.getFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(panel.getFrame(),
                        "Do you want to Exit ?",
                        "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    panel.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    panel.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
