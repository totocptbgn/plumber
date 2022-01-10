package application;

import controller.EditionController;
import controller.LevelController;
import controller.MenuController;
import view.DrawPanelEdition;
import view.DrawPanelLevel;
import view.MenuPanel;

import javax.swing.*;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 *  Classe non finale : Test du mode édition.
 */

@SuppressWarnings("serial")
public class Application extends JFrame {
	private JPanel mainPanel;
	private MenuPanel menuPanel;
	public Application() {
		mainPanel = new JPanel();
		this.add(mainPanel);
		MenuController menuController = new MenuController(this);
		this.menuPanel = new MenuPanel();
		menuController.setMenuPanel(this.menuPanel);
		this.menuPanel.createPanels(menuController);
		this.mainPanel.add(menuPanel);
		
	}

	// Main
	public static void main(String[] args) {
		Application mainFrame = new Application();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		/*if (false) {
			DrawPanelLevel panelLevel1 = new DrawPanelLevel("level5", mainFrame);
			mainFrame.setPanel(panelLevel1);
			new LevelController(panelLevel1);
		} else {
			DrawPanelEdition panelEdition = new DrawPanelEdition("level2", mainFrame);
			new EditionController(panelEdition);
			mainFrame.setPanel(panelEdition);
		}*/

		mainFrame.setVisible(true);
		mainFrame.pack();
	}
	
	//TODO: remove
	private void setPanel(JPanel panel) {
		this.mainPanel.add(panel);
		this.pack();
	}

	public void startGame(String levelName) {
		DrawPanelLevel panelLevel = new DrawPanelLevel(levelName, this);
		LevelController controller = new LevelController(panelLevel);
		this.menuPanel.setVisible(false);
		this.mainPanel.add(panelLevel);
		this.pack();
	}
	
	public void stopGame(JPanel gamePanel) {
		this.mainPanel.remove(gamePanel);
		this.menuPanel.setVisible(true);
		this.pack();
	}
	
	public void startEditing(String levelName) {
		DrawPanelEdition panelEdition = new DrawPanelEdition("level2", this);
		new EditionController(panelEdition);
		this.menuPanel.setVisible(false);
		this.mainPanel.add(panelEdition);
		this.pack();
	}
	public void stopEditing(JPanel editingPanel) {
		this.mainPanel.remove(editingPanel);
		this.menuPanel.setVisible(true);
		this.pack();
	}
}
