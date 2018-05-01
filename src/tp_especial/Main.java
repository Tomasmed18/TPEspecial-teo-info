package tp_especial;

public class Main {

	public static void main(String[] args) {
		ImagenEscalaGrises img = new ImagenEscalaGrises("Will(Original).bmp");
		System.out.println(img.getAlto());
		System.out.println(img.getAncho());
		for (int x = 0; x < img.getAncho(); x++)
			for (int y = 0; y < img.getAlto(); y++){
				System.out.println("r = " + img.getRed(x, y) + ", g = " + img.getGreen(x, y) + ", b = " + img.getBlue(x, y) + ", gris = " + img.getGris(x, y));
			}
		int[] arr = img.getArregloGrises();
		int min = 0;
		int valMin = 255;
		for (int i = 0; i < arr.length; i++){
			if (arr[i] < valMin && arr[i] != 0){
				min = i;
				valMin = arr[i];
			}
			System.out.println(arr[i]);
		}
		System.out.println("min = " + min + ", valMin = " + valMin);
		
		int[] histograma = img.getHistogramaGrises();
		for (int i = 0; i<histograma.length; i++){
			System.out.println("Gris[" + i + "] = " + histograma[i]);
		}
	}

}
