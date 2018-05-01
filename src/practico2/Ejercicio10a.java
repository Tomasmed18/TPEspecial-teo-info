package practico2;

import java.io.IOException;

import util.ArchivoTexto;

public class Ejercicio10a {
	
	public static final float EPSILON = 0.000001f;
	
	private boolean converge(float prob1, float prob2){
		return (Math.abs((prob1 - prob2)) < EPSILON);
	}

	public float probabilidadPrimeraRecurrencia(String simbolo, int n, ArchivoTexto ar) throws IOException{
		int exitos= 0;
		int pruebas = 0;
		float prob = 0;
		float prob_ant = -1;
		
		String s = ar.next();
		while ((s != null) && !s.equals(simbolo)) //se posiciona en el primer 0
			s = ar.next();
	
		while (((!converge(prob, prob_ant)) || (pruebas < 1000)) && (s != null)){
			s = ar.next();
			int m = 1;
			while((m<n) && (s!=null) && !s.equals(simbolo)){
				s = ar.next();
				m++;
			}
			if ((m==n) && (s != null) && (s.equals(simbolo)))
				exitos++;
			
			pruebas++;
			prob_ant = prob;
			prob = (float)exitos/pruebas;
		}
		return prob;
	}
	
	
	public static void main(String[] args) throws IOException {
		Ejercicio10a ej10a = new Ejercicio10a();
		ArchivoTexto archivo = new ArchivoTexto("DatosEntrada-TP2-Ej11.txt");
		int n = 15;
		String simbolo = "0 ";
		float prob = ej10a.probabilidadPrimeraRecurrencia(simbolo, n, archivo);
		System.out.println("Probabilidad de primera reccurrencia de '" + simbolo + "' en " + n + " pasos: " + prob);

	}

}
