import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    // Renvoie l'image des textures `pipes.gif` en entier.
    private static BufferedImage getTextureImage() {
        try {
            BufferedImage texture = ImageIO.read(new File("src/data/pipes.gif"));
            return texture;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Récupère puis découpe l'image des textures pour renvoyer l'élément désiré.
    private static BufferedImage getTextureTile(int column, int line) {
        return getTextureImage().getSubimage(column * 140, line * 140, 120, 120);
    }

    public static BufferedImage getTextureTile(Color c, PipeType t, Orientation o) {
        // On met les lignes et les colonnes en fonction du bloc désiré.
        int column = -1;
        int line = -1;

        switch (c) {
            case WHITE -> line = 0;
            case RED -> line = 1;
            case GREEN -> line = 2;
            case BLUE -> line = 3;
            case YELLOW -> line = 4;
            case BLACK -> line = 5;
        }
        switch (t) {
            case SOURCE -> column = 0;
            case LINE -> column = 1;
            case OVER -> column = 2;
            case TURN -> column = 3;
            case FORK -> column = 4;
            case CROSS -> column = 5;
        }

        return rotateImage(getTextureTile(column, line), o);
    }

    public static BufferedImage getTextureTile(Special s, Orientation o) {
        int column = -1;

        switch (s) {
            case DARKBROWN -> column = 0;
            case LIGHTBROWN -> column = 1;
            case BLACK -> column = 2;
            case CORNER -> column = 3;
            case BORDER -> column = 4;
            case DOTS -> column = 5;
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
            case EAST -> {
                angle = 90;
            }
            case SOUTH -> {
                angle = 180;
            }
            case WEST -> {
                angle = 270;
            }
        }

        BufferedImage buffer = new BufferedImage(120, 120, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffer.createGraphics();

        g.rotate(Math.toRadians(angle), 60, 60);
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return buffer;
    }
}

// Couleur des tuyaux.
enum Color {
    WHITE, RED, GREEN, BLUE, YELLOW, BLACK
}

// Type des tuyaux.
enum PipeType {
    SOURCE, LINE, OVER, TURN, FORK, CROSS
}

// Autres textures de la dernière ligne.
enum Special {
    DARKBROWN, LIGHTBROWN, BLACK, CORNER, BORDER, DOTS
}

enum Orientation {
    NORTH, EAST, SOUTH, WEST
}
