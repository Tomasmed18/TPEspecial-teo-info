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
		ImagenEscalaGrises img2 = new ImagenEscalaGrises("resources/Will_Canal10.bmp");
		
		float ruido = Canales.ruido(img1, img2);
		System.out.println("Ruido = " + ruido);
		float perdida = Canales.perdida(img1, img2);
		System.out.println("Pérdida = " + perdida);
		float infoMutua = Canales.informacionMutua(img1, img2);
		System.out.println("Información mutua = " + infoMutua);
		
		/*
		float[] test= {1.0f/3.0f, 2.0f/3.0f};
		float[] test2;
		test2=util.OperacionesArreglo.acumularArreglo(img1.getProbabilidadesGrises());
		*/
		
		Fuente f= new Fuente(img1);
		Canal c= new Canal(img1,img2);
		int simb=f.emitir();
		System.out.println("emitido " + simb);
		System.out.println("llega " + c.transmitirSimbolo(simb));
		System.exit(0);
	}

}
