package tp_especial;

import java.io.File;
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
		for (ImagenComparable img: imagenes)
			System.out.println("Coef. de correlacion de la imagen original con " + img.getNombre() + ": " + 
					Indicadores.coeficienteCorrelacionCruzada(img.getImagen().getArregloPixeles(), imgOriginal.getArregloPixeles()));
		
		ImagenComparable masParecida = imagenes.get(0);
		ImagenComparable menosParecida = imagenes.get(imagenes.size() - 1);
		
		System.out.println("");
		System.out.println("La imagen más parecida es: " + masParecida.getNombre());
		System.out.println("Media: " + Indicadores.media(masParecida.getImagen().getArregloPixeles()));
		System.out.println("Desvío: " + Indicadores.desviacionEstandar(masParecida.getImagen().getArregloPixeles()));
		System.out.println("");
		System.out.println("La imagen menos parecida es: " + menosParecida.getNombre());
		System.out.println("Media: " + Indicadores.media(menosParecida.getImagen().getArregloPixeles()));
		System.out.println("Desvío: " + Indicadores.desviacionEstandar(menosParecida.getImagen().getArregloPixeles()));
		
		//Histogramas
		
		Histograma h1 = new Histograma("Cantidades de grises de " + masParecida.getNombre(), masParecida.getImagen().getCantidadesGrises());
		Histograma h2 = new Histograma("Cantidades de grises de " + menosParecida.getNombre(), menosParecida.getImagen().getCantidadesGrises());
		
		
		//Huffman
		CodigoHuffman huffman = new CodigoHuffman();
		huffman.codificar(masParecida.getImagen(), "masParecida.txt");
		
		ImagenEscalaGrises decodificada = huffman.decodificar("masParecida.txt");
		decodificada.guardarImagen("masParecida_reconstruida.bmp");
		
		
	}

}
