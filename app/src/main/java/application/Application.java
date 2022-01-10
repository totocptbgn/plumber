package application;

import controller.EditionController;
import controller.MenuController;
import view.DrawPanelEdition;
import view.MenuPanel;

import javax.swing.*;
import java.io.IOException;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 *  Classe non finale : Test du mode édition.
 */

public class Application extends JFrame {

	private MenuPanel menuPanel;
	public Application() {
		MenuController menuController = new MenuController(this);
		this.menuPanel = new MenuPanel();
		menuController.setMenuPanel(this.menuPanel);
		this.menuPanel.createPanels(menuController);
		this.setPanel(menuPanel);
		
	}
	
	public void setPanel(JPanel panel) {
		this.setContentPane(panel);
		this.pack();
	}

	// Main
	public static void main(String[] args) throws IOException {
		Application mainFrame = new Application();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		/*
		DrawPanelLevel panelLevel1 = new DrawPanelLevel("level5", mainFrame);
		mainFrame.setPanel(panelLevel1);
		new LevelController(panelLevel1);


		 */

		DrawPanelEdition panelEdition = new DrawPanelEdition("level2", mainFrame);
		new EditionController(panelEdition);
		mainFrame.setPanel(panelEdition);

		
		mainFrame.setVisible(true);
	}

	public void startGame(String levelName) {
		//TODO: réparer
		//DrawPanelLevel panelLevel = new DrawPanelLevel(new Level(Integer.parseInt(levelName.split(" ")[1])), this);
		//this.setContentPane(panelLevel);
		//LevelController controller1 = new LevelController(panelLevel);
	}
	
	public void stopGame() {
		this.setPanel(this.menuPanel);
	}
}
