package application;

import controller.EditionController;
import view.DrawPanelEdition;
import controller.LevelController;
import controller.MenuController;
import model.Level;
import view.DrawPanelLevel;
import view.MenuPanel;

import javax.swing.*;
import java.io.IOException;


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
		//this.mainPanel.add(menuPanel);
		
	}

	// Main
	public static void main(String[] args) throws IOException {
		Application mainFrame = new Application();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		
		DrawPanelLevel panelLevel1 = new DrawPanelLevel("level5", mainFrame);
		mainFrame.setPanel(panelLevel1);
		LevelController controller1 = new LevelController(panelLevel1);
		

		/*DrawPanelEdition panelEdition = new DrawPanelEdition("level2", mainFrame);
		new EditionController(panelEdition);
		mainFrame.setPanel(panelEdition);*/
		mainFrame.setVisible(true);
	}
	
	//TODO: remove
	private void setPanel(JPanel panel) {
		this.mainPanel.add(panel);
		this.pack();
		
	}

	public void startGame(String levelName) {
		/*DrawPanelLevel panelLevel = new DrawPanelLevel(levelName.split(" ")[1], this);
		LevelController controller1 = new LevelController(panelLevel);
		this.menuPanel.setVisible(false);
		this.mainPanel.add(panelLevel);*/
	}
	
	public void stopGame(JPanel gamePanel) {
		this.mainPanel.remove(gamePanel);
		this.menuPanel.setVisible(true);
	}
}
