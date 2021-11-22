package view;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

	// Renvoie l'image des textures `pipes.gif` en entier.
	private static BufferedImage getTextureImage() {
		try {
			return ImageIO.read(new File("src/main/java/data/pipes.gif"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Récupère puis découpe l'image des textures pour renvoyer l'élément désiré.
	private static BufferedImage getTextureTile(int column, int line) {
		return getTextureImage().getSubimage(column * 140, line * 140, 120, 120);
	}

	public static BufferedImage getTextureTile(Color c, PipeType t) {
		return getTextureTile(c, t, Orientation.NORTH);
	}

	public static BufferedImage getTextureTile(Special s) {
		return getTextureTile(s, Orientation.NORTH);
	}

	public static BufferedImage getTextureTile(Color c, PipeType t, Orientation o) {
		// On met les lignes et les colonnes en fonction du bloc désiré.
		int column = -1;
		int line = -1;

		switch (c) {
			case WHITE : line = 0; break;
			case RED : line = 1; break;
			case GREEN : line = 2; break;
			case BLUE : line = 3; break;
			case YELLOW : line = 4; break;
			case DARKGREY: line = 5; break;
		}
		switch (t) {
			case SOURCE : column = 0; break;
			case LINE : column = 1; break;
			case OVER : column = 2; break;
			case TURN : column = 3; break;
			case FORK : column = 4; break;
			case CROSS : column = 5; break;
		}

		return rotateImage(getTextureTile(column, line), o);
	}

	public static BufferedImage getTextureTile(Special s, Orientation o) {
		int column = -1;

		switch (s) {
			case DARKBROWN : column = 0; break;
			case LIGHTBROWN : column = 1; break;
			case BLACK : column = 2; break;
			case CORNER : column = 3; break;
			case BORDER : column = 4; break;
			case DOTS : column = 5; break;
		}

		return rotateImage(getTextureTile(column, 6), o);
	}

	private static BufferedImage rotateImage(BufferedImage img, Orientation o) {
		// On renvoie si on a pas besoin de faire une rotation
		if (o == Orientation.NORTH) {
			return img;
		}

		// On fait la rotation de l'image.
		int angle = 0;
		switch (o) {
			case EAST : {
				angle = 90;
				break;
			}
			case SOUTH : {
				angle = 180;
				break;
			}
			case WEST : {
				angle = 270;
				break;
			}
		}

		BufferedImage buffer = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();

		g.rotate(Math.toRadians(angle), 60, 60);
		g.drawImage(img, 0, 0, null);
		g.dispose();

		return buffer;
	}
}

