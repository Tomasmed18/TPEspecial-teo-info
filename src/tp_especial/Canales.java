package tp_especial;

public class Canales {
	public static float ruido(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(entrada, salida); //P(Y/X)
		
		float sumatoria = 0;
		for (int i = 0; i < 256; i++)
			for (int j = 0; j < 256; j++)
				if (matCondicional[i][j] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[i][j]));
		
		sumatoria = sumatoria * (float) (-3.322); //se multiplica por 3.322 para pasar de log10 a log2
				
		return sumatoria;
	} 
	
	public static float perdida(ImagenEscalaGrises entrada, ImagenEscalaGrises salida){
		float[][] matConjunta = Probabilidades.matrizProbabilidadesConjuntas(entrada, salida); //P(X, Y)
		float[][] matCondicional = Probabilidades.matrizTransicion(salida, entrada); // P(X/Y)
		
		float sumatoria = 0;
		for (int i = 0; i < 256; i++)
			for (int j = 0; j < 256; j++)
				if (matCondicional[i][j] != 0)
					sumatoria += (matConjunta[i][j] * Math.log10(matCondicional[i][j]));
		
		sumatoria = sumatoria * (float) (-3.322); //se multiplica por 3.322 para pasar de log10 a log2
				
		return sumatoria;
	}
}
