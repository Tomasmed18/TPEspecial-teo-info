package tp_especial;

import util.OperacionesArreglo;

public class Canales {
	public static float ruido(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(entrada, salida); //P(Y/X)
		
		float sumatoria = 0;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if (matCondicional[i][j] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[i][j]));
		
		return (sumatoria * (float) (-3.322)); //se multiplica por 3.322 para pasar de log10 a log2
	} 
	
	public static float perdida(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(salida, entrada); // P(X/Y)
		
		float sumatoria = 0;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if (matCondicional[i][j] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[i][j]));
		
		return (sumatoria * (float) (-3.322)); //se multiplica por 3.322 para pasar de log10 a log2
	}
	
	public static float informacionMutua(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[] probX = new float[16]; // P(X)
		float[] probY = new float[16]; // P(Y)
		OperacionesArreglo.inicializarArreglo(probX, 0);
		OperacionesArreglo.inicializarArreglo(probY, 0);
		
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++){
				probX[j] += matConjunta[i][j];
				probY[i] += matConjunta[i][j];
			}
		
		float sumatoria = 0;
		
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if ((matConjunta[i][j] != 0) && (probX[j] != 0) && (probY[i] != 0)){ //para que de bien el logaritmo
					float l = matConjunta[i][j] / (probX[j] * probY[i]);
					sumatoria += matConjunta[i][j] * Math.log10(l);
				}
		
		return sumatoria * (float) (3.322); //se multiplica por 3.322 para pasar de log10 a log2
	}
}
