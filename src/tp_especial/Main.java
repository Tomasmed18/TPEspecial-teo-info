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
		ImagenEscalaGrises img2 = new ImagenEscalaGrises("resources/Will_Canal2.bmp");
		ImagenEscalaGrises img8 = new ImagenEscalaGrises("resources/Will_Canal8.bmp");
		ImagenEscalaGrises img10 = new ImagenEscalaGrises("resources/Will_Canal10.bmp");
		
		//---------Inciso 1
		String inciso1 = "";
		
		inciso1 += ("Ruido canal 2: " + Canales.ruido(img1, img2)) + String.format("%n");
		inciso1 += ("Ruido canal 8: " + Canales.ruido(img1, img8)) + String.format("%n");
		inciso1 += ("Ruido canal 10: " + Canales.ruido(img1, img10)) + String.format("%n");
		
		inciso1 += ("Pérdida canal 2: " + Canales.perdida(img1, img2)) + String.format("%n");
		inciso1 += ("Pérdida canal 8: " + Canales.perdida(img1, img8)) + String.format("%n");
		inciso1 += ("Pérdida canal 10: " + Canales.perdida(img1, img10)) + String.format("%n");
		
		inciso1 += ("Información Mutua canal 2: " + Canales.informacionMutua(img1, img2)) + String.format("%n");
		inciso1 += ("Información Mutua 8: " + Canales.informacionMutua(img1, img8)) + String.format("%n");
		inciso1 += ("Información Mutua canal 10: " + Canales.informacionMutua(img1, img10)) + String.format("%n");
		
		System.out.println(inciso1);
		
		
		//---------Inciso 2
		String inciso2 = "";
		
		int i = 10;
		while (i <= 100000){
			inciso2 += ("Ruido con " + i + " iteraciones: " + 
					Canales.ruidoPorSimulacion(new Fuente(img1), new Canal(img1, img2), i)) + String.format("%n");
			inciso2 += ("Pérdida con " + i + " iteraciones: " + 
					Canales.perdidaPorSimulacion(new Fuente(img1), new Canal(img1, img2), i)) + String.format("%n");
			inciso2 += ("Información Mutua con " + i + " iteraciones: " + 
					Canales.informacionMutuaPorSimulacion(new Fuente(img1), new Canal(img1, img2), i)) + String.format("%n");
			i = i * 10;
			inciso2 += String.format("%n");
		}
		inciso2 += ("Ruido por convergencia = " + Canales.ruidoPorSimulacion(new Fuente(img1), new Canal(img1, img2))) + String.format("%n");
		inciso2 += ("Pérdida por convergencia = " + Canales.perdidaPorSimulacion(new Fuente(img1), new Canal(img1, img2))) + String.format("%n");
		inciso2 += ("Información mutua por convergencia = " + Canales.informacionMutuaPorSimulacion(new Fuente(img1), new Canal(img1, img2))) + String.format("%n");
		
		System.out.println(inciso2);
		
		//---------Guardado en archivos
		File p2inciso1 = new File("Parte2Inciso1.txt");
		File p2inciso2 = new File("Parte2Inciso2.txt");
		try {
			FileOutputStream os1 = new FileOutputStream(p2inciso1);
			FileOutputStream os2 = new FileOutputStream(p2inciso2);
			
			os1.write(inciso1.getBytes());
			os2.write(inciso2.getBytes());
			
			os1.close();
			os2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
