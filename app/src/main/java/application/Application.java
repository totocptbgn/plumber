package application;

import controller.EditionController;
import view.DrawPanelEdition;

import javax.swing.*;
import java.io.IOException;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 *  Classe non finale : Test du mode édition.
 */

public class Application extends JFrame {

	public void setPanel(JPanel panel) {
		this.getContentPane().add(panel);
	}

	public static void main(String[] args) throws IOException {
		Application mainFrame = new Application();
		mainFrame.setTitle("Plumber");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		/*
		DrawPanelLevel panelLevel1 = new DrawPanelLevel(2, mainFrame);
		mainFrame.setPanel(panelLevel1);
		LevelController controller1 = new LevelController(panelLevel1);
		*/

		DrawPanelEdition panelEdition = new DrawPanelEdition(2, mainFrame);
		new EditionController(panelEdition);
		mainFrame.setPanel(panelEdition);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
