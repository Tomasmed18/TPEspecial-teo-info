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
	
	public static float[][] acumularMatriz(float[][] matriz,int alto, int ancho){
		
		float[][] acumulada=new float[alto][ancho];
			for (int j = 0; j < ancho; j++) {
				float acumColumna=0.0f;
				for (int i = 0; i < alto; i++) {
					acumColumna+=matriz[i][j];
					acumulada[i][j]=acumColumna;
					
					
				}
				acumulada[alto-1][j]=1.0f;
			
		}
		
		return acumulada;
	}
}
