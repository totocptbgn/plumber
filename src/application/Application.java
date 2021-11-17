package application;
import java.awt.Graphics;

import javax.swing.*;

import view.DrawPanel;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 */

public class Application extends JFrame {

	// Main
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Plumber");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainFrame.setPreferredSize(new Dimension(1080,720));
		
		// Donner à DrawPanel un "contrôleur" en paramètre?
		DrawPanel pnl = new DrawPanel();
		mainFrame.getContentPane().add(pnl);
		
		Graphics g = pnl.getGraphics();
		// Construire le menu ici sur g // Faire un autre panel pour le menu?

		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
