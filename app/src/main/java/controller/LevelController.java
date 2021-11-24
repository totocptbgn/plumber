package controller;

import model.Level;
import view.Color;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class LevelController {

    private Level level;                // Model du niveau actuel

    private BufferedImage dragImg;      // Buffer du tuyau qui se fait drag
    private int [] dragOffset;          // Décalage par rapport au coin haut/gauche du tuyau
    private String dragSave;            // Variable représentant l'objet déplacé en cas d'annulation
                                        // Encodage :
                                        //  - Case du plateau : P + position y + position x
                                        //  - Case de la ressource : R + position de la ressource

    private boolean dragging;           // Boolean true si le joueur est en train de drag

    public LevelController(DrawPanelLevel panel) {

        this.level = panel.getLevel();
        this.dragOffset = new int[2];
        this.dragging = false;

        panel.addMouseListener(new MouseListener() {

            int xSource;
            int ySource;

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() + " " + e.getY());
                xSource = e.getX() / 120;
                ySource = e.getY() / 120;

                dragging = true;

                // Cas où le joueur déplace un tuyau de la réserve
                int id = ySource * 2 + (xSource - level.column() - 2);
                if (xSource > level.column() + 1 && ySource < 6 && level.getRessources()[id] > 0) {
                    // Construction de l'image de drag
                    switch (id) {
                        case 0: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.CROSS); break;
                        case 1: {
                            dragImg = Texture.getTextureTile(Color.WHITE, PipeType.LINE);
                            dragImg.createGraphics().drawImage(Texture.getTextureTile(Color.WHITE, PipeType.OVER), 0, 0, null);
                            break;
                        }
                        case 2: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.LINE); break;
                        case 3: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.LINE, Orientation.EAST); break;
                        case 4: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.EAST); break;
                        case 5: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.SOUTH); break;
                        case 6: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.TURN); break;
                        case 7: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.TURN, Orientation.WEST); break;
                        case 8: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.FORK); break;
                        case 9: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.EAST); break;
                        case 10: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.WEST); break;
                        case 11: dragImg = Texture.getTextureTile(Color.WHITE, PipeType.FORK, Orientation.SOUTH); break;
                    }
                    dragOffset[0] = e.getX() - (120 * xSource);
                    dragOffset[1] = e.getY() - (120 * ySource);

                    // Sauvegarde de la pièce dans dragSave au cas où l'action est annulée
                    dragSave = "R" + id;
                    level.getRessources()[id]--;

                    // Cas où le joueur déplace un tuyau du plateau
                } else if (xSource > 0 && ySource > 0 && xSource < level.column() + 1 && ySource < level.line() + 1 && !level.getCurrentState()[ySource][xSource].equals(".") && level.getCurrentState()[ySource][xSource].charAt(0) != '*') {

                   // Construction de l'image de drag
                   PipeType pipeType = null;
                   Orientation orientation = null;

                   dragImg = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);

                   switch (level.getCurrentState()[ySource][xSource].charAt(0)) {
                       case 'L' : pipeType = PipeType.LINE; break;
                       case 'O' : {
                           pipeType = PipeType.OVER;
                           dragImg.createGraphics().drawImage(Texture.getTextureTile(view.Color.WHITE, PipeType.LINE), 0, 0, null);
                           break;
                       }
                       case 'T' : pipeType = PipeType.TURN; break;
                       case 'F' : pipeType = PipeType.FORK; break;
                       case 'C' : pipeType = PipeType.CROSS; break;
                   }

                   switch (level.getCurrentState()[ySource][xSource].charAt(1)) {
                       case '0' : orientation = Orientation.NORTH; break;
                       case '1' : orientation = Orientation.EAST; break;
                       case '2' : orientation = Orientation.SOUTH; break;
                       case '3' : orientation = Orientation.WEST; break;
                   }

                   dragImg.createGraphics().drawImage(Texture.getTextureTile(view.Color.WHITE, pipeType, orientation), 0, 0, null);
                   dragOffset[0] = e.getX() - (120 * xSource);
                   dragOffset[1] = e.getY() - (120 * ySource);

                    // Sauvegarde de la pièce dans dragSave au cas où l'action est annulée
                   dragSave = "P" + level.getCurrentState()[ySource][xSource];
                   level.getCurrentState()[ySource][xSource] = ".";
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int xTarget = e.getX() / 120;
                int yTarget = e.getY() / 120;

                dragImg = null;
                panel.setAnimation(new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB));

                // Cas où le joueur drag à partir de la reserve
                if (xSource > level.column() + 1 && ySource < 6) {

                    // Vérification de la variable de sauvegarde
                    if (dragSave != null) {

                        // Vérification que la case cible soit bien du plateau et pas vide
                        if (xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1 && level.getCurrentState()[yTarget][xTarget].equals(".")) {
                            switch (dragSave.charAt(1) - '0') {
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
                            }
                            dragSave = null;
                        } else {
                            level.getRessources()[dragSave.charAt(1) - '0']++;
                            dragSave = null;
                        }
                    }
                }

                // Cas où le joueur a drag à partir du plateau
                else if (xSource > 0 && ySource > 0 && xSource < level.column() + 1 && ySource < level.line() + 1) {

                    // Vérification de la variable de sauvegarde
                    if (dragSave != null) {

                        // Cas où le joueur a drag vers une case de la reserve
                        if (!(xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1)) {
                            switch (dragSave.substring(1)) {
                                case "C0" : level.getRessources()[0]++; break;
                                case "O0" : level.getRessources()[1]++; break;
                                case "L0" : level.getRessources()[2]++; break;
                                case "L1" : level.getRessources()[3]++; break;
                                case "T1" : level.getRessources()[4]++; break;
                                case "T2" : level.getRessources()[5]++; break;
                                case "T0" : level.getRessources()[6]++; break;
                                case "T3" : level.getRessources()[7]++; break;
                                case "F0" : level.getRessources()[8]++; break;
                                case "F1" : level.getRessources()[9]++; break;
                                case "F3" : level.getRessources()[10]++; break;
                                case "F2" : level.getRessources()[11]++; break;
                            }
                            dragSave = null;
                        }

                        // Cas où le joueur a drag vers une autre case du plateau
                        else if (xTarget < level.column() + 1 && yTarget < level.line() + 1) {

                            // Vérification que les cases cibles et sources sont bien différentes
                            if (dragSave.charAt(2) - '0' != xTarget || dragSave.charAt(1) - '0' != yTarget) {
                                if (level.getCurrentState()[yTarget][xTarget].equals(".")) {
                                    level.getCurrentState()[yTarget][xTarget] = dragSave.substring(1);
                                    level.getCurrentState()[ySource][xSource] = ".";
                                    dragSave = null;
                                } else {
                                    level.getCurrentState()[ySource][xSource] = dragSave.substring(1);
                                    dragSave = null;
                                }
                            } else {
                                level.getCurrentState()[ySource][xSource] = dragSave.substring(1);
                                dragSave = null;
                            }
                        }
                    }
                }
                panel.repaint();
                dragging = false;
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

        // Configuration des boutons de menu
        JButton [] buttons = panel.getButtons();

        // Undo button
        buttons[0].addActionListener(e -> {
            System.out.println("Undo.");
        });

        // Redo button
        buttons[1].addActionListener(e -> {
            System.out.println("Redo.");
        });

    }
}
