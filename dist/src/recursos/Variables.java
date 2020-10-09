package recursos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Juan Pablo
 */
public class Variables {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) screenSize.getWidth();
    public static int heigth = (int) screenSize.getHeight();
    public static Color white = Color.white;
    public static Color rojo = new Color(255, 82, 82);
    public static Color sombra = new Color(189, 189, 189);

}
