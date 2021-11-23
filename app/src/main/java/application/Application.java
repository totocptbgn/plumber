package application;

import controller.LevelController;
import model.Level;
import view.DrawPanelLevel;

import javax.swing.*;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 */

public class Application extends JFrame {

	public void setPanel(JPanel panel) {
		this.getContentPane().add(panel);
	}


	public static void main(String[] args) {

		Application mainFrame = new Application();
		mainFrame.setTitle("Plumber");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		DrawPanelLevel panelLevel1 = new DrawPanelLevel(new Level(2));
		mainFrame.setPanel(panelLevel1);
		LevelController controller1 = new LevelController(panelLevel1);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
