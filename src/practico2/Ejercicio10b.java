package practico2;

import java.io.IOException;

import util.ArchivoTexto;
import util.operacionesArreglo;

public class Ejercicio10b {
	
	private static final float EPSILON = 0.00001f; 
	
	public float[] mediaRecurrencia(ArchivoTexto archivo) throws IOException{
		int[] recurrencias = new int[256];
		int[] tiempoUltimaRecurrencia = new int[256];
		float[] prob = new float[256];
		float[] prob_ant = new float[256];
		int[] sumaPasos = new int[256];
		
		operacionesArreglo.inicializarArreglo(recurrencias, 0);
		operacionesArreglo.inicializarArreglo(tiempoUltimaRecurrencia, -1);
		operacionesArreglo.inicializarArreglo(prob, 0f);
		operacionesArreglo.inicializarArreglo(prob_ant, -1f);
		operacionesArreglo.inicializarArreglo(sumaPasos, 0);
		
		int tiempo = -1;
		String s = archivo.next();
		while ((!operacionesArreglo.converge(prob, prob_ant, EPSILON) || tiempo < 1000) && (s != null)){
			tiempo++;
			int v = Integer.parseInt(s) + 127; //para pasar [-127, 128] --> [0, 255]
			
			if (tiempoUltimaRecurrencia[v] == -1)
				tiempoUltimaRecurrencia[v] = tiempo;
			else{
				sumaPasos[v] = tiempo - tiempoUltimaRecurrencia[v];
				recurrencias[v]++;
				tiempoUltimaRecurrencia[v] = tiempo;
			}
			prob_ant[v] = prob[v];
			prob[v] = (float) sumaPasos[v] / recurrencias[v];
		}
		return prob;
	}
}
