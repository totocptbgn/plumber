package controller;

import model.Level;
import view.*;
import view.Color;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class LevelController {

    private Level level;

    BufferedImage dragImg;
    int [] dragOffset;

    public LevelController(DrawPanelLevel panel) {

        this.level = panel.getLevel();
        this.dragOffset = new int[2];

        panel.addMouseListener(new MouseListener() {

            int xSource;
            int ySource;

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                xSource = e.getX() / 120;
                ySource = e.getY() / 120;

                if (xSource > level.column() + 1 && ySource < 6 && level.getRessources()[ySource * 2 + (xSource - level.column() - 2)] > 0) {
                    switch (ySource * 2 + (xSource - level.column() - 2)) {
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
                } else if (xSource > 0 && ySource > 0 && xSource < level.column() + 1 && ySource < level.line() + 1 && !level.getCurrentState()[ySource][xSource].equals(".") && level.getCurrentState()[ySource][xSource].charAt(0) != '*') {
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
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int xTarget = e.getX() / 120;
                int yTarget = e.getY() / 120;

                dragImg = null;
                panel.setAnimation(new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB));

                if (xSource > level.column() + 1 && ySource < 6) {
                    if (xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1 && level.getCurrentState()[yTarget][xTarget].equals(".")) {
                        if (level.getRessources()[ySource * 2 + (xSource - level.column() - 2)] > 0) {
                            level.getRessources()[ySource * 2 + (xSource - level.column() - 2)]--;
                            switch (ySource * 2 + (xSource - level.column() - 2)) {
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
                        }
                    }
                } else if (xSource > 0 && ySource > 0 && xSource < level.column() + 1 && ySource < level.line() + 1) {
                    if (level.getCurrentState()[ySource][xSource].charAt(0) != '*' && !level.getCurrentState()[ySource][xSource].equals(".")) {
                        if (xTarget > level.column() + 1 && yTarget < 6) {
                            switch (level.getCurrentState()[ySource][xSource]) {
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
                            level.getCurrentState()[ySource][xSource] = ".";
                        } else if (xTarget > 0 && yTarget > 0 && xTarget < level.column() + 1 && yTarget < level.line() + 1) {
                            if (xSource != xTarget || ySource != xTarget) {
                                if (level.getCurrentState()[ySource][xSource].charAt(0) != '*' && !level.getCurrentState()[ySource][xSource].equals(".") && level.getCurrentState()[yTarget][xTarget].equals(".")) {
                                    level.getCurrentState()[yTarget][xTarget] = level.getCurrentState()[ySource][xSource];
                                    level.getCurrentState()[ySource][xSource] = ".";
                                }
                            }
                        }
                    }
                }
                panel.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragImg != null) {
                    panel.setAnimation(new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB));
                    Graphics g = panel.getAnimation().createGraphics();
                    g.drawImage(dragImg, e.getX() - dragOffset[0], e.getY() - dragOffset[1], null);
                }
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
}
