package tp_especial;

import util.operacionesArreglo;

public class ImagenEscalaGrises extends Imagen{

	public ImagenEscalaGrises(String nombreArchivo) {
		super(nombreArchivo);
	}
	
	public int getPixel(int x, int y){
		int rgb = super.getPixel(x, y);
		return (rgb) & 0x000000FF; //se está tomando en cuenta solo el canal azul. 
								   //o sea, se supone que la imagen ya está en escala de grises.
	}
	
	public int[] getCantidadesGrises(){
		int[] arr = new int[256];
		operacionesArreglo.inicializarArreglo(arr, 0);
		for (int x = 0; x < this.getAncho(); x++)
			for (int y = 0; y < this.getAlto(); y++)
				arr[this.getPixel(x, y)]++;
		return arr;
	}
	
	public float[] getProbabilidadesGrises(){
		int[] grises = this.getCantidadesGrises();
		float[] probGrises = new float[grises.length];
		int totalGrises = this.getAlto() * this.getAncho();
		for (int i = 0; i < grises.length; i++) 
			probGrises[i]=(float)grises[i]/(float)totalGrises;
		return probGrises;
	}
}
