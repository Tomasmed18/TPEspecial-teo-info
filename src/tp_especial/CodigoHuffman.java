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
		OutputStream outC = new FileOutputStream("codificacion.dat");
		float[] probabilidadesSimbolos=img.getProbabilidadesGrises();
		codificaciones = new Hashtable<String, String>();
		this.codificarSimbolos(probabilidadesSimbolos,codificaciones); //se generan los codigos para cada simbolo
		
		for (String k: codificaciones.keySet())
			System.out.println(k + " ---> " + codificaciones.get(k));
		
		byte aux = 0;
		int counter = 0;
		int totalBits=0;
		
		//byte[] codigo= new byte[img.getAlto()*img.getAncho()]; 
		
		for (int pixel: img.getArregloPixeles()){
			String codificacion = codificaciones.get(Integer.toString(pixel));
			
			for (char c: codificacion.toCharArray()){
				
				totalBits++;
				if (counter == 8){
					outC.write(aux);
					
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
			outC.write(aux);
			
		}
		
		
		
		String saltoLinea=String.format("%n"); //por alguna razon /n solo no funciona, puede ser error de UTF cuando vuelco el archivo
		String encabezado;
		String simbolosYProb="";
		int alto = img.getAlto();
		int ancho = img.getAncho();
		encabezado = alto + ";" + ancho + saltoLinea; 

		for(int i=0;i<probabilidadesSimbolos.length;i++) {
			
			simbolosYProb+= i + "," + probabilidadesSimbolos[i] + ";" + saltoLinea;
			
		}
		/*
		File out= new File("codificacion.txt");
		
		if (out.exists()){
			out.delete(); //borro el contenido para la nueva codificacion
		}*/
		
	

			OutputStream out = new FileOutputStream("instruccionesCodificacion.txt");
		
			/*
			bw = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream(out), encoding));
			*/
			out.write(encabezado.getBytes(encoding));
			out.write((totalBits+saltoLinea).getBytes(encoding));
			out.write(simbolosYProb.getBytes(encoding));
			
		
			
			out.close();
			outC.close();
			
		
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
	public int[] decodificar(String source,String codificado) {
		
		/*
		el formato del archivo esta dado por el metodo de codificar. 
		1er linea -encabezado:alto*ancho
		2da: total de bits codificados (para leer hasta determinado punto, ya que una porcion sera basura.
		256 lineas de codigo y sus probabilidades
		y luego la codificacion
		*/
		
		try {
			
			BufferedReader bufferReader= new BufferedReader(new InputStreamReader(
                    new FileInputStream(source), encoding));
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
			
			
			
			int[] decodificado= new int[alto*ancho];
			String bufferDecodificador="";
			int pixeles=0;
			int bitsLeidos=0;
			int mask=128;  //0000100 y el 1 esta en el bit 15 (el bit de mayor peso de un char)
			
			
			InputStream in = new FileInputStream(codificado);
			//while( (codigoCodificado=bufferReader.readLine() )!=  null)
			{ //esto es por si el string original codificado se separo en varias lineas a causa de volcar el archivo
			byte[] arrByteLeido= {0};
			byte byteLeido=0;
			int stop=0;
			//BufferedInputStream bufferIn= new BufferedInputStream(in);
			while((stop= in.read(arrByteLeido))>=0) {
				 byteLeido=arrByteLeido[0];
				
				 {
					
						
					for(int i=0;i<8;i++) {
						//imprimirBitsChar(c);
						if(bitsLeidos>=totalBits)
							break;
						bitsLeidos++;
						
						byte bitMayorPeso=  (byte) (byteLeido & mask);
						byteLeido= (byte) (byteLeido<<1); //lo desplazo asi veo el proximo bit
						if(bitMayorPeso==	(byte)10000000) { //10000000..
							bufferDecodificador+="1";
							
						}else {
							bufferDecodificador+="0";
						}
						
						
						if(bufferDecodificador.length()==8) { //complete el buffer, la idea ahora es decodificar los simbolos dentro de ese buffer
							int frontera=1;
							for(int indexBuffer=0;indexBuffer<8;indexBuffer++) { 
								String clave=this.getKey(codificaciones, bufferDecodificador.substring(0, frontera)); //clave es un valor entre 0..255, que es el tono gris del pixel.
								
								if(clave!=null) {
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
			
			}
			in.close();
			System.out.println("\nbitslEIDOS " + bitsLeidos + " total " + totalBits);
			bufferReader.close();
			return decodificado;
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null; //aca no llega nunca, pero Java.... lo requiere....
	}
	
}
