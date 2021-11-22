/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package plumber;

import application.Application;
import controller.LevelController;
import model.Level;
import view.DrawPanelLevel;
import javax.swing.*;

public class App {
    
    public static void main(String[] args) {

		
        Application mainFrame = new Application();
		mainFrame.setTitle("Plumber");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		DrawPanelLevel panelLevel1 = new DrawPanelLevel(new Level(2));
		mainFrame.setPanel(panelLevel1);
		LevelController controller1 = new LevelController(panelLevel1);

		mainFrame.pack();
		mainFrame.setVisible(true);
    }

	public Object getGreeting() {
		return "Graddle Test Greetings!";
	}
}
