package tp_especial;

public class Main {

	public static void main(String[] args) {
		ImagenEscalaGrises imgOriginal = new ImagenEscalaGrises("resources/Will(Original).bmp");
		ImagenEscalaGrises img1 = new ImagenEscalaGrises("resources/Will_6.bmp");
		Imagen verde = new Imagen("Verde.bmp");
		
		System.out.println("Coeficiente de correlación cruzada: " 
							+ Indicadores.coeficienteCorrelacionCruzada(imgOriginal.getArregloPixeles(), img1.getArregloPixeles()));
		Histograma h = new Histograma("Cantidades de grises de Will(Original).bmp", imgOriginal.getCantidadesGrises());
		int[] histograma = imgOriginal.getCantidadesGrises();
		for (int i = 0; i<histograma.length; i++)
			if (histograma[i] != 0)
				System.out.println("Gris[" + i + "] = " + histograma[i]);
		Histograma h2 = new Histograma("Cantidades de grises de Will_6.bmp", img1.getCantidadesGrises());
		
		System.out.println(verde.getRed(0, 1));
		System.out.println(verde.getBlue(0, 1));
		System.out.println(verde.getGreen(0, 1));
		/**
		System.out.println(img.getAlto());
		System.out.println(img.getAncho());
		for (int x = 0; x < img.getAncho(); x++)
			for (int y = 0; y < img.getAlto(); y++){
				System.out.println("r = " + img.getRed(x, y) + ", g = " + img.getGreen(x, y) + ", b = " + img.getBlue(x, y) + ", gris = " + img.getPixel(x, y));
			}
		int[] arr = img.getArregloPixeles();
		int min = 0;
		int valMin = 255;
		for (int i = 0; i < arr.length; i++){
			if (arr[i] < valMin && arr[i] != 0){
				min = i;
				valMin = arr[i];
			}
			System.out.println(arr[i]);
		}
		System.out.println("min = " + min + ", valMin = " + valMin);
		
		int[] histograma = img.getCantidadesGrises();
		for (int i = 0; i<histograma.length; i++){
			System.out.println("Gris[" + i + "] = " + histograma[i]);
		}
		
		System.out.println("Media = " + Indicadores.media(arr));
		System.out.println("Desvío estandar = " + Indicadores.desviacionEstandar(arr));
		**/
	}

}
