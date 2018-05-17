package tp_especial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


public class Main {
	
	private static ImagenEscalaGrises imgOriginal;

	private static class ImagenComparable implements Comparable<ImagenComparable>{
		String nombre;
		ImagenEscalaGrises img;
		
		public ImagenComparable(String nombre, ImagenEscalaGrises img){
			this.nombre = nombre;
			this.img = img;
		}
		
		public int compareTo(ImagenComparable arg0) {
			float corr1 = Indicadores.coeficienteCorrelacionCruzada(this.img.getArregloPixeles(), imgOriginal.getArregloPixeles());
			float corr2 = Indicadores.coeficienteCorrelacionCruzada(arg0.getImagen().getArregloPixeles(), imgOriginal.getArregloPixeles());
			if (corr1 > corr2)
				return -1;
			else if (corr1 < corr2)
				return 1;
			else 
				return 0;
		}
		
		public ImagenEscalaGrises getImagen(){
			return this.img;
		}
		
		public String getNombre(){
			return this.nombre;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		imgOriginal = new ImagenEscalaGrises("resources/Will(Original).bmp");
		
		//Carga de las imagenes
		List<ImagenComparable> imagenes = new ArrayList<ImagenComparable>();
		for (int i = 1; i <= 7; i++){
			ImagenEscalaGrises img = new ImagenEscalaGrises("resources/Will_" + i + ".bmp");
			ImagenComparable imgC = new ImagenComparable("Will_" + i + ".bmp", img);
			imagenes.add(imgC);
		}
		
		//Ordenamiento
		Collections.sort(imagenes);
		
		//Coeficiente de correlacion de cada una
		String salida1 = "";
		for (ImagenComparable img: imagenes)
			salida1 += ("Coef. de correlacion de la imagen original con " + img.getNombre() + ": " + 
					Indicadores.coeficienteCorrelacionCruzada(img.getImagen().getArregloPixeles(), imgOriginal.getArregloPixeles()) + String.format("%n"));
		
		System.out.println(salida1);
		
		
		//Indicadores de las imagenes más y menos parecidas
		
		ImagenComparable masParecida = imagenes.get(0);
		ImagenComparable menosParecida = imagenes.get(imagenes.size() - 1);
		
		String salida2 = "";
		salida2 += "La imagen más parecida es: " + masParecida.getNombre() + String.format("%n");;
		salida2 += "Media: " + Indicadores.media(masParecida.getImagen().getArregloPixeles()) + String.format("%n");;
		salida2 += "Desvío: " + Indicadores.desviacionEstandar(masParecida.getImagen().getArregloPixeles()) +String.format("%n");;
		salida2 += "La imagen menos parecida es: " + menosParecida.getNombre() + String.format("%n");;
		salida2 += "Media: " + Indicadores.media(menosParecida.getImagen().getArregloPixeles()) + String.format("%n");;
		salida2 += "Desvío: " + Indicadores.desviacionEstandar(menosParecida.getImagen().getArregloPixeles()) + String.format("%n");;
		
		System.out.println(salida1);
		
		//Histogramas
		Histograma hOriginal = new Histograma("Cantidades de grises de Will(Original).bmp", imgOriginal.getCantidadesGrises(), false);
		Histograma h1 = new Histograma("Cantidades de grises de " + masParecida.getNombre(), masParecida.getImagen().getCantidadesGrises(), false);
		Histograma h2 = new Histograma("Cantidades de grises de " + menosParecida.getNombre(), menosParecida.getImagen().getCantidadesGrises(), false);
		
		//Huffman
		
		CodigoHuffman huffman = new CodigoHuffman();
		
		
		long start = System.currentTimeMillis();
		huffman.codificar(masParecida.getImagen(), "masParecida.txt");
		long stop = System.currentTimeMillis();
		System.out.println("Tiempo de compresión: " + (stop - start) + " ms");
		
		start = System.currentTimeMillis();
		ImagenEscalaGrises decodificada = huffman.decodificar("masParecida.txt");
		stop = System.currentTimeMillis();
		System.out.println("Tiempo de descompresión: " + (stop - start) + " ms");
		
		decodificada.guardarImagen("masParecida_reconstruida.bmp");
		
		
		//archivos de salida
		File inciso1 = new File("Inciso1.txt");
		File inciso2 = new File("Inciso2.txt");
		try {
			FileOutputStream os1 = new FileOutputStream(inciso1);
			FileOutputStream os2 = new FileOutputStream(inciso2);
			
			os1.write(salida1.getBytes());
			os2.write(salida2.getBytes());
			
			os1.close();
			os2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		hOriginal.guardarJPEG("HistogramaOriginal.jpeg");
		h1.guardarJPEG("HistogramaMasParecida.jpeg");
		h2.guardarJPEG("HistogramaMenosParecida.jpeg");
		
	}

}
