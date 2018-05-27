package tp_especial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


public class Main {
	
	public static void main(String[] args) {
		ImagenEscalaGrises img1 = new ImagenEscalaGrises("resources/Will(Original).bmp");
		ImagenEscalaGrises img2 = new ImagenEscalaGrises("resources/Will_1.bmp");
		
		float[][] mat = Probabilidades.matrizProbabilidadesConjuntas(img1, img2);
		float suma = 0;
		for (int i = 0; i < 256; i++){
			for (int j = 0; j < 256; j++){
				if (mat[i][j] != 0)
					System.out.println(mat[i][j] + " en " + i + " " + j);
				suma += mat[i][j];
			}
			
		}
		System.out.println("Suma = " + suma);
		
		
		mat = Probabilidades.matrizTransicion(img1, img2);
		suma = 0;
		for (int i = 0; i < 256; i++){
			for (int j = 0; j < 256; j++){
				if (mat[i][j] != 0)
					System.out.println(mat[i][j] + " en " + i + " " + j);
				suma += mat[i][j];
			}
		}
		System.out.println("Suma = " + suma);
		
		float ruido = Canales.ruido(img1, img2);
		System.out.println("Ruido = " + ruido);
		float perdida = Canales.perdida(img1, img2);
		System.out.println("Pérdida = " + perdida);
		
		System.exit(0);
	}

}
