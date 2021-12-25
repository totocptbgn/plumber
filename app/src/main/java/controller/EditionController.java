package controller;

import model.Level;
import view.DrawPanelEdition;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *  Classe controller pour DrawPanelEdition, voir LevelController.java
 */

public class EditionController {

    private Level level;                // Model du niveau actuel

    private BufferedImage dragImg;      // Buffer du tuyau qui se fait drag
    private int [] dragOffset;          // Décalage par rapport au coin haut/gauche du tuyau
    // private String dragSave;         // Variable représentant l'objet déplacé en cas d'annulation

    private JButton [] buttons;         // Undo & Redo buttons

    public EditionController(DrawPanelEdition panel) {

        this.level = panel.getLevel();
        // this.dragOffset = new int[2];

        // Configuration des boutons de menu
        this.buttons = panel.getButtons();

        // Clear button
        buttons[0].addActionListener(e -> {
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

        // Resize button
        buttons[1].addActionListener(e -> {

        });

        // Save button
        buttons[2].addActionListener(e -> {

        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {}
            public void mouseMoved(MouseEvent e) {}
        });
    }
}
