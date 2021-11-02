import javax.swing.*;
import java.awt.*;

/**
 *  Classe principale qui initialise les objets et lance le menu.
 */

public class MainFrame extends JFrame {

    static public int WIDTH = 840;
    static public int HEIGHT = 720;

    private DrawPanel pnl;

    // Construction du Frame et du Panel
    public MainFrame() {
        pnl = new DrawPanel();
        getContentPane().add(pnl);

        Graphics g = pnl.getGraphics();
        // Construire le menu ici sur g

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Plumber");
        setResizable(false);
        pack();
        setVisible(true);
    }

    // Main
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        // Model model = new Model();
    }
}
