import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  Panel utilisé pour dessiner dans la frame qui contient une image.
 */

public class DrawPanel extends JPanel {

    private BufferedImage img;

    public DrawPanel () {
        img = new BufferedImage(MainFrame.WIDTH, MainFrame.HEIGHT, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public Graphics getGraphics() {
        return img.getGraphics();
    }
}
