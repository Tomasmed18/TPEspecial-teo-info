package tp_especial;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class CodigoHuffman {

	private static final String encoding = "UTF8";
	
	int[] mensaje;
	int ultimaPosEscrita = 0;
	Arbol raiz;
	
	private interface Arbol{
		public float valor();
		public void getCodificacion(Hashtable codificacion, String valActual);
		public Arbol avanzar(byte direccion);
		
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
		
		public Arbol avanzar(byte direccion){ 
			if ((direccion & (byte) (10000000)) == (byte) (10000000)) //si el bit de mayor peso es 1, va por derecha
				return derecha;
			else
				return izquierda;
		}
	}
	
	private class Hoja implements Arbol{
		float valor;
		int simbolo;
		
		public Hoja(float valor, int simbolo){
			this.valor = valor;
			this.simbolo = simbolo;
		}
		
		public float valor(){
			return valor;
		}
		public void getCodificacion(Hashtable codificacion, String valActual){
			codificacion.put(Integer.toString(simbolo), valActual);
		}
		
		public Arbol avanzar(byte direccion){
			mensaje[ultimaPosEscrita++] = simbolo;
			return raiz.avanzar(direccion);
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
	
	
	public CodigoHuffman(){}
	
	
	private void codificarSimbolos(float[] probs, Hashtable<String, String> codificaciones){
		List<Arbol> arboles = new ArrayList<Arbol>();
		for (int i = 0; i < probs.length; i++)
			if (probs[i] != 0)
				arboles.add(new Hoja(probs[i], i));

		Collections.sort(arboles, new ComparadorArbol());
		
		while (arboles.size() > 1){
			Rama nueva = new Rama(arboles.remove(0), arboles.remove(0));
			arboles.add(nueva);
			arboles.sort(new ComparadorArbol());
		}
		
		if (arboles.size() == 1)
			arboles.get(0).getCodificacion(codificaciones, "");
	}
	
	private Arbol codificarSimbolos(float[] probs){
		List<Arbol> arboles = new ArrayList<Arbol>();
		for (int i = 0; i < probs.length; i++)
			if (probs[i] != 0)
				arboles.add(new Hoja(probs[i], i));

		Collections.sort(arboles, new ComparadorArbol());
		
		while (arboles.size() > 1){
			Rama nueva = new Rama(arboles.remove(0), arboles.remove(0));
			//MEJORAR
			arboles.add(nueva);
			arboles.sort(new ComparadorArbol());
		}
		if (arboles.size() == 1)
			return arboles.get(0);
		else
			return null;
		}
	
	public void codificar(ImagenEscalaGrises img, String nombreArchivoSalida){

			String saltoLinea = String.format("%n"); //por alguna razon /n solo no funciona, puede ser error de UTF cuando vuelco el archivo
			
			String encabezado;
			
			int alto = img.getAlto();
			int ancho = img.getAncho();
			encabezado = alto + ";" + ancho + saltoLinea; 
			
			
			String cantPixeles = alto * ancho + saltoLinea;
			int[] frecuenciaSimbolos= new int[256]; //0...255 valores de tonos
			
			
			float[] probabilidadesSimbolos = img.getProbabilidadesGrises();
			
			Hashtable<String, String> codificaciones = new Hashtable<String, String>();
			this.codificarSimbolos(probabilidadesSimbolos,codificaciones); //se generan los codigos para cada simbolo

			
			//------------------------codificacion del mensaje en binario---------------------
			
			ByteArrayOutputStream streamBytesCodificados= new ByteArrayOutputStream();
			byte aux = 0;
			int counter = 0;
			
			for (int pixel: img.getArregloPixeles()){
				frecuenciaSimbolos[pixel]++;
				String codificacion = codificaciones.get(Integer.toString(pixel));
				
				for (char c: codificacion.toCharArray()){
					if (counter == 8){ //si se llenó el byte
						streamBytesCodificados.write(aux);
						aux = 0;
						counter = 0;
					}
					if (c == '1'){
						int mask = 1; // 0000000000000001
						aux = (byte) (aux << 1);
						aux = (byte) (aux | mask);
					}
					else{ 
						aux = (byte) (aux << 1);
					}
					counter++;
				}
			}
			if ((counter < 8) && (counter != 0)){
				aux = (byte) (aux << (8 - counter));
				streamBytesCodificados.write(aux);
				
			}
			
			//--------------------------------Escritura de archivo-----------------------------
			
			OutputStream out;
			try {
				out = new FileOutputStream(nombreArchivoSalida);
				this.escribirArchivo(out, encabezado, cantPixeles, frecuenciaSimbolos, streamBytesCodificados);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
			
			
	}
	
	private void escribirArchivo(OutputStream out, String encabezado, 
								String cantPixeles, int[] frecuenciasSimbolos, ByteArrayOutputStream byteArray){
		try{
			out.write(encabezado.getBytes(encoding));
			out.write(cantPixeles.getBytes(encoding));
		
			String simbolosYProb = "";
		
			for(int i = 0; i < frecuenciasSimbolos.length; i++) {
				if(frecuenciasSimbolos[i] != 0) {
					simbolosYProb= i + "," + frecuenciasSimbolos[i] + ";" + String.format("%n");
					out.write(simbolosYProb.getBytes(encoding));
				}
			}
			System.out.println(byteArray.size());
			for(byte b: byteArray.toByteArray())
				out.write(b);
				out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public ImagenEscalaGrises decodificar(String source) {
		
		/*
		el formato del archivo esta dado por el metodo de codificar. 
		1er linea -encabezado:alto*ancho
		2da: total de pixeles (esto puede ser al pedo porque se saca de lo anterior, tengo que reevaluar si conviene una u otra)
		Resto de lineas es simbolo,frecuenca; ---> frecuencia/totalPixeles = probabilidad
		y luego la codificacion
		*/
		
		try {
			/*lo siguiente requiere una explicacion porque es muy de bajo nivel de codificacion
			  se abre el archivo con un bufferedReader y un InputStream. Lo primero para leer de a lineas, lo segundo
			 para leer de a bytes (ya que leer de a lineas lo codificado rompe el archivo ya que hay saltos de lineas que son agregados por la misma codificacion y entonces se corren todos los bits)
			  
			 el truco esta en leer las lineas normalmente y cuando leo la codificacion debo posicionar el puntero del inputStream en esa ubicacion (es decir, tengo que saltarme todos los bytes leidos anteriormente)
			 
			  en las lineas mas abajo aparece saltearBytes+=altoAncho.getBytes().length+2;
			  
			  basicamente le sumo (en bytes) la linea que lei del archivo y dos bytes mas. Esos dos bytes extras son los saltos de linea que se codifican con 16 bits.
			  (la unica explicacion de que ese caracter sean 2 bytes es que java usar 2 bytes para char)
			 
			 */
			BufferedReader bufferReader= new BufferedReader(new InputStreamReader(new FileInputStream(source),encoding));
			InputStream in = new FileInputStream(source);
			
			
			String altoAncho = bufferReader.readLine();
			String stringPixeles = bufferReader.readLine();
			String[] dimensiones = altoAncho.split(";");
			
			int alto = Integer.parseInt(dimensiones[0]);
			int ancho = Integer.parseInt(dimensiones[1]);
			
			float[] probGrises= new float[256];
			
			int totalPixeles=Integer.parseInt(stringPixeles);
			int acumulado=0;
			
			
			int saltearBytes=0;
			saltearBytes+=altoAncho.getBytes().length+2; //primer linea
			saltearBytes+=stringPixeles.getBytes().length+2; //segunda linea
			
			while (acumulado < totalPixeles) { //lineas de simbolos,frecuencia;
				String frecuencia = bufferReader.readLine();
				saltearBytes += frecuencia.getBytes().length+2;
				int simbolo=Integer.parseInt(frecuencia.split(",")[0]);
				frecuencia = frecuencia.split(",")[1].split(";")[0]; //la linea es simb,prob; Me quedo solo con la frecuencia
				float probabilidad = (float)Integer.parseInt(frecuencia)/totalPixeles;
				probGrises[simbolo] = probabilidad;
				acumulado+=Integer.parseInt(frecuencia);
				
			}
			System.out.println(saltearBytes);
			in.skip(saltearBytes); //me salto todos los bytes de la primer parte, todo lo siguiente es codificado mediante huffman
			
			
			
			Arbol arbol = this.codificarSimbolos(probGrises); 
			this.raiz = arbol;
			mensaje = new int[(alto * ancho) + 8];	//este +8 es para evitar errores por leer simbolos de mas en el ultimo byte 	
			
			byte[] arrByteLeido= {0};
			byte byteLeido=0;
			while((in.read(arrByteLeido)) >= 0) { //mientras haya bytes que leer
				byteLeido = arrByteLeido[0];
				for(int i = 0; i < 8; i++) {		
					arbol = arbol.avanzar(byteLeido);
					byteLeido = (byte) (byteLeido << 1); //lo desplazo asi veo el proximo bit
				}
			}
		
			in.close();
			bufferReader.close();
			
			ImagenEscalaGrises reconstruida = new ImagenEscalaGrises(mensaje, alto, ancho,BufferedImage.TYPE_BYTE_INDEXED);
			return reconstruida;
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return null; //aca no llega nunca, pero Java.... lo requiere....
	}
	
}
