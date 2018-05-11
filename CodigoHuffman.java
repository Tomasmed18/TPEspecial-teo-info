package tp_especial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class CodigoHuffman {
	private Hashtable<String, String> codificaciones;
	
	private interface Arbol{
		public float valor();
		public void getCodificacion(Hashtable codificacion, String valActual);
	}
	
	private class Rama implements Arbol{
		Arbol izquierda, derecha;
		
		public Rama(Arbol izq, Arbol der){
			izquierda = izq;
			derecha = der;
		}
		
		public float valor(){
			return izquierda.valor() + derecha.valor();
		}
		
		public void getCodificacion(Hashtable codificacion, String valActual){
			izquierda.getCodificacion(codificacion, valActual + "0");
			derecha.getCodificacion(codificacion, valActual + "1");
		}
	}
	
	private class Hoja implements Arbol{
		float valor;
		String simbolo;
		
		public Hoja(float valor, String simbolo){
			this.valor = valor;
			this.simbolo = simbolo;
		}
		
		public float valor(){
			return valor;
		}
		public void getCodificacion(Hashtable codificacion, String valActual){
			codificacion.put(simbolo, valActual);
		}
	}
	
	private class ComparadorArbol implements Comparator<Arbol>{
		public int compare(Arbol a1, Arbol a2){
			float resta = a1.valor() - a2.valor();
			if (resta > 0)
				return 1;
			else if (resta == 0)
				return 0;
			else
				return -1;
		}
	}
	
	private void insertarOrdenado(List<Arbol> lista, Arbol a){
		int i = 0;
		for (Arbol a2: lista){
			if (a2.valor() > a.valor())
				i++;
		}
		
	}
	
	public CodigoHuffman(){}
	
	private void codificarSimbolos(float[] probs){
		List<Arbol> arboles = new ArrayList<Arbol>();
		for (int i = 0; i < probs.length; i++)
			if (probs[i] != 0)
				arboles.add(new Hoja(probs[i], Integer.toString(i)));

		Collections.sort(arboles, new ComparadorArbol());
		
		while (arboles.size() > 1){
			Rama nueva = new Rama(arboles.remove(0), arboles.remove(0));
			//MEJORAR
			arboles.add(nueva);
			arboles.sort(new ComparadorArbol());
		}
		codificaciones = new Hashtable<String, String>();
		if (arboles.size() == 1)
			arboles.get(0).getCodificacion(codificaciones, "");
	}
	
	public void codificar(ImagenEscalaGrises img){
		this.codificarSimbolos(img.getProbabilidadesGrises()); //se generan los codigos para cada simbolo
		
		for (String k: codificaciones.keySet())
			System.out.println(k + " ---> " + codificaciones.get(k));
		String codigo = "";
		char aux = 0;
		int counter = 0;
		
		for (int pixel: img.getArregloPixeles()){
			String codificacion = codificaciones.get(Integer.toString(pixel));
			
			for (char c: codificacion.toCharArray()){
				if (counter == 16){
					codigo += aux;
					aux = 0;
					counter = 0;
				}
				
				if (c == '1'){
					int mask = 1; // 0000000000000001
					aux = (char)(aux << 1);
					aux = (char) (aux | mask);
				}
				else{
					aux = (char) (aux << 1);
				}
				counter++;
			}
					
		}
		if ((counter <= 16) && (counter != 0)){
			aux = (char) (aux << (16 - counter));
			codigo += aux;
		}
		
		//System.out.println(codigo);
		for (char c: codigo.toCharArray()){
			System.out.print(c + " ---> ");
			char mask = 1;
			for (int i = 0; i < 16 ; i++){
				char ax = 0;
				ax = (char) (c & mask);
				if (ax == 1)
					System.out.print("1");
				else
					System.out.print("0");
				c = (char) (c >> 1);
			}
			System.out.println("");
			
		}
				
		
		String encabezado;
		int alto = img.getAlto();
		int ancho = img.getAncho();
		encabezado = alto + ";" + ancho + "\n"; 

		
	}
	
}
