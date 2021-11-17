package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Classe qui représente le model d'un niveau.
 */

public class Level {

	public final int id;
	public String board [][];

	public Level(int id) {
		this.id = id;
		try {
			Scanner s = new Scanner(new File("src/data/level" + id + ".p"));
			int height = s.nextInt();
			int width = s.nextInt();

			this.board = new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					board[i][j] = s.next();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
