package tp_especial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Histograma {
	JFreeChart chart;
	
	public Histograma(String titulo, int[] valores, boolean visible){
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (int i = 0; i < valores.length; i++)
			if (valores[i] != 0)
				data.addValue((double) valores[i], Integer.toString(i), "GRISES");
	

		chart = ChartFactory.createBarChart3D(
				titulo,       
				"Tonalidades de grises",               // domain axis label
				"Cantidades de píxeles",                  // range axis label
				data,                  
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,                     // tooltips?
				false                     // URLs?
				        );
		Plot plot = chart.getPlot();
		
		ChartFrame frame = new ChartFrame("Histograma", chart);
		frame.pack();
		frame.setVisible(visible);

	}
	
	public void guardarJPEG(String nombre) {
		File archivo = new File(nombre);
		try {
			ChartUtilities.saveChartAsJPEG(archivo, chart, 1000, 800);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
