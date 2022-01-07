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
		this.firstPlay = true;
	}
	
	public void setButtonAction(JButton b) {
		if(b.getText()=="Jouer") {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(firstPlay) {
						firstPlay = !firstPlay;
						app.startGame("Level 1");
					}
					else {
						menuPanel.switchMenu();
					}
				}
			});}
		else if(b.getText().contains("Level")) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					app.startGame(b.getText());
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