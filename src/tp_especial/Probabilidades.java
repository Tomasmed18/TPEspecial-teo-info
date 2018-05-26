package tp_especial;

import util.OperacionesArreglo;
import util.OperacionesMatriz;

public class Probabilidades {
	
	public static float[][] matrizProbabilidadesConjuntas(ImagenEscalaGrises img1, ImagenEscalaGrises img2){
		int[][] ocurrencias = new int[256][256];
		OperacionesMatriz.inicializar(ocurrencias, 0, 256, 256);
		
		int[] pix1 = img1.getArregloPixeles();
		int[] pix2 = img2.getArregloPixeles();
		int cantidadPixeles = pix1.length;
		
		for (int i = 0; i < cantidadPixeles; i++)
			ocurrencias[pix2[i]][pix1[i]]++;
		
		float[][] probabilidades = new float[256][256];
		for (int i = 0; i < 256; i++)
			for (int j = 0; j < 256; j++)
				probabilidades[i][j] = (float) ocurrencias[i][j] / (float) cantidadPixeles;
		
		return probabilidades;
	}
	
	public static float[][] matrizTransicion(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		int[][] ocurrencias = new int[256][256];
		OperacionesMatriz.inicializar(ocurrencias, 0, 256, 256);
		int[] ocurrenciasPorEntrada = new int[256];
		OperacionesArreglo.inicializarArreglo(ocurrenciasPorEntrada, 0);
		
		int[] in = entrada.getArregloPixeles();
		int[] out = salida.getArregloPixeles();
		int cantidadPixeles = in.length;
		
		for (int i = 0; i < cantidadPixeles; i++){
			ocurrencias[out[i]][in[i]]++;
			ocurrenciasPorEntrada[in[i]]++;
		}
		
		float[][] probabilidades = new float[256][256];
		for (int j = 0; j < 256; j++)
			if (ocurrenciasPorEntrada[j] != 0)
				for (int i = 0; i < 256; i++)
					probabilidades[i][j] = (float) ocurrencias[i][j] / (float) ocurrenciasPorEntrada[j];
		
		return probabilidades;
	}
}
