package tp_especial;

public class Canal {

	private float[][] matrizTransicion;
	public Canal(ImagenEscalaGrises entrada, ImagenEscalaGrises salida) {
		
		this.matrizTransicion=Probabilidades.matrizTransicion(entrada, salida); //esto hay que pasarla acumulada
		
		
	}
	
	
	public int transmitirSimbolo(int simb) {
		
		return 0;
	}
}
