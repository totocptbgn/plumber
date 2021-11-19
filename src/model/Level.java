package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Classe qui représente le model d'un niveau.
 */

public class Level {

	private String currentState[][];
	private int ressources[];

	public Level(int id) {
		try {
			Scanner s = new Scanner(new File("src/data/level" + id + ".p"));
			int height = s.nextInt();
			int width = s.nextInt();

			this.currentState = new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					currentState[i][j] = s.next();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ressources = new int[12];

	}
	public int column() {
		return currentState.length;
	}

	public int line() {
		return currentState[0].length;
	}
}
