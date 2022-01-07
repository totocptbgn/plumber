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
		for(int i = 0; i < colorState.length; i++)
			for(int j = 0; j < colorState[0].length; j++)
				colorState[i][j]="";
		
		ArrayList<Integer[]> toCheck = new ArrayList<Integer[]>();
		// On enregistre les points de départs depuis la première et la dernière ligne
		for(int i = 0; i < currentState.length; i+=currentState.length-1) {
			for(int j = 1; j < currentState[0].length-1; j++) {
				// Est-ce que la tile hors des cases
				if(currentState[i][j].equals("X"))
					continue;
				else toCheck.add(new Integer[]{i, j});
			}
		}
		// On enregistre les points de départs depuis la première et la dernière colonne
		for(int i = 1; i < currentState.length-1; i++) {
			for(int j = 0; j < currentState[0].length; j+=currentState[0].length-1) {
				// Est-ce que la tile est hors des cases
				if(currentState[i][j].equals("X"))
					continue;
				else toCheck.add(new Integer[]{i, j});
			}
		}
		
		// On part de chaque source pour colorer les tuyaux
		for(Integer[] source : toCheck) {
			String color = "";
			if(colorState[source[0]][source[1]].equals("")) {
				if(currentState[source[0]][source[1]].contains("R")) color = "Red";
				else if(currentState[source[0]][source[1]].contains("G")) color = "Green";
				else if(currentState[source[0]][source[1]].contains("B")) color = "Blue";
				else if(currentState[source[0]][source[1]].contains("Y")) color = "Yellow";
				colorState[source[0]][source[1]]=color;
				int nextX = -1;
				int nextY = -1;
				if(currentState[source[0]][source[1]].contains("0")) {
					nextX = source[0]-1;
					nextY = source[1];
				}
				else if(currentState[source[0]][source[1]].contains("1")) {
					nextX = source[0];
					nextY = source[1]+1;
				}
				else if(currentState[source[0]][source[1]].contains("2")) {
					nextX = source[0]+1;
					nextY = source[1];
				}
				else if(currentState[source[0]][source[1]].contains("3")) {
					nextX = source[0]-1;
					nextY = source[1];
				}
				if(nextX<1||nextX>currentState.length-1 ||
						nextY<1||nextY>currentState[0].length-1) {
					continue;
					
				}
				colorPipe(color, source, new Integer[]{nextX, nextY});
			}
			else continue;
		}
	}
	
	public void colorPipe(String color, Integer[] previous, Integer[] current) {
		// Est-ce qu'il n'y a pas de tuyaux ?
		if(currentState[current[0]][current[1]].equals(".")) return;
		// Cas particulier du tuyau OVER
		if(currentState[current[0]][current[1]].contains("O")) {
			
		}
		// Est-ce que le tuyau est connecté au précédent ?
		else if(!arePipesConnected(previous, current)) return;
		else {
			// Est-ce que la couleur est déjà établi pour ce tuyau?
			if(!colorState[current[0]][current[1]].equals("")) return;
			else {
				
			}
		}
	}

	private boolean arePipesConnected(Integer[] previous, Integer[] current) {
		if(currentState[current[0]][current[1]].contains("C"))
			return true;
		if(current[0] == previous[0]-1 && current[1] == previous[1]) { // le tuyau précédent est au sud
			if(currentState[current[0]][current[1]].contains("L")) {
				if(currentState[current[0]][current[1]].contains("1"))
					return false;
				else return true;
			}
			if(currentState[current[0]][current[1]].contains("T")) {
				if(currentState[current[0]][current[1]].contains("0") ||
						currentState[current[0]][current[1]].contains("3"))
					return false;
				else return true;
			}
			if(currentState[current[0]][current[1]].contains("F")) {
				if(currentState[current[0]][current[1]].contains("3"))
					return false;
				else return true;
			}
		}
		if(current[0] == previous[0] && current[1] == previous[1]-1) { // le tuyau précédent est à l'est
			if(currentState[current[0]][current[1]].contains("L")) {
				if(currentState[current[0]][current[1]].contains("0"))
					return false;
				else return true;
			}
			if(currentState[current[0]][current[1]].contains("T")) {
				if(currentState[current[0]][current[1]].contains("2") ||
						currentState[current[0]][current[1]].contains("3"))
					return false;
				else return true;
			}
			if(currentState[current[0]][current[1]].contains("F")) {
				if(currentState[current[0]][current[1]].contains("2"))
					return false;
				else return true;
			}
		}
		if(current[0] == previous[0]+1 && current[1] == previous[1]) { // le tuyau précédent est au nord
			
		}
		if(current[0] == previous[0] && current[1]+1 == previous[1]) { // le tuyau précédent est à l'ouest
			
		}
		return false;
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
