package Interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Juan Pablo
 */
public class PanelMesasInicio extends JPanel {

    private final JLabel lblTiempo, lblPersonas, picLabel;
    private final JPanel pnlTexto;
    private ImageIcon icon;
    private boolean uso;

    public PanelMesasInicio(int numero, int espacioPersonas, String tiempo, String path, boolean uso) {
        this.uso = uso;
        setLayout(new GridLayout(2, 1));
        setBorder(new CompoundBorder(new EmptyBorder(0, 5, 10, 5), new TitledBorder("# " + numero)));
        setBackground(Color.white);

        icon = new ImageIcon("src/recursos/" + path + ".jpg");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        picLabel = new JLabel(icon, JLabel.CENTER);
        add(picLabel);

        pnlTexto = new JPanel();
        pnlTexto.setLayout(new GridLayout(2, 1));
        pnlTexto.setBackground(Color.white);
        add(pnlTexto);

        lblTiempo = new JLabel("Tiempo: " + tiempo, JLabel.CENTER);
        lblTiempo.setFont(new Font("Arial", 10, 10));
        pnlTexto.add(lblTiempo);

        lblPersonas = new JLabel("Persona: " + espacioPersonas, JLabel.CENTER);
        lblPersonas.setFont(new Font("Arial", 10, 10));
        pnlTexto.add(lblPersonas);

    }

    public void setLblTiempo(String tiempo) {
        lblTiempo.setText("Tiempo: " + tiempo);
        if (uso == false) {
            lblTiempo.setVisible(false);
        } else {
            lblTiempo.setVisible(true);
        }
    }

    public void setLblPersonas(int personas) {
        lblPersonas.setText(((personas >= 0) ? "Persona: " + personas : ""));
    }

    public void setImageIcon(String path) {
        icon = new ImageIcon("src/recursos/" + path + ".jpg");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        picLabel.setIcon(icon);

    }

    public void setIdMesa(int idMesa) {
        setBorder(new CompoundBorder(new EmptyBorder(0, 5, 10, 5), new TitledBorder((idMesa >= 0) ? "# " + idMesa : " ")));
    }

    public void setUso(boolean uso) {
        this.uso = uso;
    }

}
