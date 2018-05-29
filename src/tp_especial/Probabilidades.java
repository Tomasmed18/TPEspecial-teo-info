package tp_especial;

import util.OperacionesArreglo;
import util.OperacionesMatriz;

public class Probabilidades {
	
	public static float[][] matrizProbabilidadesConjuntas(ImagenEscalaGrises img1, ImagenEscalaGrises img2){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		
		int[] pix1 = img1.getArregloPixeles();
		int[] pix2 = img2.getArregloPixeles();
		int cantidadPixeles = pix1.length;
		
		for (int i = 0; i < cantidadPixeles; i++)
			ocurrencias[pix2[i]/17][pix1[i]/17]++; // se divide por 17 para pasar de 256 tonos de gris a 16
		
		float[][] probabilidades = new float[16][16];
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				probabilidades[i][j] = (float) ocurrencias[i][j] / (float) cantidadPixeles;
		
		return probabilidades;
	}
	
	public static float[][] matrizTransicion(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		int[][] ocurrencias = new int[16][16];
		OperacionesMatriz.inicializar(ocurrencias, 0, 16, 16);
		int[] ocurrenciasPorEntrada = new int[16];
		OperacionesArreglo.inicializarArreglo(ocurrenciasPorEntrada, 0);
		
		int[] in = entrada.getArregloPixeles();
		int[] out = salida.getArregloPixeles();
		int cantidadPixeles = in.length;
		
		for (int i = 0; i < cantidadPixeles; i++){
			ocurrencias[out[i]/17][in[i]/17]++;
			ocurrenciasPorEntrada[in[i]/17]++;
		}
		
		float[][] probabilidades = new float[16][16];
		for (int j = 0; j < 16; j++)
			if (ocurrenciasPorEntrada[j] != 0)
				for (int i = 0; i < 16; i++)
					probabilidades[i][j] = (float) ocurrencias[i][j] / (float) ocurrenciasPorEntrada[j];
		
		return probabilidades;
	}
}
