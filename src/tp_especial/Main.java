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
		ImagenEscalaGrises img6 = new ImagenEscalaGrises("resources/Will_6.bmp");
		ImagenEscalaGrises imgcanal2 = new ImagenEscalaGrises("resources/Will_Canal2.bmp");
		ImagenEscalaGrises imgcanal8 = new ImagenEscalaGrises("resources/Will_Canal8.bmp");
		ImagenEscalaGrises imgcanal10 = new ImagenEscalaGrises("resources/Will_Canal10.bmp");
		
		String sl = String.format("%n");
		
		//---------Inciso 1
		String inciso1 = "";
		int[] cantGrises2 = imgcanal2.getCantidadesGrises();
		int[] cantGrises8 = imgcanal8.getCantidadesGrises();
		int[] cantGrises10 = imgcanal10.getCantidadesGrises();
		
		inciso1 += "Distribuciones de grises de la salida del canal 2:" + sl;
		for (int i = 0; i < cantGrises2.length; i++)
			inciso1 += "[" + i + "] = " + cantGrises2[i] + sl;
		inciso1 += "Media del canal 2 = " + Indicadores.media(imgcanal2.getArregloPixeles()) + sl;
		inciso1 += "Desvío del canal 2 = " + Indicadores.desviacionEstandar(imgcanal2.getArregloPixeles()) + sl + sl;
		
		inciso1 += "Distribuciones de grises de la salida del canal 8:" + sl;
		for (int i = 0; i < cantGrises8.length; i++)
			inciso1 += "[" + i + "] = " + cantGrises8[i] + sl;
		inciso1 += "Media del canal 8 = " + Indicadores.media(imgcanal8.getArregloPixeles()) + sl;
		inciso1 += "Desvío del canal 8 = " + Indicadores.desviacionEstandar(imgcanal8.getArregloPixeles()) + sl + sl;
		
		inciso1 += "Distribuciones de grises de la salida del canal 10:" + sl;
		for (int i = 0; i < cantGrises10.length; i++)
			inciso1 += "[" + i + "] = " + cantGrises10[i] + sl;
		inciso1 += "Media del canal 10 = " + Indicadores.media(imgcanal10.getArregloPixeles()) + sl;
		inciso1 += "Desvío del canal 10 = " + Indicadores.desviacionEstandar(imgcanal10.getArregloPixeles());
		
		Histograma h2 = new Histograma("Cantidades de grises de Canal 2" , imgcanal2.getCantidadesGrises(), false);
		Histograma h8 = new Histograma("Cantidades de grises de Canal 8" , imgcanal8.getCantidadesGrises(), false);
		Histograma h10 = new Histograma("Cantidades de grises de Canal 10" , imgcanal10.getCantidadesGrises(), false);
		
		System.out.println(inciso1);
		
		//---------Inciso 2
		
		String inciso2 = "";
		CodigoHuffman ch = new CodigoHuffman();
		
		float longitudMedia2 = ch.codificar(imgcanal2, "CodificacionCanal2.txt");
		float longitudMedia8 = ch.codificar(imgcanal8, "CodificacionCanal8.txt");
		float longitudMedia10 = ch.codificar(imgcanal10, "CodificacionCanal10.txt");
		
		inciso2 += "Longitud media por símbolo del código de la salida del canal 2 = " + longitudMedia2 + sl;
		inciso2 += "Longitud media por símbolo del código de la salida del canal 8 = " + longitudMedia8 + sl;
		inciso2 += "Longitud media por símbolo del código de la salida del canal 10 = " + longitudMedia10 + sl;
		
		System.out.println(inciso2);
		
		//---------Inciso 3
		
		String inciso3 = "";
		Canal canal2 = new Canal(img1, imgcanal2);
		Canal canal8 = new Canal(img1, imgcanal8);
		Canal canal10 = new Canal(img1, imgcanal10);
		
		ImagenEscalaGrises img6Canal2 = img6.transmitirPorCanal(canal2);
		ImagenEscalaGrises img6Canal8 = img6.transmitirPorCanal(canal8);
		ImagenEscalaGrises img6Canal10 = img6.transmitirPorCanal(canal10);
		
		inciso3 += "Will_6.bmp transmitida por el canal 2:" + sl;
		inciso3 += "Ruido = " + Canales.ruido(img6, img6Canal2) + sl;
		inciso3 += "Pérdida = " + Canales.perdida(img6, img6Canal2) + sl;
		inciso3 += "Información Mutua = " + Canales.informacionMutua(img6, img6Canal2) + sl + sl;
		
		inciso3 += "Will_6.bmp transmitida por el canal 8:" + sl;
		inciso3 += "Ruido = " + Canales.ruido(img6, img6Canal8) + sl;
		inciso3 += "Pérdida = " + Canales.perdida(img6, img6Canal8) + sl;
		inciso3 += "Información Mutua = " + Canales.informacionMutua(img6, img6Canal8) + sl + sl;
		
		inciso3 += "Will_6.bmp transmitida por el canal 10:" + sl;
		inciso3 += "Ruido = " + Canales.ruido(img6, img6Canal10) + sl;
		inciso3 += "Pérdida = " + Canales.perdida(img6, img6Canal10) + sl;
		inciso3 += "Información Mutua = " + Canales.informacionMutua(img6, img6Canal10);
		
		System.out.println(inciso3);
		
		//---------Inciso 4
		
		
		String inciso4 = "";
		Fuente fuente = new Fuente(img1);
		
		
		inciso4 += "Transmitiendo por el canal 2:" + sl;
		int i = 10;
		while (i <= 100000){
			inciso4 += ("Ruido con " + i + " iteraciones: " + 
					Canales.ruidoPorSimulacion(fuente, canal2, i)) + sl;
			inciso4 += ("Pérdida con " + i + " iteraciones: " + 
					Canales.perdidaPorSimulacion(fuente, canal2, i)) + sl;
			inciso4 += ("Información Mutua con " + i + " iteraciones: " + 
					Canales.informacionMutuaPorSimulacion(fuente, canal2, i)) + sl;
			i = i * 10;
			inciso4 += sl;
		}
		inciso4 += ("Ruido por convergencia = " + Canales.ruidoPorSimulacion(fuente, canal2)) + sl;
		inciso4 += ("Pérdida por convergencia = " + Canales.perdidaPorSimulacion(fuente, canal2)) + sl;
		inciso4 += ("Información mutua por convergencia = " + Canales.informacionMutuaPorSimulacion(fuente, canal2)) + sl + sl + sl;

		
		
		inciso4 += "Transmitiendo por el canal 8:" + sl;
		i = 10;
		while (i <= 100000){
			inciso4 += ("Ruido con " + i + " iteraciones: " + 
					Canales.ruidoPorSimulacion(fuente, canal8, i)) + sl;
			inciso4 += ("Pérdida con " + i + " iteraciones: " + 
					Canales.perdidaPorSimulacion(fuente, canal8, i)) + sl;
			inciso4 += ("Información Mutua con " + i + " iteraciones: " + 
					Canales.informacionMutuaPorSimulacion(fuente, canal8, i)) + sl;
			i = i * 10;
			inciso4 += sl;
		}
		inciso4 += ("Ruido por convergencia = " + Canales.ruidoPorSimulacion(fuente, canal8)) + sl;
		inciso4 += ("Pérdida por convergencia = " + Canales.perdidaPorSimulacion(fuente, canal8)) + sl;
		inciso4 += ("Información mutua por convergencia = " + Canales.informacionMutuaPorSimulacion(fuente, canal8)) + sl + sl + sl;
		
		
		
		inciso4 += "Transmitiendo por el canal 10:" + sl;
		i = 10;
		while (i <= 100000){
			inciso4 += ("Ruido con " + i + " iteraciones: " + 
					Canales.ruidoPorSimulacion(fuente, canal10, i)) + sl;
			inciso4 += ("Pérdida con " + i + " iteraciones: " + 
					Canales.perdidaPorSimulacion(fuente, canal10, i)) + sl;
			inciso4 += ("Información Mutua con " + i + " iteraciones: " + 
					Canales.informacionMutuaPorSimulacion(fuente, canal10, i)) + sl;
			i = i * 10;
			inciso4 += sl;
		}
		inciso4 += ("Ruido por convergencia = " + Canales.ruidoPorSimulacion(fuente, canal10)) + sl;
		inciso4 += ("Pérdida por convergencia = " + Canales.perdidaPorSimulacion(fuente, canal10)) + sl;
		inciso4 += ("Información mutua por convergencia = " + Canales.informacionMutuaPorSimulacion(fuente, canal10));
		
		System.out.println(inciso4);
		
		
		
		//---------Guardado en archivos
		h2.guardarJPEG("Histograma_Canal2.jpeg");
		h8.guardarJPEG("Histograma_Canal8.jpeg");
		h10.guardarJPEG("Histograma_Canal10.jpeg");
		
		img6Canal2.guardarImagen("Will_6_canal2.jpeg");
		img6Canal8.guardarImagen("Will_6_canal8.jpeg");
		img6Canal10.guardarImagen("Will_6_canal10.jpeg");
		
		File archinciso1 = new File("EntregaFinalInciso1.txt");
		File archinciso2 = new File("EntregaFinalInciso2.txt");
		File archinciso3 = new File("EntregaFinalInciso3.txt");
		File archinciso4 = new File("EntregaFinalInciso4.txt");
		try {
			FileOutputStream os1 = new FileOutputStream(archinciso1);
			FileOutputStream os2 = new FileOutputStream(archinciso2);
			FileOutputStream os3 = new FileOutputStream(archinciso3);
			FileOutputStream os4 = new FileOutputStream(archinciso4);
			
			os1.write(inciso1.getBytes());
			os2.write(inciso2.getBytes());
			os3.write(inciso3.getBytes());
			os4.write(inciso4.getBytes());
			
			os1.close();
			os2.close();
			os3.close();
			os4.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}

}
