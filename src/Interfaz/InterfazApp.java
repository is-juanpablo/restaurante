package Interfaz;

import Controlador.Controlador;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Juan Pablo
 */
public class InterfazApp extends JFrame {

    private final BarTopPanel pnlTopBar;
    private final JPanel frameSeleccionado;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    private static Controlador ctrl;
    private final CardLayout cardLayout;
    private static InterfazApp interfazApp;

    public InterfazApp(Controlador ctrl) {
        InterfazApp.ctrl = ctrl;
        //ctrl.conexionSQL();
        ctrl.consultaBalance();
        setVisible(true);
        setResizable(false);
        getContentPane().setLayout(null);
        setTitle("  Software Restaurante  ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        pnlTopBar = new BarTopPanel(ctrl);
        pnlTopBar.setBounds(0, 0, (int) width, 50);
        getContentPane().add(pnlTopBar);

        frameSeleccionado = new JPanel();
        frameSeleccionado.setLayout(new CardLayout());
        frameSeleccionado.setBounds(0, 50, (int) width, (int) height - 50);
        getContentPane().add(frameSeleccionado);

        frameSeleccionado.add(new PanelInicio(ctrl));

        cardLayout = (CardLayout) frameSeleccionado.getLayout();
        cardLayout.show(frameSeleccionado, "alta");
        SwingUtilities.updateComponentTreeUI(frameSeleccionado);
        this.repaint();
    }

    /*
    public static void main(String[] args) {
        interfazApp = new InterfazApp(new Controlador());
        ctrl.conectarInterfaz(interfazApp);
    }*/
    public void conectarPanel(JPanel panel) {
        frameSeleccionado.removeAll();
        frameSeleccionado.add(panel, "alta");
        cardLayout.show(frameSeleccionado, "alta");
        SwingUtilities.updateComponentTreeUI(frameSeleccionado);
        this.repaint();
    }
}
