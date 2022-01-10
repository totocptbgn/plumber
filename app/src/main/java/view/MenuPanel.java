package view;

import controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuPanel extends JPanel{
	private JPanel mainMenu, levelSelect, editedLevelsSelect;
	private int idMenuShowing;
	public MenuPanel() {
		mainMenu = new JPanel();
		levelSelect = new JPanel();
		editedLevelsSelect = new JPanel();
		mainMenu.setPreferredSize(new Dimension(720,512));;
		mainMenu.setVisible(true);
		levelSelect.setVisible(false);
		editedLevelsSelect.setVisible(false);
	}
	public void createPanels(MenuController controller) {
		createMainMenu(controller);
		createLevelsMenu(controller);
		createEditedLevelsMenu(controller);
		
		this.add(mainMenu);
		this.add(levelSelect);
		this.add(editedLevelsSelect);
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
		JButton editedLevels = new JButton("Niveaux persos");
		controller.setButtonAction(editedLevels);
		mainMenu.add(editedLevels);
		
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
		levelSelect.setLayout(new GridLayout(0,7));
		for(int j = 0; j < 8; j++)
			levelSelect.add(Box.createHorizontalGlue());
		File folder = new File("src/main/java/data");
		File[] listOfFiles = folder.listFiles();
		int i = 1;
		for(File file : listOfFiles) {
			if(file.isFile() && file.getName().contains("level")) {
				JButton b = new JButton("Level "+i);
				controller.setButtonAction(b);
				levelSelect.add(b);
				if(i%3 == 0)
					for(int j = 0; j < 9; j++)
						levelSelect.add(Box.createHorizontalGlue());
				else levelSelect.add(Box.createHorizontalGlue());
				i++;
			}
		}
		for(int j = 0; j < 70; j++)
			levelSelect.add(Box.createHorizontalGlue());
		JButton edit = new JButton("Mode édition OFF");
		controller.setButtonAction(edit);
		levelSelect.add(edit);
		for(int j = 0; j < 3; j++)
			levelSelect.add(Box.createHorizontalGlue());
		JButton b = new JButton("Retour");
		controller.setButtonAction(b);
		levelSelect.add(b);
	}
	
	private void createEditedLevelsMenu(MenuController controller) {
		editedLevelsSelect.setLayout(new GridLayout(0,7));
		for(int j = 0; j < 8; j++)
			editedLevelsSelect.add(Box.createHorizontalGlue());
		File folder = new File("src/main/java/data");
		File[] listOfFiles = folder.listFiles();
		int i = 1;
		for(File file : listOfFiles) {
			if(file.isFile() && file.getName().substring(file.getName().length()-2).equals(".p") && !file.getName().contains("level")) {
				JButton b = new JButton(file.getName());
				controller.setButtonAction(b);
				editedLevelsSelect.add(b);
				if(i%3 == 0)
					for(int j = 0; j < 9; j++)
						editedLevelsSelect.add(Box.createHorizontalGlue());
				else editedLevelsSelect.add(Box.createHorizontalGlue());
				i++;
			}
		}
		for(int j = 0; j < 79-i*2; j++)
			editedLevelsSelect.add(Box.createHorizontalGlue());
		JButton edit = new JButton("Mode édition OFF");
		controller.setButtonAction(edit);
		editedLevelsSelect.add(edit);
		for(int j = 0; j < 3; j++)
			editedLevelsSelect.add(Box.createHorizontalGlue());
		JButton retour = new JButton("Retour");
		controller.setButtonAction(retour);
		editedLevelsSelect.add(retour);
	}

	public void switchMenuLevel(int i) {
		if(i != 3)
			this.idMenuShowing = i;
		if(mainMenu.isVisible()) {
			mainMenu.setVisible(false);
			if(this.idMenuShowing == 1)
				levelSelect.setVisible(true);
			else if(i == 2)
				editedLevelsSelect.setVisible(true);
		}
		else if(levelSelect.isVisible()||editedLevelsSelect.isVisible()) {
			levelSelect.setVisible(false);
			editedLevelsSelect.setVisible(false);
			mainMenu.setVisible(true);
		}
	}
}
