package Interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import models.Grafico;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class PanelGraficoIndividual extends JPanel {

    private final JLabel lblTorta;
    private final int width = Variables.width / 2 - 20;
    private final int heigth = (Variables.heigth - 130) / 2 - 30;
    private final JPanel panelGraficoTorta;
    private int x = 1;

    public PanelGraficoIndividual(int pos, boolean opcion, Grafico graficos) {
        x = 1;
        setBackground(Color.white);
        setLayout(new GridLayout());
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("")));

        panelGraficoTorta = new JPanel();
        panelGraficoTorta.setBackground(Color.white);
        add(panelGraficoTorta);

        lblTorta = new JLabel();
        panelGraficoTorta.add(lblTorta);

        switch (pos) {
            case 0:
            case 1: {
                JFreeChart barra = null;
                DefaultCategoryDataset datos;
                datos = new DefaultCategoryDataset();
                graficos.getMapa().entrySet().forEach((entry) -> {
                    datos.setValue(entry.getValue(), entry.getKey(), "");
                });

                barra = ChartFactory.createBarChart(graficos.getNombre(), graficos.getValorX(), graficos.getValorY(), datos, PlotOrientation.VERTICAL, true, true, true);
                barra.getPlot().setBackgroundPaint(Color.WHITE);

                BufferedImage graficoBarra = barra.createBufferedImage(width, heigth);
                lblTorta.setSize(panelGraficoTorta.getSize());
                lblTorta.setIcon(new ImageIcon(graficoBarra));
                panelGraficoTorta.updateUI();
                break;
            }
            case 4:
                DefaultPieDataset porciones;
                JFreeChart torta;
                porciones = new DefaultPieDataset();
                porciones.setValue("A", 4550);
                porciones.setValue("B", 400);
                porciones.setValue("C", 300);
                porciones.setValue("D", 350);
                torta = ChartFactory.createPieChart3D("Ejemplo", porciones, true, true, false);
                BufferedImage graficoTorta = torta.createBufferedImage(width, heigth);
                lblTorta.setSize(panelGraficoTorta.getSize());
                lblTorta.setIcon(new ImageIcon(graficoTorta));
                panelGraficoTorta.updateUI();
                break;
            case 2:
            case 3: {
                XYSeries series;
                XYSeriesCollection datos;
                JFreeChart linea = null;
                series = new XYSeries("Comportamiento " + graficos.getNombre());

                graficos.getMapa().entrySet().forEach((entry) -> {
                    series.add(x++, entry.getValue());
                });

                datos = new XYSeriesCollection(series);
                linea = ChartFactory.createXYLineChart("Grafico " + graficos.getNombre(), graficos.getValorX(), graficos.getValorY(), datos, PlotOrientation.VERTICAL, true, true, false);
                XYPlot plot = linea.getXYPlot();
                final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
                xAxis.setTickUnit(new NumberTickUnit(1));

                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                renderer.setSeriesPaint(0, Color.RED);
                renderer.setSeriesStroke(0, new BasicStroke(0.5f));
                plot.setRenderer(renderer);
                plot.setBackgroundPaint(Color.white);
                plot.setRangeGridlinesVisible(true);
                plot.setRangeGridlinePaint(Color.BLACK);
                plot.setDomainGridlinesVisible(true);
                plot.setDomainGridlinePaint(Color.BLACK);

                //linea.getLegend().setFrame(BlockBorder.NONE);
                BufferedImage graficoLinea = linea.createBufferedImage(width, heigth);
                lblTorta.setSize(panelGraficoTorta.getSize());
                lblTorta.setIcon(new ImageIcon(graficoLinea));
                panelGraficoTorta.updateUI();
                break;
            }
            default:
                break;
        }
    }
}
