package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import application.Application;
import view.MenuPanel;

public class MenuController {
	private Application app;
	private boolean firstPlay;
	private MenuPanel menuPanel;
	
	public MenuController(Application app) {
		this.app = app;
		this.firstPlay = false;
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
						menuPanel.switchMenuLevel(1);
					}
				}
			});
		}
		else if(b.getText().equals("Niveaux persos")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menuPanel.switchMenuLevel(2);
				}
			});
		}
		else if(b.getText().equals("Edition de niveaux")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.startEditing("level2");
				}
			});
		}
		else if(b.getText().contains("Level")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.startGame("level"+b.getText().split(" ")[1]);
				}
			});
		}
		else if(b.getText().substring(b.getText().length()-2).equals(".p")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					b.setText(b.getText().substring(0, b.getText().length()-2));
					app.startGame(b.getText());
				}
			});
		}
		else if(b.getText().equals("Retour")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
