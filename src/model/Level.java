package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  Classe qui représente le model d'un niveau.
 */

public class Level {

	private String currentState[][];
	private int ressources[]; // C0, O0, L0, L1, T1, T2, T0, T3, F0, F1, F3, F2

	public Level(int id) {
		// Contruction de currentState en lisant le fichier de niveau
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

		// Remplissage de ressources
		ressources = new int[12];
		Arrays.fill(ressources, 0);

		for (int i = 1; i < currentState.length - 1; i++) {
			for (int j = 1; j < currentState[0].length - 1; j++) {
				String tile = currentState[i][j];
				if (!tile.equals(".")) {
					if (tile.charAt(0) != '*') {
						switch (tile) {
							case "C0" : ressources[0]++; break;
							case "O0" : ressources[1]++; break;
							case "L0" : ressources[2]++; break;
							case "L1" : ressources[3]++; break;
							case "T1" : ressources[4]++; break;
							case "T2" : ressources[5]++; break;
							case "T0" : ressources[6]++; break;
							case "T3" : ressources[7]++; break;
							case "F0" : ressources[8]++; break;
							case "F1" : ressources[9]++; break;
							case "F3" : ressources[10]++; break;
							case "F2" : ressources[11]++; break;
						}
						currentState[i][j] = ".";
					}
				}
			}
		}
	}

	public int column() {
		return currentState.length - 2;
	}

	public int line() {
		return currentState[0].length - 2;
	}

	public int[] getRessources() {
		return ressources;
	}

	public String[][] getCurrentState() {
		return currentState;
	}
}
