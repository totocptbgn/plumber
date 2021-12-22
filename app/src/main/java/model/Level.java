package model;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  Classe qui représente le model d'un niveau.
 */

public class Level {

	private String currentState[][];
	private String colorState[][];
	private int ressources[]; // C0, O0, L0, L1, T1, T2, T0, T3, F0, F1, F3, F2

	public Level(int id) {
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
					currentState[i][j] = ".";
				}
			}
		}
	}

	public void updateColor() {
		// TODO: remplir colorState
		this.colorState = new String[this.currentState.length][this.currentState[0].length];
		ArrayList<Integer[]> colorChecked = new ArrayList<Integer[]>();
		// On effectue les changements depuis la première et la dernière ligne
		for(int i = 0; i < currentState.length; i+=currentState.length-1) {
			for(int j = 0; j < currentState[0].length; j++) {
				// Est-ce qu'on a déjà vérifié cette source?
				for(Integer[] alreadyChecked: colorChecked) {
					if(alreadyChecked[0]==i&&alreadyChecked[1]==j) {
						continue;
					}
				}
				// Est-ce que cet élément est une source?
				if(currentState[i][j].contains("R") || currentState[i][j].contains("G")
						|| currentState[i][j].contains("B") || currentState[i][j].contains("Y")) {
					String color = currentState[i][j].split("")[0];
					ArrayList<Integer[]> toCheck = new ArrayList<Integer[]>();
					Integer[] coordinatesToCheck = {new Integer(i), new Integer(j)};
					toCheck.add(coordinatesToCheck);
					// Tant qu'on a un prochain élément connecté à la source à vérifier
					while(!toCheck.isEmpty()) {
						for(Integer[] toRemove : toCheck) {
							toCheck.remove(toRemove);
							// Est-ce qu'on a déjà vérifié cette tile?
							boolean hasAlreadyBeenChecked = false;
							for(Integer[] alreadyChecked: colorChecked) {
								if(alreadyChecked[0]==toRemove[0]&&alreadyChecked[1]==toRemove[1]) { //TODO: tester si c'est une tile OVER
									hasAlreadyBeenChecked = true;
									break;
								}
							}
							if(hasAlreadyBeenChecked) {
								continue;
							}
							colorState[toRemove[0]][toRemove[1]].concat(color);
							//TODO: choisir les prochaines tiles à check
						}
					}
				}
				else continue;
				//this.colorState[0][0].concat("");
			}
		}
	}

	public boolean isCompleted() {
		//TODO: detecter la victoire
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
