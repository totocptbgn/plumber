package controller;

import model.Level;
import view.DrawPanelLevel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class LevelController {

    private Level level;

    public LevelController(DrawPanelLevel panel) {

        this.level = panel.getLevel();

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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int xTarget = e.getX() / 120;
                int yTarget = e.getY() / 120;

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

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }


}
