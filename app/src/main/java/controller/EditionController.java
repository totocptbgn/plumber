package controller;

import model.Level;
import view.DrawPanelEdition;

import javax.swing.*;
import java.awt.event.*;
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
            JTextField heightField = new JTextField(5);
            JTextField widthField = new JTextField(5);

            JPanel dialogPanel = new JPanel();
            dialogPanel.add(new JLabel("Height : "));
            dialogPanel.add(heightField);
            dialogPanel.add(Box.createHorizontalStrut(15));
            dialogPanel.add(new JLabel("Width : "));
            dialogPanel.add(widthField);

            int result = JOptionPane.showConfirmDialog(panel.getFrame(), dialogPanel,"Pick new values from 2 to 9.", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int h = Integer.parseInt(heightField.getText());
                    int w = Integer.parseInt(widthField.getText());

                    if (h < 2 || h > 9 || w < 2 || w > 9) {
                        JOptionPane.showMessageDialog(panel.getFrame(),
                                "Board size was not changed because the values must be between 2 and 9 included.",
                                "Wrong Values",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    level.changeLevelSize(h, w);
                    panel.updateFrameSize();
                    panel.repaint();
                } catch (NumberFormatException nfe)  {
                    JOptionPane.showMessageDialog(panel.getFrame(),
                            "Board size was not changed because the values must be numbers.",
                            "Wrong Values",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Save button
        buttons[2].addActionListener(e -> {
            Object[] options = {"Yes", "Cancel"};
            int result = JOptionPane.showOptionDialog(panel.getFrame(),
                    "Do you want to save this level into a new file ?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (result == JOptionPane.YES_OPTION) {
                // TODO : Create new level from current state
            }
        });
        buttons[2].setEnabled(false);

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

        // Close window confirmation
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
