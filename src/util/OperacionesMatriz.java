package util;

public class OperacionesMatriz {
	public static void inicializar(int[][] matriz, int valor, int alto, int ancho){
		for (int i = 0; i < alto; i++)
			for (int j = 0; j < ancho; j++)
				matriz[i][j] = valor;
	}
	
	public static void inicializar(float[][] matriz, float valor, int alto, int ancho){
		for (int i = 0; i < alto; i++)
			for (int j = 0; j < ancho; j++)
				matriz[i][j] = valor;
	}
}
