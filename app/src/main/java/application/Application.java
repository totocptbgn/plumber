package application;

import javax.swing.*;


/**
 *  Classe principale qui initialise les objets et lance le menu.
 */

public class Application extends JFrame {

	public void setPanel(JPanel panel) {
		this.getContentPane().add(panel);
	}
}
