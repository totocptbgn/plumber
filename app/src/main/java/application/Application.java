package application;

import controller.LevelController;
import controller.MenuController;
import model.Level;
import view.DrawPanelLevel;
import view.MenuPanel;

import javax.swing.*;


/**
 *  Classe principale qui initialise les objets et lance le menu.
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
	public static void main(String[] args) {
		Application mainFrame = new Application();
		mainFrame.setTitle("Plumber");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//DrawPanelLevel panelLevel1 = new DrawPanelLevel(new Level(1));
		//mainFrame.setPanel(panelLevel1);
		//LevelController controller1 = new LevelController(panelLevel1);
		
		mainFrame.setVisible(true);
	}

	public void startGame(String levelName) {
		DrawPanelLevel panelLevel = new DrawPanelLevel(new Level(Integer.parseInt(levelName.split(" ")[1])), this);
		this.setContentPane(panelLevel);
		LevelController controller1 = new LevelController(panelLevel);
	}
	
	public void stopGame() {
		this.setPanel(this.menuPanel);
	}
}
