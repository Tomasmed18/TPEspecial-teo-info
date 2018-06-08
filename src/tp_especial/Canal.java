package tp_especial;

public class Canal {

	private float[][] matrizTransicion;
	private float[][] matrizTransicionAcumulada;
	public Canal(ImagenEscalaGrises entrada, ImagenEscalaGrises salida) {
		
		this.matrizTransicion = Probabilidades.matrizTransicion(entrada, salida); //esto hay que pasarla acumulada
		this.matrizTransicionAcumulada = util.OperacionesMatriz.acumularMatriz(matrizTransicion, 16, 16);
		
	}
	
	
	public int transmitirSimbolo(int simb) {
		
		int tonoGris = simb/17;
		double prob = Math.random();
		int fila = 0;
		while (prob > matrizTransicionAcumulada[fila][tonoGris]) {
			fila++;
			
		}
		return fila*17; //se retornaria el simbolo y que retorna de chequear la columna "tono Gris" de la matriz de transicion
	}
}
