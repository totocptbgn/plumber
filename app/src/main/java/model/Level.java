package model;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  Classe qui représente le model d'un niveau.
 */

public class Level {

	private boolean editionMode;				// Si vrai, le plateau n'est pas vidée, et la reserve reste vide.

	private String currentState[][];			// Listes des états des tuyaux du plateau
	private String colorState[][];				// Listes des couleurs des tuyaux du plateau
	private int ressources[]; 					// Liste des tuyaux : C0, O0, L0, L1, T1, T2, T0, T3, F0, F1, F3, F2

	public Level(int id, boolean editionMode) {
		this.editionMode = editionMode;

		// Contruction de currentState en lisant le fichier de niveau
		try {
			Scanner s = new Scanner(new File("src/main/java/data/level" + id + ".p"));
			int height = s.nextInt();
			int width = s.nextInt();

			if (height > 9 || width > 9) {
				throw new Exception("Width and heigth level are limited to 9.");
			}

			this.currentState = new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					currentState[i][j] = s.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Remplissage de ressources
		ressources = new int[12];
		Arrays.fill(ressources, 0);

		// Si on est en mode édition, alors on ne vide pas le plateau dans la ressource
		if (editionMode) {
			return;
		}

		for (int i = 1; i < currentState.length - 1; i++) {
			for (int j = 1; j < currentState[0].length - 1; j++) {
				String tile = currentState[i][j];
				if (!tile.equals(".") && tile.charAt(0) != '*') {
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
				}
			}
		}
	}

	public void updateColor() {
		// TODO: remplir colorState
	}

	public boolean isCompleted() {
		// TODO: detecter la victoire
		return false;
	}

	public int column() {
		return currentState[0].length - 2;
	}

	public int line() {
		return currentState.length - 2;
	}

	public int[] getRessources() {
		return ressources;
	}

	public String[][] getCurrentState() {
		return currentState;
	}
}
