package tp_especial;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		ImagenEscalaGrises imgOriginal = new ImagenEscalaGrises("resources/Will(Original).bmp");

		//Histograma h = new Histograma("Cantidades de grises de Will(Original).bmp", imgOriginal.getCantidadesGrises());
		//Histograma h2 = new Histograma("Cantidades de grises de Will_6.bmp", img1.getCantidadesGrises());
		CodigoHuffman cod = new CodigoHuffman();
		cod.codificar(imgOriginal);
		
		int[] decodificado=cod.decodificar(new File("codificacion.txt"));
		int[] pixeles=imgOriginal.getArregloPixeles();
		
		int ancho =imgOriginal.getAncho();
		int alto= imgOriginal.getAlto();
		
		/*
		for (int x = 0; x < ancho; x++)
			for (int y = 0; y < alto; y++){
				if(pixeles[x*alto+y]!=decodificado[x*alto+y])
					System.out.println("pixel color: " + pixeles[x*alto+y] + " decodificado como:  " + decodificado[x*alto+y]);
			}
		*/
		
		
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
		/*
		char test=0x4001;
		test=(char) ( test<<1);
	    char mask= 0x8000;
	    char aux= (char) (test & mask);
	    if(aux==0x8000)
	    	System.out.println(aux);
	    */
	}

}
