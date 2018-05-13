package tp_especial;

import java.io.*;
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
	
	private void codificarSimbolos(float[] probs, Hashtable codificaciones){
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
		
		if (arboles.size() == 1)
			arboles.get(0).getCodificacion(codificaciones, "");
	}
	
	public void codificar(ImagenEscalaGrises img){
		float[] probabilidadesSimbolos=img.getProbabilidadesGrises();
		codificaciones = new Hashtable<String, String>();
		this.codificarSimbolos(probabilidadesSimbolos,codificaciones); //se generan los codigos para cada simbolo
		
		for (String k: codificaciones.keySet())
			System.out.println(k + " ---> " + codificaciones.get(k));
		String codigo = "";
		char aux = 0;
		int counter = 0;
		int totalBits=0;
		
		
		
		for (int pixel: img.getArregloPixeles()){
			String codificacion = codificaciones.get(Integer.toString(pixel));
		
			for (char c: codificacion.toCharArray()){
				
				totalBits++;
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
				else{ //aca podria usarse una mascara de cero pero es equivalente un desplazamiento
					aux = (char) (aux << 1);
				}
				counter++;
			}
					
		}
		if ((counter <= 16) && (counter != 0)){
			aux = (char) (aux << (16 - counter));
			codigo += aux;
			
		}
		
		
		imprimirBitsChar(codigo.toCharArray()[0]);
		String saltoLinea=String.format("%n"); //por alguna razon /n solo no funciona, puede ser error de UTF cuando vuelco el archivo
		String encabezado;
		String simbolosYProb="";
		int alto = img.getAlto();
		int ancho = img.getAncho();
		encabezado = alto + ";" + ancho + saltoLinea; 

		for(int i=0;i<probabilidadesSimbolos.length;i++) {
			
			simbolosYProb+= i + "," + probabilidadesSimbolos[i] + ";" + saltoLinea;
			
		}
		File out= new File("codificacion.txt");
		
		if (out.exists()){
			out.delete(); //borro el contenido para la nueva codificacion
		}
	
		FileWriter fw=null;
	
		try {
			fw = new FileWriter(out);
			fw.write(encabezado);
			fw.write(totalBits+saltoLinea);
			fw.write(simbolosYProb);
			fw.write(codigo);
			
			fw.close();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	private String getKey(Hashtable<String, String> codificacion, String codigo) {
		
		for( String clave: codificacion.keySet()) {
			if(codigo.equals(codificacion.get(clave)))
				return clave;
			
		}
		
		return null;
	}
	
	private void imprimirBitsChar(char c) {
		
		System.out.print(c + " ---> ");
		int mask = 32768;
		for (int i = 0; i < 16 ; i++){
			
			int ax = 0;
			ax = (c & mask);
			if (ax == 32768)
				System.out.print("1");
			else
				System.out.print("0");
			c = (char) (c << 1);
		}
		System.out.println("");
	}
	
	public int[] decodificar(File source) {
		
		/*
		el formato del archivo esta dado por el metodo de codificar. 
		1er linea -encabezado:alto*ancho
		2da: total de bits codificados (para leer hasta determinado punto, ya que una porcion sera basura.
		256 lineas de codigo y sus probabilidades
		y luego la codificacion
		*/
		
		try {
			FileReader fileReader = new FileReader(source);
			BufferedReader bufferReader= new BufferedReader(fileReader);
			String altoAncho=bufferReader.readLine();
			String[] dimensiones=altoAncho.split(";");
			int alto=Integer.parseInt(dimensiones[0]);
			int ancho=Integer.parseInt(dimensiones[1]);
			int totalBits=Integer.parseInt(bufferReader.readLine());
			float[] probGrises= new float[256];
			
			for(int i=0;i<256;i++) {
				String probabilidad=bufferReader.readLine();
				probabilidad=probabilidad.split(",")[1].split(";")[0]; //la linea es simb,prob; Me quedo solo con la prob
				probGrises[i]=Float.parseFloat(probabilidad);
				
			}
		
			
		
			codificaciones = new Hashtable<String, String>();
			this.codificarSimbolos(probGrises, codificaciones); //obtengo los simbolos y las codificaciones correspondientes, los que prob =0 no tienen codigo.
			
			String codigoCodificado="";
			
			int[] decodificado= new int[alto*ancho];
			String bufferDecodificador="";
			int pixeles=0;
			int bitsLeidos=0;
			int mask=32768;  //0000100 y el 1 esta en el bit 15 (el bit de mayor peso de un char)
			
			
			
			while( (codigoCodificado=bufferReader.readLine() )!=  null) { //esto es por si el string original codificado se separo en varias lineas a causa de volcar el archivo
			
				char[] charCodificados=codigoCodificado.toCharArray();
				for(char c: charCodificados) {
					if(pixeles==0)
						imprimirBitsChar(c);
					for(int i=0;i<16;i++) {
						
						if(bitsLeidos==totalBits)
							break; //todo el resto es basura
						bitsLeidos++;
						char bitMayorPeso=  (char) (c & mask);
						c=(char) (c<<1); //lo desplazo asi veo el proximo bit
						if(bitMayorPeso==32768) { //10000000..
							bufferDecodificador+="1";
							
						}else {
							bufferDecodificador+="0";
						}
						
						String clave=this.getKey(codificaciones, bufferDecodificador); //clave es un valor entre 0..255, que es el tono gris del pixel.
						
						if(clave!=null) {
							//lo que venia decodificando se convirtio en un simbolo, por lo que hay que resetear el buffer y guardar el simbolo (que es un valor 0..255) en el pixel en cuestion
							bufferDecodificador="";
							decodificado[pixeles]=Integer.parseInt(clave);
							pixeles++;
							
						}
						
					}
					
					
				}
				
				
				
			}
			
			bufferReader.close();
			return decodificado;
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; //aca no llega nunca, pero Java.... lo requiere....
	}
	
}
