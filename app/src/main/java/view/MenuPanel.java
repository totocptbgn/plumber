package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.MenuController;

public class MenuPanel extends JPanel{
	private JPanel mainMenu, levelSelect;
	public MenuPanel() {
		mainMenu = new JPanel();
		levelSelect = new JPanel();
		mainMenu.setPreferredSize(new Dimension(720,512));;
		mainMenu.setVisible(true);
		levelSelect.setVisible(false);
	}
	public void createPanels(MenuController controller) {
		createMainMenu(controller);
		createLevelsMenu(controller);
		
		this.add(mainMenu);
		this.add(levelSelect);
	}

	private void createMainMenu(MenuController controller) {
		this.mainMenu.setLayout(new GridLayout(0,3));
		
		for(int i = 0; i < 4; i++)
			mainMenu.add(Box.createHorizontalGlue());
		// Bouton jouer
		JButton play = new JButton("Jouer");
		controller.setButtonAction(play);
		mainMenu.add(play);
		
		for(int i = 0; i < 5; i++)
			mainMenu.add(Box.createHorizontalGlue());
		
		// Bouton parametres
		JButton parameters = new JButton("Paramètres");
		controller.setButtonAction(parameters);
		mainMenu.add(parameters);
		
		for(int i = 0; i < 5; i++)
			mainMenu.add(Box.createHorizontalGlue());
		
		// Bouton quitter
		JButton quit = new JButton("Quitter");
		controller.setButtonAction(quit);
		mainMenu.add(quit);
		
		for(int i = 0; i < 4; i++)
			mainMenu.add(Box.createHorizontalGlue());
	}
	
	private void createLevelsMenu(MenuController controller) {
		levelSelect.setLayout(new GridLayout(0,11));
		for(int j = 0; j < 12; j++)
			levelSelect.add(Box.createHorizontalGlue());
		File folder = new File("src/main/java/data");
		File[] listOfFiles = folder.listFiles();
		int i = 1;
		for(File file : listOfFiles) {
			if(file.isFile() && file.getName().contains("level")) {
				JButton b = new JButton("Level "+i);
				controller.setButtonAction(b);
				levelSelect.add(b);
				if(i%5 == 0)
					for(int j = 0; j < 13; j++)
						levelSelect.add(Box.createHorizontalGlue());
				else levelSelect.add(Box.createHorizontalGlue());
				i++;
			}
		}
	}

	public void switchMenu() {
		if(mainMenu.isVisible()) {
			mainMenu.setVisible(false);
			levelSelect.setVisible(true);
		}
		else if(levelSelect.isVisible()) {
			levelSelect.setVisible(false);
			mainMenu.setVisible(true);
		}
	}
}
