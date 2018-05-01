package tp_especial;

import util.operacionesArreglo;

public class ImagenEscalaGrises extends Imagen{

	public ImagenEscalaGrises(String nombreArchivo) {
		super(nombreArchivo);
	}
	
	public int getGris(int x, int y){
		int rgb = this.getPixel(x, y);
		return (rgb) & 0x000000FF; //se está tomando en cuenta solo el canal azul. 
								   //o sea, se supone que la imagen ya está en escala de grises.
	}
	
	public int[] getArregloGrises(){ //va recorriendo la imagen por columnas
		int[] arreglo = new int[this.getAlto() * this.getAncho()];
		int alto = this.getAlto();
		int ancho = this.getAncho();
		for (int x = 0; x < ancho; x++)
			for (int y = 0; y < alto; y++){
				arreglo[x * alto + y] = this.getGris(x, y);
			}
		return arreglo;
	}
	
	public int[] getHistogramaGrises(){
		int[] hist = new int[256];
		operacionesArreglo.inicializarArreglo(hist, 0);
		for (int x = 0; x < this.getAncho(); x++)
			for (int y = 0; y < this.getAlto(); y++){
				hist[this.getGris(x, y)]++;
			}
		
		return hist;
	}
}
