package tp_especial;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Histograma {
	
	public Histograma(String titulo, int[] valores){
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (int i = 0; i < valores.length; i++)
			if (valores[i] != 0)
				data.addValue((double) valores[i], Integer.toString(i), "GRISES");
	

		JFreeChart chart = ChartFactory.createBarChart(
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
		frame.setVisible(true);

	}

}
