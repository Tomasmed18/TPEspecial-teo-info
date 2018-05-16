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
	
	private static final String encoding="UTF8";
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
		try {
			//OutputStream outC = new FileOutputStream("codificacion.dat");
			OutputStream out = new FileOutputStream("codificacion.txt");
			String saltoLinea=String.format("%n"); //por alguna razon /n solo no funciona, puede ser error de UTF cuando vuelco el archivo
			String encabezado;
			String simbolosYProb="";
			
			int alto = img.getAlto();
			int ancho = img.getAncho();
			
			String cantPixeles=alto*ancho+saltoLinea;
			int[] frecuenciaSimbolos= new int[256]; //0...255 valores de tonos
			
			byte aux = 0;
			
			
			int counter = 0;
		
			
			codificaciones = new Hashtable<String, String>();
			
			float[] probabilidadesSimbolos=img.getProbabilidadesGrises();
			
			this.codificarSimbolos(probabilidadesSimbolos,codificaciones); //se generan los codigos para cada simbolo
		
	
			encabezado = alto + ";" + ancho + saltoLinea; 
			
		
				
			
			
			
			
			for (String k: codificaciones.keySet())
				System.out.println(k + " ---> " + codificaciones.get(k));
			
			
			
			//byte[] codigo= new byte[img.getAlto()*img.getAncho()]; 
			ByteArrayOutputStream streamBytesCodificados= new ByteArrayOutputStream();
			for (int pixel: img.getArregloPixeles()){
				frecuenciaSimbolos[pixel]++;
				String codificacion = codificaciones.get(Integer.toString(pixel));
				
				for (char c: codificacion.toCharArray()){
					
					
					if (counter == 8){
						streamBytesCodificados.write(aux);
						
						
						aux = 0;
						counter = 0;
					}
					
					if (c == '1'){
						int mask = 1; // 0000000000000001
						aux = (byte)(aux << 1);
						aux = (byte) (aux | mask);
					}
					else{ //aca podria usarse una mascara de cero pero es equivalente un desplazamiento
						aux = (byte) (aux << 1);
					}
					counter++;
				}
						
			}
			if ((counter <= 8) && (counter != 0)){
				aux = (byte) (aux << (8 - counter));
				streamBytesCodificados.write(aux);
				
			}
			
			//--------------------------------Escritura de archivos-----------------------------
			
			out.write(encabezado.getBytes(encoding));
			out.write(cantPixeles.getBytes(encoding));
			
			for(int i=0; i<frecuenciaSimbolos.length;i++) {
				if(frecuenciaSimbolos[i]!=0) {
					simbolosYProb= i + "," + frecuenciaSimbolos[i] + ";" + saltoLinea;
					out.write(simbolosYProb.getBytes(encoding));
				}
				
			}
				
		
		
			for(byte b:streamBytesCodificados.toByteArray())
				out.write(b);
			out.close();
			//outC.close();
				
			
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
	
	private void imprimirByte(byte c) {
			
			System.out.print(c + " ---> ");
			int mask = 128;
			for (int i = 0; i < 8 ; i++){
				
				int ax = 0;
				ax = (c & mask);
				if (ax == 128)
					System.out.print("1");
				else
					System.out.print("0");
				c = (byte) (c << 1);
			}
			System.out.println("");
		}
	


	public int[] decodificar(String source) {
		
		/*
		el formato del archivo esta dado por el metodo de codificar. 
		1er linea -encabezado:alto*ancho
		2da: total de pixeles (esto puede ser al pedo porque se saca de lo anterior, tengo que reevaluar si conviene una u otra)
		Resto de lineas es simbolo,frecuenca; ---> frecuencia/totalPixeles = probabilidad
		y luego la codificacion
		*/
		
		try {
			/*lo siguiente requiere una explicacion porque es muy de bajo nivel de codificacion
			 es una guasada pero se abre el archivo con un bufferedReader y un InputStream. Lo primero para leer de a lineas, lo segundo
			 para leer de a bytes (ya que leer de a lineas lo codificado rompe el archivo ya que hay saltos de lineas que son agregados por la misma codificacion y entonces se corren todos los bits)
			  
			 el truco esta en leer las lineas normalmente y cuando leo la codificacion debo posicionar el puntero del inputStream en esa ubicacion (es decir, tengo que saltarme todos los bytes leidos anteriormente)
			 
			  en las lineas mas abajo aparece saltearBytes+=altoAncho.getBytes().length+2;
			  
			  basicamente le sumo (en bytes) la linea que lei del archivo y dos bytes mas. Esos dos bytes extras son los saltos de linea que se codifican con 16 bits.
			  (la unica explicacion de que ese caracter sean 2 bytes es que java usar 2 bytes para char)
			 
			 */
			BufferedReader bufferReader= new BufferedReader(new InputStreamReader(new FileInputStream(source),encoding));
			
			InputStream in = new FileInputStream(source);
			int saltearBytes=0;
			String altoAncho=bufferReader.readLine();
			String stringPixeles=bufferReader.readLine();
			String[] dimensiones=altoAncho.split(";");
			
			int alto=Integer.parseInt(dimensiones[0]);
			int ancho=Integer.parseInt(dimensiones[1]);
			
			float[] probGrises= new float[256];
			codificaciones = new Hashtable<String, String>();
			
			int totalPixeles=Integer.parseInt(stringPixeles);
			int acumulado=0;
		
			saltearBytes+=altoAncho.getBytes().length+2; //primer linea
			saltearBytes+=stringPixeles.getBytes().length+2; //segunda linea
			
			while(acumulado<totalPixeles) { //lineas de simbolos,frecuencia;
				String frecuencia=bufferReader.readLine();
				saltearBytes+=frecuencia.getBytes().length+2;
				int simbolo=Integer.parseInt(frecuencia.split(",")[0]);
				frecuencia=frecuencia.split(",")[1].split(";")[0]; //la linea es simb,prob; Me quedo solo con la frecuencia
				float probabilidad=(float)Integer.parseInt(frecuencia)/totalPixeles;
				probGrises[simbolo]=probabilidad;
				acumulado+=Integer.parseInt(frecuencia);
				
			}
		
			in.skip(saltearBytes); //me salto todos los bytes de la primer parte, todo lo siguiente es codificado mediante huffman
			
			this.codificarSimbolos(probGrises, codificaciones); //obtengo los simbolos y las codificaciones correspondientes, los que prob =0 no tienen codigo.
			
			
			
			int[] decodificado= new int[alto*ancho];
			String bufferDecodificador="";
			int pixeles=0;
		
			int mask=128;  //0000100 y el 1 esta en el bit 15 (el bit de mayor peso de un char)
			
			int maxCodificacion=0;
			for(String k: codificaciones.keySet()) {
				int longCodigo=codificaciones.get(k).length();
				if( longCodigo>maxCodificacion)
					maxCodificacion= longCodigo;
				
			}
			
			
			
			byte[] arrByteLeido= {0};
			byte byteLeido=0;
			int stop=0;
			
			while((stop= in.read(arrByteLeido))>=0) {
				 byteLeido=arrByteLeido[0];
				
				 {
					
						
					for(int i=0;i<8;i++) {
						//imprimirBitsChar(c);
						
						
						
						byte bitMayorPeso=  (byte) (byteLeido & mask);
						byteLeido= (byte) (byteLeido<<1); //lo desplazo asi veo el proximo bit
						if(bitMayorPeso==	(byte)10000000) { //10000000..
							bufferDecodificador+="1";
							
						}else {
							bufferDecodificador+="0";
						}
						
						
						if(bufferDecodificador.length()==maxCodificacion) { //complete el buffer, la idea ahora es decodificar los simbolos dentro de ese buffer
							int frontera=1;
							for(int indexBuffer=0;indexBuffer<maxCodificacion;indexBuffer++) { 
								String clave=this.getKey(codificaciones, bufferDecodificador.substring(0, frontera)); //clave es un valor entre 0..255, que es el tono gris del pixel.
								
								if(clave!=null) {
									if(pixeles>=alto*ancho)
										break;
									//el substring es un simbolo, por lo que voy a cortar ese simbolo del string para decodificar los demas (asi el substring(0, frontera) sigue funcionando)
									//Es basicamente ir moviendo una ventana para hacer string matching
									bufferDecodificador=bufferDecodificador.substring(frontera);
									frontera=1;
									
									decodificado[pixeles]=Integer.parseInt(clave);
									
									pixeles++;
									
									
								}else
									frontera++;
								}
						}
					}
					
					
				}
				
				
				
			}
			
			
			in.close();
			bufferReader.close();
			return decodificado;
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; //aca no llega nunca, pero Java.... lo requiere....
	}
	
}
