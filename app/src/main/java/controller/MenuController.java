package controller;

import application.Application;
import view.MenuPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
	private Application app;
	private boolean firstPlay;
	private MenuPanel menuPanel;
	private boolean editionMode;
	private boolean editionModeChanged;
	
	public MenuController(Application app) {
		this.app = app;
		this.firstPlay = false;
		this.editionMode = false;
		this.editionModeChanged = false;
	}
	
	public void setButtonAction(JButton b) {
		if(b.getText().equals("Jouer")) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(firstPlay) {
						firstPlay = !firstPlay;
						app.startGame("level1");
					}
					else {
						if(editionModeChanged) {
							editionMode = true;
							editionModeChanged = false;
						}
						menuPanel.switchMenuLevel(1);
					}
				}
			});
		}
		else if(b.getText().equals("Niveaux persos")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(editionModeChanged) {
						editionMode = true;
						editionModeChanged = false;
					}
					menuPanel.switchMenuLevel(2);
				}
			});
		}
		else if(b.getText().equals("Mode édition OFF")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editionMode = !editionMode;
					if(editionMode)
						b.setText("Mode édition ON");
					else
						b.setText("Mode édition OFF");
				}
			});
		}
		else if(b.getText().contains("Level")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!editionMode)
						app.startGame("level"+b.getText().split(" ")[1]);
					else
						app.startEditing("level"+b.getText().split(" ")[1]);
				}
			});
		}
		else if(b.getText().substring(b.getText().length()-2).equals(".p")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!editionMode)
						app.startGame(b.getText());
					else
						app.startEditing(b.getText());
					
				}
			});
		}
		else if(b.getText().equals("Retour")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(editionMode) {
						editionMode = !editionMode;
						editionModeChanged = true;
					}
					menuPanel.switchMenuLevel(3);
				}
			});
		}
		else if(b.getText()=="Quitter") {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
	}

	public void setMenuPanel(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
	}
}
