package tp_especial;

import java.util.Hashtable;

public class CodigoHuffman {
	
	
	private interface Arbol{
		public float valor();
		public void getCodificacion(Hashtable codificacion, String valActual);
	}
	
	private class Rama implements Arbol{
		Arbol izquierda, derecha;
		
		public Rama(Rama izq, Rama der){
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
	
	public CodigoHuffman(float[] probs){
		
	}
}
